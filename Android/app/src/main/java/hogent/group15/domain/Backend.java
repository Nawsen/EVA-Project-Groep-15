package hogent.group15.domain;

import android.os.AsyncTask;
import android.telecom.Call;
import android.util.Log;

import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URI;
import java.util.List;
import java.util.Scanner;

import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.ResponseCallback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.converter.GsonConverter;

/**
 * Created by Frederik on 10/20/2015.
 */
public class Backend {

    private static Backend backend;
    public final static String TAG = "BACKEND";
    private final RestAdapter restAdapter = doConfig(new RestAdapter.Builder()).build();
    private final BackendAPI backendAPI = restAdapter.create(BackendAPI.class);
    private JsonWebToken jwtToken;

    private Backend() {
    }

    public static Backend getBackend() {
        if (backend == null) {
            backend = new Backend();
        }

        return backend;
    }

    private RestAdapter.Builder doConfig(RestAdapter.Builder adapter) {
        return adapter
                .setEndpoint("http://bitcode.io:8080/backend/api/")
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setConverter(new GsonConverter(new GsonBuilder().registerTypeHierarchyAdapter(Gender.class, new Gender.GenderSerializer()).create()))
                .setRequestInterceptor(new RequestInterceptor() {
                    @Override
                    public void intercept(RequestFacade request) {
                        if (jwtToken != null) {
                            request.addHeader("Authorization", "Bearer " + jwtToken.getToken());
                        }
                    }
                });
    }

    public void registerUser(User user, ResponseCallback callback) {
        backendAPI.register(user, callback);
    }

    public void loginUser(User user, final Callback<JsonWebToken> callback) {
        backendAPI.login(user, new Callback<JsonWebToken>() {
            @Override
            public void success(JsonWebToken s, Response response) {
                jwtToken = s;
                callback.success(s, response);
            }

            @Override
            public void failure(RetrofitError error) {
                callback.failure(error);
            }
        });
    }

    public void getDailyChallenges(final Callback<List<Challenge>> callback) {
        backendAPI.getDailyChallenges(callback);
    }

    public void getDetailedChallenge(final int challengeId, final Callback<Challenge> callback) {
        backendAPI.getDetailedChallenge(challengeId, callback);
    }

    public void acceptChallenge(final Challenge challenge, final Callback<Challenge> callback) {
        backendAPI.acceptChallenge(challenge.getId(), "", new ResponseCallback() {

            @Override
            public void success(Response response) {
                ChallengesRepository.getInstance().setCurrentChallenge(challenge);
                callback.success(challenge, response);
            }

            @Override
            public void failure(RetrofitError error) {
                callback.failure(error);
            }
        });
    }

    public void getAcceptedChallenge(Callback<Challenge> callback) {
        backendAPI.getAcceptedChallenge(callback);
    }

    public void completeCurrentChallenge(final ResponseCallback callback) {
        backendAPI.completeChallenge("", new ResponseCallback() {
            @Override
            public void success(Response response) {
                ChallengesRepository.getInstance().setCurrentChallenge(null);
                callback.success(response);
            }

            @Override
            public void failure(RetrofitError error) {
                callback.failure(error);
            }
        });
    }
}
