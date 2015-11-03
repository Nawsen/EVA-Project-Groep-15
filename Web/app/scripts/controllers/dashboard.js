/**
 * Created by wannes on 9/10/2015.
 */
angular.module('eva').controller('DashboardCtrl',
    ['$scope', '$state', 'auth', '$http',
    function ($scope, $state, auth, $http) {
    $scope.user = {'firstname': 'voornaam', 'lastname': 'achternaam', 'avatar': 'http://placehold.it/150x150'};
    if (auth.isLoggedIn()) {
    } else {
        $state.go('login');
    }
}]);