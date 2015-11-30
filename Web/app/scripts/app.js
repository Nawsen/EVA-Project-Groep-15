/**
 * Created by wannes on 9/10/2015.
 */
var app = angular.module('eva', ['ui.router', 'angular-toasty', 'facebook']);

app.config(['$stateProvider', '$urlRouterProvider', '$httpProvider','toastyConfigProvider', 'FacebookProvider', function ($stateProvider, $urlRouterProvider, $httpProvider, toastyConfigProvider, FacebookProvider) {
    $httpProvider.defaults.useXDomain = true;
    FacebookProvider.init('1506320029692649');
    toastyConfigProvider.setConfig({
        sound: true,
        shake: false,
        position: 'bottom-left',
        showClose: false,
        clickToClose: true
    });
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
