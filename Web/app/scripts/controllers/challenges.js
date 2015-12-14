angular.module('eva').controller('ChallengesCtrl',
    ['$scope', 'auth', '$http', '$state', 'NetworkingService', 'translation',
        function ($scope, auth, $http, $state, netService, translation) {
            $scope.translation = translation;
            $scope.user = {
                'firstname': 'voornaam',
                'lastname': 'achternaam',
                'avatar': 'http://placehold.it/150x150'
            };

            $scope.challenges = {};

            $scope.selectedIndex = 0;
            $scope.hasAcceptedChallenge = false;

            $scope.selectedChallenge = function () {
                return $scope.challenges[$scope.selectedIndex];
            };

            $scope.showSelectedChallengeDetails = function (arrayId) {
                $scope.selectedIndex = arrayId;
                $scope.loadChallengeData();
            };

            $scope.showAcceptedChallengeDetails = function () {
                $scope.detailedChallenge = $scope.acceptedChallenge;
            };

            $scope.detailedChallenge = {};

            $scope.loadChallengeData = function () {
                netService.get('/backend/api/challenges/' + $scope.selectedChallenge().id).success(function (data, status, headers, config) {
                    $scope.detailedChallenge = data;
                });
            };

            function initialize() {
                if (auth.isLoggedIn()) {
                    netService.get('/backend/api/challenges/completed').success(function (data) {
                        $scope.challenges = data;
                    });
                    netService.get('/backend/api/users/details').success(function (data, status) {
                        if (status == 200) {
                            $("#innerbar").css("width", (data.completedCount * 100) / 21 + "%");
                        }
                    });
                } else {
                    $state.go('login');
                }
            }

            initialize();
        }]
    );