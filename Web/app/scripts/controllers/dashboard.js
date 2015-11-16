/**
 * Created by wannes on 9/10/2015.
 */

var app = angular.module('eva');

app.controller('DashboardCtrl',
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
            
            $scope.showAcceptedChallengeDetails = function() {
                $scope.detailedChallenge = $scope.acceptedChallenge;
            };

            $scope.detailedChallenge = {};

            $scope.loadChallengeData = function () {
                netService.get('/backend/api/challenges/' + $scope.selectedChallenge().id).success(function (data, status, headers, config) {
                    $scope.detailedChallenge = data;
                });
            };
            
            function initializeDashboard() {
                if (auth.isLoggedIn()) {
                    netService.get('/backend/api/challenges/accepted').success(function (data, status) {
                        if (status == 204) {
                            $scope.hasAcceptedChallenge = false;
                            netService.get('/backend/api/challenges/daily').success(function (data) {
                                $scope.challenges = data;
                            });
                        } else {
                            $scope.hasAcceptedChallenge = true;
                            $scope.acceptedChallenge = data;
                        }
                    });

                    //challenges.getDailyChallenges();
                    //console.log(challenges.getDailyChallenges());
                } else {
                    $state.go('login');
                }
            }
            
            $scope.acceptChallenge = function (challengeId) {
                netService.put('/backend/api/challenges/' + $scope.selectedChallenge().id + '/accept').success(function (data, status) {
                    initializeDashboard();
                });
            };
            
            $scope.completeChallenge = function() {
                netService.put('/backend/api/challenges/complete').success(function() {
                    $scope.hasAcceptedChallenge = false;
                    initializeDashboard();
                }).error(function(err) {
                    console.log(err);
                });
            };
            
            initializeDashboard();
        }]
    );