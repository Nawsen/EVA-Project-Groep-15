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

app.controller("IndexCtrl", ["$scope", "auth", function($scope, auth) {
    $scope.isLoggedIn = function() {
        return auth.isLoggedIn();
    };
}]);