/**
 * Created by wannes on 9/10/2015.
 */
angular.module('eva').controller('DashboardCtrl',
    ['$scope', '$state', 'auth', '$http', 'challenges',
        function ($scope, $state, auth, $http, challenges) {
            $scope.user = {
                'firstname': 'voornaam',
                'lastname': 'achternaam',
                'avatar': 'http://placehold.it/150x150'
            }
            ;
            $scope.challenges = {};
            if (auth.isLoggedIn()) {
                $http.get("http://ts.wannesvandorpe.be:666/backend/api/challenges/daily").success(function (data, status, headers, config) {
                    $scope.challenges = data;
                    console.log($scope.challenges[0]);
                });

                //challenges.getDailyChallenges();
                console.log(challenges.getDailyChallenges());
            } else {
                $state.go('login');
            }
        }]);