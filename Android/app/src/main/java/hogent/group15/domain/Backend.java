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
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.converter.GsonConverter;

/**
 * Created by Frederik on 10/20/2015.
 */
public class Backend {

    private final URI backendServerUri;
    private static Backend backend;
    private final static String TAG = "BACKEND";
    private final RestAdapter restAdapter = doConfig(new RestAdapter.Builder()).build();
    private final BackendAPI backendAPI = restAdapter.create(BackendAPI.class);
    private JsonWebToken jwtToken;

    private Backend() {
        backendServerUri = URI.create("");
    }

    public static Backend getBackend() {
        if (backend == null) {
            backend = new Backend();
        }

        return backend;
    }

    public enum LoginResult {
        NETWORK_ERROR,
        WRONG_CREDENTIALS
    }

    private RestAdapter.Builder doConfig(RestAdapter.Builder adapter) {
        return adapter
                .setEndpoint("http://192.168.0.188:8080/backend/api/")
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

    public void registerUser(User user, Callback<Response> callback) {
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
        if (jwtToken != null) {
            backendAPI.getDailyChallenges(callback);
        }
    }

    public void getDetailedChallenge(final int descriptionId, final Callback<Challenge> callback) {
        if(jwtToken != null) {
            backendAPI.getDetailedChallenge(descriptionId, callback);
        }
    }
}
