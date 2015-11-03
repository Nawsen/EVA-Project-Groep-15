/**
 * Created by Mathias on 29-Oct-15.
 */

app.factory('auth', ['$http', '$window', function ($http, $window) {
    var auth = {};

    auth.saveToken = function (token) {
        $window.localStorage['eva-token'] = token;
    };

    auth.getToken = function () {
        return $window.localStorage['eva-token'];
    }
    auth.isLoggedIn = function () {
        var token = auth.getToken();

        if (token) {
            auth.setHeader();
            return true;
        } else {
            return false;
        }
    };
    auth.currentUser = function () {
        if (auth.isLoggedIn()) {
            var token = auth.getToken();
            var payload = JSON.parse($window.atob(token.split('.')[1]));

            return payload.jti;
        }
    };
    auth.register = function (user) {
        return $http.post('http://bitcode.io:8080/backend/api/users/register', user).success(function (data) {
            //auth.saveToken(data.token);
            //wordt toch niet teruggestuurd bij register.
        });
    };

    auth.login = function (user) {
        return $http.post('http://bitcode.io:8080/backend/api/users/login', user).success(function (data) {
            auth.saveToken(data.token);
            auth.setHeader();
        });

    };
    auth.setHeader = function () {
        $http.defaults.headers.common.Authorization = 'Bearer ' + auth.getToken();
    }
    auth.logOut = function () {
        $window.localStorage.removeItem('eva-token');
    };
    return auth;
}])

