/**
 * Created by wannes on 9/10/2015.
 */
angular.module('eva').controller('SettingsCtrl',
    ['$scope', '$location', 'NetworkingService', 'translation', '$state',
        function ($scope, $location, netService, translation, $state) {
            $scope.showHelpMail = false;
            $scope.passwordFocused = false;
            $scope.showHelpPassword = function () {
                if ($scope.passwordFocused) {
                    if ($scope.userNew.password != undefined) {
                        if ($scope.userNew.password.length >= 7) {
                            return false;
                        }
                        return true;
                    } else {
                        return true;
                    }
                } else {
                    return false;
                }
            };
            $scope.showHelpRepeatPassword = false;
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
            $scope.userOld = {
                email: "",
                password: "",
                repeatPassword: "",
                firstName: "",
                lastName: "",
                grade: "OMNIVORE"
            };
            $scope.userNew = {
                email: "bla@bla.be",
                password: "",
                repeatPassword: "",
                firstName: "",
                lastName: "",
                grade: "OMNIVORE"
            };
            $scope.userCleaned = {};


            /**
             * Niet noodzakelijk, heb ik gewoon geschreven voor cleanliness.
             */
            function removeUnchangedProperties() {
                for (var key in $scope.userOld) {
                    if ($scope.userOld.hasOwnProperty(key)) {
                        if ($scope.userNew[key] == $scope.userOld[key]) {
                            $scope.userCleaned[key] = $scope.userNew[key];
                        }
                    }
                }

            }

            function loadUserData() {
                netService.get('/backend/api/users/details').success(function (data) {
                    console.log(data);
                    $scope.userOld = data;
                    $scope.userNew = data;
                    for (var property in $scope.values) {
                        if ($scope.values.hasOwnProperty(property)) {
                            if ($scope.values[property].val == data.grade) {
                                $scope.userOld.grade = $scope.values[property];
                                $scope.userNew.grade = $scope.values[property];
                            }
                        }
                    }
                });
            }

            $scope.validPassword = function () {
                if ($scope.userNew.hasOwnProperty("password") && $scope.userNew.password != undefined) {
                    if ($scope.userNew.password === $scope.userNew.repeatPassword) {
                        if ($scope.userNew.password != '') {
                            if ($scope.userNew.password.length >= 7) {
                                // passwords match, aren't empty and long enough
                                return true;
                            } else {
                                return false;
                            }
                        } else {
                            // empty password, valid (no change, but password property was created)
                            return true;
                        }
                        return false;
                    } else {
                        // passwords don't match
                        return false;
                    }
                } else {
                    // has no password property -> password wasn't changed
                    return true;
                }
            };

            $scope.sendData = function () {
                removeUnchangedProperties();
                netService.put('/backend/api/users/update', $scope.userCleaned).then(function () {
                    $state.go('dashboard');
                });
            };

            loadUserData();
        }]);
