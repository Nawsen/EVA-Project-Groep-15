/**
 * Created by wannes on 9/10/2015.
 */
var app = angular.module('eva');
angular.module('eva').controller('LoginCtrl',
    ['$scope', '$http', '$location', '$state', 'auth', 'messages','Facebook','toasty',
        function ($scope, $http, $location, $state, auth, messages, Facebook, toasty) {
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
                Facebook.login(function(response) {
                    console.log(response);
                    $scope.user.facebookId = response.authResponse.userID;
                    $scope.user.accessToken = response.authResponse.accessToken;
                    auth.login($scope.user).error(function (error) {
                        toasty.error({
                            title: 'Something went wrong!',
                            msg: 'Please try again or notify an admin.'
                        });
                    });
                },{'scope': 'email,public_profile,user_friends,user_about_me,user_location'});

                return false;
            }
            $scope.register = function () {
                $state.go('register');
                return false;
            }

        }]);