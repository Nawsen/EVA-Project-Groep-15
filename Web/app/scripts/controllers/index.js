app.controller("IndexCtrl", ["$scope", "auth", 'NetworkingService', '$state', 'toasty', 'translation', 'messages',
    function ($scope, auth, netService, $state, toasty, translation, messages) {
        (function(d, s, id) {
            var js, fjs = d.getElementsByTagName(s)[0];
            if (d.getElementById(id)) return;
            js = d.createElement(s); js.id = id;
            js.src = "//connect.facebook.net/nl_NL/sdk.js#xfbml=1&version=v2.5&appId=1618258668410463";
            fjs.parentNode.insertBefore(js, fjs);
        }(document, 'script', 'facebook-jssdk'));
        $scope.translation = translation;
        $scope.isLoggedIn = function () {
            return auth.isLoggedIn();
        };
        $scope.user = {};
        $scope.initSideBar = function () {
            if (auth.isLoggedIn()) {
                netService.get('/backend/api/users/details').success(function (data, status) {
                    if (status == 200) {
                        $scope.user = data;
                        if (messages.hasOwnProperty("loggedIn")) {
                            if (messages.loggedIn) {
                                toasty.success({
                                    title: translation.getCurrentlySelected().login_successful,
                                    msg: translation.getCurrentlySelected().welcome_notification + auth.currentUser()
                                });
                            }
                        }

                        if (data.imageUrl == "" || !data.imageUrl) {
                            $scope.user.imageUrl = "http://cdn-9chat-fun.9cache.com/static/dist/images/avatar-default.png";
                        }
                        console.log($scope.user);
                    }
                });
            }
        };
        $scope.logout = function () {
            auth.logOut();
            $state.go('login');
        };
        function removeActive() {
            $("#overviewButton").removeClass("active");
            $("#completedButton").removeClass("active");
            $("#settingsButton").removeClass("active");
            $("#adminButton").removeClass("active");
        }

        $scope.overview = function () {
            removeActive();
            $("#overviewButton").addClass("active");
            $state.go('dashboard');
        };
        $scope.completed = function () {
            removeActive();
            $("#completedButton").addClass("active");
            $state.go('completed');
        };
        $scope.settings = function () {
            removeActive();
            $("#settingsButton").addClass("active");
            $state.go('settings');
        };
        $scope.admin = function () {
            removeActive();
            $("#adminButton").addClass("active");
            $state.go('admin');
        };
        $scope.initSideBar();
        $scope.$on("initSideBar", function () {
            $scope.initSideBar();
        });
    }]);