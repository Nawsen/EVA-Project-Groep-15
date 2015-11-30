/**
 * Created by mathi on 3/11/2015.
 */
app.factory('challenges', ['$http', 'auth', 'NetworkService', function ($http, auth, netService) {
    var challenges = {};
    challenges.challenges = {};
    challenges.getDailyChallenges = function () {
        var config = {
            headers: {
                'Authorization': 'Bearer ' + auth.getToken(),
                'Accept': 'application/json'
            }
        };

        netService.get("/backend/api/challenges/daily").success(function (data, status, headers, config) {
            challenges.challenges = data;
        });
        return challenges.challenges;
    };

    return challenges;
}])