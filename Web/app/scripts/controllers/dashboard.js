/**
 * Created by wannes on 9/10/2015.
 */

var app = angular.module('eva');

app.controller('DashboardCtrl',
    ['$scope', 'auth',
        function ($scope, auth) {
            $scope.user = {
                'firstname': 'voornaam',
                'lastname': 'achternaam',
                'avatar': 'http://placehold.it/150x150'
            }

            ;
            $scope.challenges = [
                {
                    title: 'test',
                    imageUrl: 'images/challenges/pompoensoep_kokoscreme_basilicum.jpg'
                },
                {
                    title: 'test',
                    imageUrl: 'images/challenges/knolseldersoep.jpg'
                },
                {
                    title: 'test',
                    imageUrl: 'images/challenges/knolseldersoep.jpg'
                }];

            if (auth.isLoggedIn()) {
                /*
                 $http.get("http://bitcode.io:8080/backend/api/challenges/daily").success(function (data, status, headers, config) {
                 $scope.challenges = data;
                 console.log($scope.challenges[0]);
                 });*/

                //challenges.getDailyChallenges();
                //console.log(challenges.getDailyChallenges());
            } else {
                $state.go('login');
            }
        }]
);