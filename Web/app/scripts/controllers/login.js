/**
 * Created by wannes on 9/10/2015.
 */
angular.module('eva').controller('LoginCtrl', ['$scope', '$http','$location', function ($scope, $http, $location) {
    $scope.login = function () {
        //TODO implement jsonwebtokens

        var dataObj = {
            email : $scope.email,
            password : $scope.password
        };
        var res = $http.post('http://178.62.232.69/backend/api/user/login/', dataObj);
        res.success(function(data, status, headers, config) {
            $location.path('/dashboard');
            localStorage.setItem("email", $scope.email);
            angular.element(document.querySelector('#emailwarn')).text("");
            angular.element(document.querySelector('#passwordwarn')).text("");
        });
        res.error(function(data, status, headers, config) {
            angular.element(document.querySelector('#emailwarn')).text("Wrong email!");
            angular.element(document.querySelector('#passwordwarn')).text("Wrong password!");
        });

        $scope.email = "";
        $scope.password = "";
        return false;

    }
    $scope.facebooklogin = function () {
        //TODO implement facebook api
        $location.path('/dashboard');
        return false;
    }
    $scope.register = function () {
        //TODO implement facebook api
        $location.path('/register');
        return false;
    }


}]);