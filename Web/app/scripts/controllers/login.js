/**
 * Created by wannes on 9/10/2015.
 */
var app = angular.module('eva');
angular.module('eva').controller('LoginCtrl',
    ['$scope', '$http', '$location', '$state','$window', 'auth', 'messages', 'Facebook', 'translation', 'toasty',
        function ($scope, $http, $location, $state, $window, auth, messages, Facebook, translation, toasty) {
            $scope.translation = translation;
            $scope.user = {
                email: messages.email,
                password: ""
            };
            $('#menu').css('display', 'none');
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
                    toasty.error({
                        title: translation.getCurrentlySelected().login_error
                    });
                    $scope.error = error;
                }).then(function () {
                    messages.loggedIn = true;
                    $state.go('dashboard');
                    $scope.$emit('initSideBar');
                    $('#menu').css('display', 'block');
                });


                $scope.email = "";
                $scope.password = "";
                return false;

            };

            $scope.facebooklogin = function () {
                Facebook.login(function (response) {
                    console.log(response);
                    $scope.user.facebookId = response.authResponse.userID;
                    $scope.user.accessToken = response.authResponse.accessToken;
                    auth.login($scope.user).error(function (error) {
                        toasty.error({
                            title: translation.getCurrentlySelected().login_error_fb
                        });
                    }).then(function (data) {
                        if ($window.localStorage['eva-fbreg']){
                            $state.go('register');
                        } else {
                            $window.location.reload();
                        }

                    });
                }, {'scope': 'email,public_profile,user_friends,user_about_me,user_location'});
                messages.loggedIn = true;
                $state.go('dashboard');
                $('#menu').css('display', 'block');
                return false;
            };
            $scope.register = function () {
                $state.go('register');
                return false;
            }

        }]);