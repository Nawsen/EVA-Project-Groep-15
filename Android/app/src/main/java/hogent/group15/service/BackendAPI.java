package hogent.group15.service;

import java.util.List;

import hogent.group15.domain.Challenge;
import hogent.group15.domain.User;
import retrofit.Callback;
import retrofit.ResponseCallback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;

/**
 * Created by Frederik on 11/2/2015.
 */
public interface BackendAPI {

    @GET("/challenges/daily/detailed")
    void getDailyChallenges(Callback<List<Challenge>> callback);

    @POST("/users/login")
    void login(@Body User user, Callback<LoginResponse> token);

    @GET("/challenges/{challengeId}")
    void getDetailedChallenge(@Path("challengeId") int id, Callback<Challenge> description);

    @POST("/users/register")
    void register(@Body User user, ResponseCallback callback);

    @PUT("/challenges/{challengeId}/accept")
    void acceptChallenge(@Path("challengeId") int id, @Body String body, ResponseCallback callback);

    @GET("/challenges/accepted")
    void getAcceptedChallenge(Callback<Challenge> callback);

    @PUT("/challenges/complete")
    void completeChallenge(@Body String body, ResponseCallback callback);

    @GET("/challenges/completed")
    void getCompletedChallenges(Callback<List<Challenge>> challenges);
}
