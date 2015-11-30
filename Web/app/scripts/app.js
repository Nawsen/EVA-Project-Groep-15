/**
 * Created by wannes on 9/10/2015.
 */
var app = angular.module('eva', ['ui.router']);

app.config(['$stateProvider', '$urlRouterProvider', '$httpProvider', function ($stateProvider, $urlRouterProvider, $httpProvider) {
    $httpProvider.defaults.useXDomain = true;

    $stateProvider
        .state('login', {
            url: '/login',
            templateUrl: 'views/login.html',
            controller: 'LoginCtrl'
        })
        .state('dashboard', {
            url: '/dashboard',
            templateUrl: 'views/dashboard.html',
            controller: 'DashboardCtrl'
        })
        .state('register', {
            url: '/register',
            templateUrl: 'views/register.html',
            controller: 'RegisterCtrl'
        })
        .state('index', {
            url: '/index',
            templateUrl: 'index.html',
            controller: 'IndexCtrl'
        }).state('completed', {
            url: '/completed',
            templateUrl: 'views/challenges.html',
            controller: 'ChallengesCtrl'
        });
    $urlRouterProvider.otherwise('/login');
}]);

app.controller("IndexCtrl", ["$scope", "auth",'NetworkingService', function($scope, auth, netService) {
    $scope.isLoggedIn = function() {
        return auth.isLoggedIn();
    };
    $scope.user = {};
    function initSideBar(){
        if (auth.isLoggedIn()) {
            netService.get('/backend/api/users/details').success(function (data, status) {
                if (status == 200) {
                   $scope.user = data;

                    if (data.imageUrl == "" || !data.imageUrl){
                        $scope.user.imageUrl = "http://www.gravatar.com/avatar/2b4daf6ced6cd12b76fbe41bd1584108?d=mm&s=250&r=G";
                    }
                }
            });
        }
    }
    initSideBar();
}]);