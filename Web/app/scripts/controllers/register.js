/**
 * Created by wannes on 9/10/2015.
 */
angular.module('eva').controller('RegisterCtrl', ['$scope', '$location', function ($scope, $location) {
    $scope.login = function () {
        //TODO check login info with backend
        //for now basic login checker
        /*if ($scope.email === "demo" && $scope.password === "demo") {
            $scope.emailwarn = "";
            $scope.passwordwarn = "";
            $location.path('/dashboard');
        } else {
            $scope.messages = [
                {'emailerror': 'Wrong email!'},
                {'passworderror': 'Wrong password!'}
            ];
        }
        $scope.email = "";
        $scope.password = "";
        return false;*/

    };



}]);
