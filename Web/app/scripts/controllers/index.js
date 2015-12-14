app.controller("IndexCtrl", ["$scope", "auth", 'NetworkingService', '$state', 'toasty', 'translation',
    function ($scope, auth, netService, $state, toasty, translation) {
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
                        toasty.success({
                            title: 'Successfully logged in!',
                            msg: 'Welcome ' + auth.currentUser()
                        });
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