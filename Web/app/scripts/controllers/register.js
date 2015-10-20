/**
 * Created by wannes on 9/10/2015.
 */
angular.module('eva').controller('RegisterCtrl', ['$scope', '$location', function ($scope, $location) {
    $scope.form = {

    }
    $scope.user = {
        email: "",
        password:"sssss",
        repeatPassword:"ssss",
        firstName: "",
        lastName: ""
    };
    $scope.validatePassword = function () {
        console.log(registerForm.email.$valid);
        if (($scope.user.password === $scope.user.repeatPassword) && $scope.user.password.length >= 5 && $scope.form.email.$valid) {
            console.log('test');
            return false;
        } else {
            console.log('test2');
            return true;
        }
    }



}]);
