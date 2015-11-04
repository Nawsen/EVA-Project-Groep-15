/**
 * Created by mathi on 3/11/2015.
 */
app.factory('challenges', ['$http', 'auth', function ($http, auth) {
    var challenges = {};
    challenges.challenges = {};
    challenges.getDailyChallenges = function () {
        var config = {headers:  {
            'Authorization': 'Bearer ' + auth.getToken(),
            'Accept': 'application/json'
        }
        };

        $http.get("localhost:8080/backend/api/challenges/daily").success(function(data, status, headers, config) {
            challenges.challenges = data;
        });
        return challenges.challenges;
    };

    return challenges;
}])