/**
 * Created by wannes on 9/10/2015.
 */

var app = angular.module('eva');

app.controller('DashboardCtrl',
    ['$scope', 'auth', '$http', '$state',
        function ($scope, auth, $http, $state) {
            $scope.user = {
                'firstname': 'voornaam',
                'lastname': 'achternaam',
                'avatar': 'http://placehold.it/150x150'
            };
            $scope.challenges = {};

            $scope.selectedIndex = 0;
            $scope.selectedChallenge = function () {
                return $scope.challenges[$scope.selectedIndex];
            }

            $scope.challengeClicked = function (arrayId) {
                $scope.selectedIndex = arrayId;
                $scope.loadChallengeData();
            }
            $scope.acceptChallenge =function (chId){
                console.log(chId);
            }

            $scope.detailedChallenge = {};

            $scope.loadChallengeData = function () {
                $http.get("http://localhost:8080/backend/api/challenges/" + $scope.selectedChallenge().id).success(function (data, status, headers, config) {
                    $scope.detailedChallenge = data;
                });
            }

            if (auth.isLoggedIn()) {
                $http.get("http://localhost:8080/backend/api/challenges/daily").success(function (data, status, headers, config) {
                    $scope.challenges = data;
                    console.log($scope.challenges[0]);
                });

                //challenges.getDailyChallenges();
                //console.log(challenges.getDailyChallenges());
            } else {
                $state.go('login');
            }
        }]
    );