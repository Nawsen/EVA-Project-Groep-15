
app.controller("IndexCtrl", ["$scope", "auth",'NetworkingService', function($scope, auth, netService) {
    $scope.isLoggedIn = function() {
        return auth.isLoggedIn();
    };
    $scope.user = {};
    $scope.initSideBar = function (){
        if (auth.isLoggedIn()) {
            netService.get('/backend/api/users/details').success(function (data, status) {
                if (status == 200) {
                    $scope.user = data;

                    if (data.imageUrl == "" || !data.imageUrl){
                        $scope.user.imageUrl = "http://www.gravatar.com/avatar/2b4daf6ced6cd12b76fbe41bd1584108?d=mm&s=250&r=G";
                    }
                    console.log($scope.user);
                }
            });
        }
    }
    $scope.initSideBar();
    $scope.$on("initSideBar", function(){
        $scope.initSideBar();
    });
}]);