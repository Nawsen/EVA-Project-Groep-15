/**
 * Created by wannes on 9/10/2015.
 */
angular.module('eva').controller('SettingsCtrl',
    ['$scope', '$location', 'NetworkingService',
        function ($scope, $location, netService) {
            $scope.showHelpMail = false;
            $scope.showHelpPassword = false;
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
                console.log('test');
            }

            function loadUserData() {
                console.log('test');
                netService.get('/backend/api/users/details').success(function (data) {
                    $scope.userOld = data;
                    $scope.user = data;
                });
            }

            $scope.validPassword = function () {
                if (($scope.userNew.password === $scope.userNew.repeatPassword) && $scope.userNew.password.length >= 7) {
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
            removeUnchangedProperties();
            loadUserData();
        }]);
