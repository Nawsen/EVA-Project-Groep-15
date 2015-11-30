/**
 * Created by wannes on 9/10/2015.
 */
angular.module('eva').controller('SettingsCtrl',
    ['$scope', '$location', 'NetworkingService',
        function ($scope, $location, netService) {
            $scope.userOld = {
                email: "",
                password: "",
                repeatPassword: "",
                firstName: "",
                lastName: "",
                grade: $scope.values[0]
            };
            $scope.user = {
                email: "",
                password: "",
                repeatPassword: "",
                firstName: "",
                lastName: "",
                grade: $scope.values[0]
            };

            $scope.loadUserData = function () {
                netService.get('/backend/api/users/details').success(function (data) {
                    $scope.userOld = data;
                });
            };
            $scope.saveChanges = function () {

            }
        }]);
