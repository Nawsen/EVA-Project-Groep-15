angular.module('eva').controller('ChallengesCtrl',
    ['$scope', 'auth', '$http', '$state', '$window', 'NetworkingService', 'translation',
        function ($scope, auth, $http, $state, $window, netService, translation) {
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

            $scope.sharecomp = function(challenge, i){
                if (i==3){
                    FB.ui(
                        {
                            method: 'feed',
                            name: 'I just unlocked the "' + challenge.title + '" achievement!',
                            link: 'http://www.evavzw.be',
                            picture: challenge.imageUrl,
                            caption: 'Goto www.evavzw.be for more fun challenges!',
                            description: challenge.description,
                            message: ''
                        }, function(response){
                            $window.location.reload();
                        });
                }
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