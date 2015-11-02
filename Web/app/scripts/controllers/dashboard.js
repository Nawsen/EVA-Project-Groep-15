/**
 * Created by wannes on 9/10/2015.
 */
angular.module('eva').controller('DashboardCtrl', ['$scope', '$location', 'auth', function ($scope, $location, auth) {
    $scope.user = {'firstname': 'voornaam', 'lastname': 'achternaam', 'avatar': 'http://placehold.it/150x150'};

}]);