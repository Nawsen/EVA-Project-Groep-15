/**
 * Created by wannes on 9/10/2015.
 */
angular.module('eva').controller('LoginCtrl',
    ['$scope', '$http','$location', '$state', 'auth',
    function ($scope, $http, $location, $state, auth) {
    $scope.login = function () {
        //TODO implement jsonwebtokens

        var dataObj = {
            email : $scope.email,
            password : $scope.password
        };
        auth.login(dataObj).error(function (error) {
            $scope.error = error;
            angular.element(document.querySelector('#emailwarn')).text("Wrong email!");
            angular.element(document.querySelector('#passwordwarn')).text("Wrong password!");
        }).then(function () {
            angular.element(document.querySelector('#emailwarn')).text("");
            angular.element(document.querySelector('#passwordwarn')).text("");
            $state.go('dashboard');
        });


        $scope.email = "";
        $scope.password = "";
        return false;

    }
    $scope.facebooklogin = function () {
        //TODO implement facebook api
        $state.go('/dashboard');
        return false;
    }
    $scope.register = function () {
        //TODO implement facebook api
        $state.go('/register');
        return false;
    }


}]);