/**
 * Created by wannes on 9/10/2015.
 */
angular.module('eva').controller('SettingsCtrl',
    ['$scope', '$location', 'NetworkingService',
        function ($scope, $location, netService) {
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
                    "text": "Omnivoor"
                },
                {
                    "val": "PESCETARIAR",
                    "text": "Pescotariër (geen vlees, wel vis)"
                },
                {
                    "val": "PARTTIME_VEGETARIAN",
                    "text": "Parttime-vegetariër (Minstens 3 keer per week vegetarisch)"
                },
                {
                    "val": "VEGETARIAN",
                    "text": "Vegetariër (geen vlees of vis)"
                },
                {
                    "val": "VEGAN",
                    "text": "Veganist (geen dierlijke producten)"
                }
            ];
            $scope.userOld = {
                email: "",
                password: "",
                repeatPassword: "",
                firstName: "",
                lastName: "",
                grade: $scope.values[0]
            };
            $scope.userNew = {
                email: "bla@bla.be",
                password: "",
                repeatPassword: "",
                firstName: "",
                lastName: "",
                grade: $scope.values[0]
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

                    $scope.userOld = data;
                    $scope.userNew = data;
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
                console.log($scope.userCleaned);
            };

            loadUserData();
        }]);
