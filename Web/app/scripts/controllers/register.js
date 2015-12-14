/**
 * Created by wannes on 9/10/2015.
 */
angular.module('eva').controller('RegisterCtrl',
    ['$scope', '$location', '$window', '$http', '$state', 'auth', 'messages', 'translation', 'toasty',
        function ($scope, $location, $window, $http, $state, auth, messages, translation, toasty) {
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

                auth.register(userObj).error(function (error) {
                    $scope.error = error;
                }).then(function () {
                    $state.go('login');
                });
            };
            $scope.registerFb = function () {
                $window.localStorage.removeItem('eva-fbreg');
                var userObj = {
                    email: $scope.user.email,
                    firstName: $scope.user.firstName,
                    lastName: $scope.user.lastName,
                    facebookId: $scope.user.facebookid,
                    gender: $scope.convertGender(),
                    grade: $scope.user.grade.val,
                    imageUrl: $scope.user.imageUrl
                };

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
            };
        }]);
