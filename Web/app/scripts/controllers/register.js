/**
 * Created by wannes on 9/10/2015.
 */
angular.module('eva').controller('RegisterCtrl',
    ['$scope', '$location', '$http', '$state', 'auth',
    function ($scope, $location, $http,  $state, auth) {
    $scope.showHelpMail = false;
    $scope.showHelpPassword = false;
    $scope.showHelpRepeatPassword = false;
    $scope.selectedTab = 'male';
    $scope.values = [
        {
            "val": "OMNIVORE",
            "text": "Omnivoor"
        },
        {
            "val": "PESCETARIAR",
            "text": "Pescotariër (geen vlees, wel vis)"
        },
        {
            "val": "PARTTIME_VEGETARIAN",
            "text": "Parttime-vegetariër (Minstens 3 keer per week vegetarisch)"
        },
        {
            "val": "VEGETARIAN",
            "text": "Vegetariër (geen vlees of vis)"
        },
        {
            "val": "VEGAN",
            "text": "Veganist (geen dierlijke producten)"
        }
    ];
    $scope.user = {
        email: "stoerbeer@hotmail.com",
        password: "wachtwoord",
        repeatPassword: "wachtwoord",
        firstName: "s",
        lastName: "s",
        grade: $scope.values[0]
    };
    $scope.validPassword = function () {
        if (($scope.user.password === $scope.user.repeatPassword) && $scope.user.password.length >= 7) {
            return true;
        } else {
            return false;
        }
    }
    $scope.validForm = function () {
        if ($scope.user.firstName != "" && $scope.user.lastName != "") {
            return true
        } else {
            return false;
        }
    }
    $scope.convertGender = function () {
        if ($scope.selectedTab === 'male') {
            return 0
        } else {
            return 1
        }
    }
    $scope.register = function () {
        var userObj = {
            email: $scope.user.email,
            firstName: $scope.user.firstName,
            lastName: $scope.user.lastName,
            password: $scope.user.password,
            gender: $scope.convertGender(),
            grade: $scope.user.grade.val
        };
        //var res = $http.post('http://178.62.232.69/backend/api/user/register', userObj);
        //$location.path('/login');
        $state.go('login');

        auth.register(userObj).error(function (error) {
            $scope.error = error;
        }).then(function () {
            $state.go('login');
        });
    }


}]);
