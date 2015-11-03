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

        challenges.challenges = $http.get("http://ts.wannesvandorpe.be:666/backend/api/challenges/daily", config);
    };

    return challenges;
}])