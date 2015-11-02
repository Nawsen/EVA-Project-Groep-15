package hogent.group15.domain;

import android.telecom.Call;

import java.util.List;

import hogent.group15.ui.controls.list.ChallengeListEntry;
import retrofit.Callback;
import retrofit.ResponseCallback;
import retrofit.client.Response;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;

/**
 * Created by Frederik on 11/2/2015.
 */
public interface BackendAPI {

    @GET("/challenges/daily")
    void getDailyChallenges(Callback<List<Challenge>> callback);

    @POST("/users/login")
    void login(@Body User user, Callback<JsonWebToken> token);

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
}
