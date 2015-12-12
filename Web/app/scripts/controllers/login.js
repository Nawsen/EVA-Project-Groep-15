/**
 * Created by wannes on 9/10/2015.
 */
var app = angular.module('eva');
angular.module('eva').controller('LoginCtrl',
    ['$scope', '$http', '$location', '$state', 'auth', 'messages', 'translation',
        function ($scope, $http, $location, $state, auth, messages, translation) {
            $scope.translation = translation;
            $scope.user = {
                email: messages.email,
                password: ""
            };
            //check if user is already logged in
            if (auth.isLoggedIn()){
                $state.go('dashboard');
                $scope.$emit('initSideBar');
            }
            $scope.login = function () {
                auth.login($scope.user).error(function (error) {
                    $scope.error = error;
                    console.log(error);
                    angular.element(document.querySelector('#emailwarn')).text("Wrong email!");
                    angular.element(document.querySelector('#passwordwarn')).text("Wrong password!");
                }).then(function () {
                    angular.element(document.querySelector('#emailwarn')).text("");
                    angular.element(document.querySelector('#passwordwarn')).text("");
                    $state.go('dashboard');
                    $scope.$emit('initSideBar');
                });


                $scope.email = "";
                $scope.password = "";
                return false;

            }
            $scope.facebooklogin = function () {
                //TODO implement facebook api
                $state.go('dashboard');
                return false;
            }
            $scope.register = function () {
                //TODO implement facebook api
                $state.go('register');
                return false;
            }

        }]);