
app.controller("IndexCtrl", ["$scope", "auth",'NetworkingService', '$state', 'toasty', function($scope, auth, netService, $state, toasty) {
    $scope.isLoggedIn = function() {
        return auth.isLoggedIn();
    };
    $scope.user = {};
    $scope.initSideBar = function (){
        if (auth.isLoggedIn()) {
            netService.get('/backend/api/users/details').success(function (data, status) {
                if (status == 200) {
                    $scope.user = data;
                    toasty.success({
                        title: 'Successfully logged in!',
                        msg: 'Welcome ' + auth.currentUser()
                    });
                    if (data.imageUrl == "" || !data.imageUrl){
                        $scope.user.imageUrl = "http://www.gravatar.com/avatar/2b4daf6ced6cd12b76fbe41bd1584108?d=mm&s=250&r=G";
                    }
                    console.log($scope.user);
                }
            });
        }
    }
    $scope.logout = function () {
        auth.logOut();
        $state.go('login');
    }
    function removeActive(){
        $("#overviewButton").removeClass("active");
        $("#completedButton").removeClass("active");
        $("#settingsButton").removeClass("active");
        $("#adminButton").removeClass("active");
    }
    $scope.overview = function(){
        removeActive()
        $("#overviewButton").addClass("active");
        $state.go('dashboard');
    }
    $scope.completed = function (){
        removeActive()
        $("#completedButton").addClass("active");
        $state.go('completed');
    }
    $scope.settings = function (){
        removeActive()
        $("#settingsButton").addClass("active");
        $state.go('settings');
    }
    $scope.admin = function (){
        removeActive()
        $("#adminButton").addClass("active");
        $state.go('admin');
    }
    $scope.initSideBar();
    $scope.$on("initSideBar", function(){
        $scope.initSideBar();
    });
}]);