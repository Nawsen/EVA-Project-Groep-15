/**
 * Created by wannes on 9/10/2015.
 */
angular.module('eva').controller('RegisterCtrl',
    ['$scope', '$location', '$http', '$state', 'auth', 'messages', 'translation', 'toasty', '$window',
        function ($scope, $location, $http, $state, auth, messages, translation, toasty, $window) {
            $scope.translation = translation;
            $scope.showHelpMail = false;
            $scope.showHelpPassword = false;
            $scope.showHelpRepeatPassword = false;
            $scope.selectedTab = 'male';
            $scope.values = [
                {
                    "val": "OMNIVORE",
                    "text": translation.getCurrentlySelected().grade_omnivore
                },
                {
                    "val": "PESCETARIAR",
                    "text": translation.getCurrentlySelected().grade_pescetariar
                },
                {
                    "val": "PARTTIME_VEGETARIAN",
                    "text": translation.getCurrentlySelected().grade_parttime_vegetarian
                },
                {
                    "val": "VEGETARIAN",
                    "text": translation.getCurrentlySelected().grade_vegetarian
                },
                {
                    "val": "VEGAN",
                    "text": translation.getCurrentlySelected().grade_vegan
                }
            ];
            $scope.user = {
                email: "",
                password: "",
                repeatPassword: "",
                firstName: "",
                lastName: "",
                grade: $scope.values[0]
            };
            if ($window.localStorage['eva-fbreg']){
                $scope.facebook = true;
                var data = JSON.parse($window.localStorage['eva-fbreg']);
                console.log(data);
                $scope.user = {
                    email: data.email,
                    firstName: data.firstName,
                    password: "",
                    repeatPassword: "",
                    lastName: data.lastName,
                    grade: $scope.values[0],
                    facebookid: data.id,
                    imageUrl: data.pictureUrl
                };

            }
            $scope.translation = translation;
            $scope.showHelpMail = false;
            $scope.showHelpPassword = false;
            $scope.showHelpRepeatPassword = false;
            $scope.selectedTab = 'male';


            $scope.validPassword = function () {
                if (($scope.user.password === $scope.user.repeatPassword) && $scope.user.password.length >= 7) {
                    return true;
                } else {
                    return false;
                }
            };
            $scope.validForm = function () {
                if ($scope.user.firstName != "" && $scope.user.lastName != "") {
                    return true
                } else {
                    return false;
                }
            };
            $scope.convertGender = function () {
                if ($scope.selectedTab === 'male') {
                    return 0
                } else {
                    return 1
                }
            };
            $scope.register = function () {
                var userObj = {
                    email: $scope.user.email,
                    firstName: $scope.user.firstName,
                    lastName: $scope.user.lastName,
                    password: $scope.user.password,
                    gender: $scope.convertGender(),
                    grade: $scope.user.grade.val
                };

                messages.email = userObj.email;

                auth.register(userObj).error(function (error, status) {
                    if (status == 400) {
                        toasty.error({
                            title: translation.getCurrentlySelected().error,
                            msg: translation.getCurrentlySelected().register_already_exists,
                            timeout: 0
                        });
                    } else if (status == 500) {
                        toasty.error({
                            title: translation.getCurrentlySelected().error,
                            msg: translation.getCurrentlySelected().internal_error,
                            timeout: 0
                        });
                    }

                }).then(function () {
                    $state.go('login');
                });
            };
            $scope.registerFb = function () {

                console.log("-----------");
                console.log($scope.user);
                var userObj = {
                    email: $scope.user.email,
                    firstName: $scope.user.firstName,
                    lastName: $scope.user.lastName,
                    facebookId: $scope.user.facebookid.toString(),
                    gender: $scope.convertGender(),
                    grade: $scope.user.grade.val,
                    imageUrl: $scope.user.imageUrl
                };
                console.log(userObj);
                auth.register(userObj).error(function (error) {
                    $scope.error = error;
                }).then(function () {
                    if ($scope.facebook){
                        $state.go('login');
                    }else{
                        messages.email = userObj.email;
                        $state.go('login');
                    }
                });
                $window.localStorage.removeItem('eva-fbreg');
            };
        }]);
