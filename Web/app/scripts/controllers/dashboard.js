/**
 * Created by wannes on 9/10/2015.
 */

var app = angular.module('eva');

app.controller('DashboardCtrl',
    ['$scope', 'auth', '$http', '$window', '$state', 'NetworkingService', 'toasty', 'translation',
        function ($scope, auth, $http, $window, $state, netService, toasty, translation) {
            $scope.translation = translation;
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
            //social
            $scope.share = function(challenge, i){
                if (i==0){
                    FB.ui(
                        {
                            method: 'feed',
                            name: 'I am about to start the challenge : "' + challenge.title + '", anyone wants to help?',
                            link: 'http://www.evavzw.be',
                            picture: challenge.imageUrl,
                            caption: 'Goto www.evavzw.be for more fun challenges!',
                            description: challenge.description,
                            message: ''
                        }, function(response){
                            $window.location.reload();
                        });
                }

                if (i==1){
                    FB.ui(
                        {
                            method: 'feed',
                            name: 'I just accepted the "' + challenge.title + '" challenge.',
                            link: 'http://www.evavzw.be',
                            picture: challenge.imageUrl,
                            caption: 'Goto www.evavzw.be for more fun challenges!',
                            description: challenge.description,
                            message: ''
                        }, function(response){
                            $window.location.reload();
                        });
                }
                if (i==2){
                    FB.ui(
                        {
                            method: 'feed',
                            name: 'I just completed the "' + challenge.title + '" challenge!',
                            link: 'http://www.evavzw.be',
                            picture: challenge.imageUrl,
                            caption: 'Goto www.evavzw.be for more fun challenges!',
                            description: challenge.description,
                            message: ''
                        }, function(response){
                            $window.location.reload();
                        });
                }
                if (i==3){
                    FB.ui(
                        {
                            method: 'feed',
                            name: 'I just unlocked the "' + challenge.name + '" achievement!',
                            link: 'http://www.evavzw.be',
                            picture: challenge.imageUrl,
                            caption: 'Goto www.evavzw.be for more fun challenges!',
                            description: challenge.description,
                            message: ''
                        }, function(response){
                            $window.location.reload();
                        });
                }

            }

            $scope.detailedChallenge = {};

            $scope.loadChallengeData = function () {
                netService.get('/backend/api/challenges/' + $scope.selectedChallenge().id).success(function (data, status, headers, config) {
                    $scope.detailedChallenge = data;
                });
            };

            function initializeDashboard() {
                if (auth.isLoggedIn()) {
                    netService.get('/backend/api/users/details').success(function (data, status) {
                        if (status == 200) {
                            $("#innerbar").css("width", (data.completedCount * 100) / 21 + "%");
                        }
                    });
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

                    netService.get('/backend/api/achievements').success(function (data, status, headers, config) {
                        $scope.achievements = data;
                    });

                    //challenges.getDailyChallenges();
                    //console.log(challenges.getDailyChallenges());
                } else {
                    $state.go('login');
                }
            }

            $scope.acceptChallenge = function (challengeId) {
                netService.put('/backend/api/challenges/' + $scope.selectedChallenge().id + '/accept').success(function (data, status) {
                    toasty.info({
                        title: translation.getCurrentlySelected().challenge_good_luck,
                        msg: translation.getCurrentlySelected().challenge_accept_message
                    });
                    initializeDashboard();
                });
            };
            $scope.cancelChallenge = function () {
                netService.put('/backend/api/challenges/fail').success(function () {
                    $scope.hasAcceptedChallenge = false;
                    toasty.info({
                        title: translation.getCurrentlySelected().challenge_cancel_title,
                        msg: translation.getCurrentlySelected().challenge_cancel_message
                    });
                    initializeDashboard();
                }).error(function (err) {
                    console.log(err);
                });
            }

            $scope.completeChallenge = function () {
                netService.put('/backend/api/challenges/complete').success(function () {
                    $scope.hasAcceptedChallenge = false;
                    toasty.info({
                        title: translation.getCurrentlySelected().challenge_complete_title,
                        msg: translation.getCurrentlySelected().challenge_complete_message
                    });
                    initializeDashboard();
                }).error(function (err) {
                    console.log(err);
                });
            };

            initializeDashboard();
        }]
);