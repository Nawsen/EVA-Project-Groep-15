/**
 * Created by wannes on 9/10/2015.
 */
angular.module('eva').controller('RegisterCtrl', ['$scope', '$location', function ($scope, $location) {
    $scope.showHelpMail = false;
    $scope.showHelpPassword = false;
    $scope.showHelpRepeatPassword = false;

    $scope.user = {
        email: "",
        password:"",
        repeatPassword:"",
        firstName: "",
        lastName: ""
    };
    $scope.validPassword = function () {
        if (($scope.user.password === $scope.user.repeatPassword) && $scope.user.password.length > 0) {
            console.log('true');
            return true;
        } else {
            console.log('false');
            return false;
        }
    }



}]);
