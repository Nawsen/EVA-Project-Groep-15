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
            /*
             Mock data
             */
            /*
            $scope.challenges = [
                {
                    id: '230',
                    title: 'test',
                    imageUrl: 'images/challenges/pompoensoep_kokoscreme_basilicum.jpg'
                },
                {
                    id: '945',
                    title: 'test',
                    imageUrl: 'images/challenges/knolseldersoep.jpg'
                },
                {
                    id: '32',
                    title: 'test',
                    imageUrl: 'images/challenges/knolseldersoep.jpg'
                }];*/
            $scope.selectedIndex = 0;
            $scope.selectedChallenge = function () {
                return $scope.challenges[$scope.selectedIndex];
            }

            $scope.challengeClicked = function (arrayId) {
                $scope.selectedIndex = arrayId;
                $scope.loadChallengeData();
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