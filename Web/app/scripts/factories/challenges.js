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

        $http.get("http://ts.wannesvandorpe.be:666/backend/api/challenges/daily").success(function(data, status, headers, config) {
            challenges.challenges = data;
        });
        return challenges.challenges;
    };

    return challenges;
}])