/**
 * Created by wannes on 9/10/2015.
 */
var app = angular.module('eva');
angular.module('eva').controller('LoginCtrl',
    ['$scope', '$http', '$location', '$state', 'auth', 'messages','Facebook', 'translation', 'toasty',
        function ($scope, $http, $location, $state, auth, messages, Facebook, translation, toasty) {
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
            if (messages.hasOwnProperty("email") && messages.email != "") {
                toasty.success({
                    title: translation.getCurrentlySelected().register_successful,
                    msg: translation.getCurrentlySelected().register_successful_message
                });
                delete messages["email"];
            }
            $scope.login = function () {
                auth.login($scope.user).error(function (error) {
                    $scope.error = error;
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
            $scope.checkFacebookStatus;
            $scope.checkFacebookStatus = function () {
                Facebook.getLoginStatus(function(response) {
                    if(response.status === 'connected') {
                        $scope.fbLogin();
                    }
                });
            }
            $scope.fbLogin = function() {
                Facebook.api('/me?fields=first_name,last_name,email', function(response) {
                   console.log(response);
                });
            };
            $scope.facebooklogin = function () {
                Facebook.login(function(response) {
                    $scope.checkFacebookStatus();
                },{'scope': 'email,public_profile,user_friends'});
                $messages.loggedIn = true;
                $state.go('dashboard');
                return false;
            }
            $scope.register = function () {
                //TODO implement facebook api
                $state.go('register');
                return false;
            }

        }]);