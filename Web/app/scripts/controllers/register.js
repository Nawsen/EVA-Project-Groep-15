/**
 * Created by wannes on 9/10/2015.
 */
angular.module('eva').controller('RegisterCtrl',
    ['$scope', '$location', '$http', '$state', 'auth', 'messages', 'translation', 'toasty',
        function ($scope, $location, $http, $state, auth, messages, translation, toasty) {
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
        }]);
