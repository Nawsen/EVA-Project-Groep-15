/**
 * Created by wannes on 9/10/2015.
 */
angular.module('eva').controller('DashboardCtrl',
    ['$scope', '$state', 'auth', '$http', 'challenges',
    function ($scope, $state, auth, $http, challenges) {
    $scope.user = {'firstname': 'voornaam', 'lastname': 'achternaam', 'avatar': 'http://placehold.it/150x150'};
    if (auth.isLoggedIn()) {
        console.log($http.defaults.headers.common.Authorization);


        challenges.getDailyChallenges();
    } else {
        $state.go('login');
    }
}]);