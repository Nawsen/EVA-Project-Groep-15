angular.module('eva').controller('ChallengesCtrl',
    ['$scope', 'auth', '$http', '$state', 'NetworkingService',
        function ($scope, auth, $http, $state, netService) {
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
                } else {
                    $state.go('login');
                }
            }

            initialize();
        }]
    );