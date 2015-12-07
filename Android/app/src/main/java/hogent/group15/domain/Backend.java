package hogent.group15.domain;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.telecom.Call;
import android.transition.Fade;
import android.util.Log;
import android.widget.ImageView;

import com.google.gson.GsonBuilder;
import com.j256.ormlite.dao.CloseableIterable;
import com.j256.ormlite.dao.CloseableIterator;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URI;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import hogent.group15.ui.R;
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
    public final static String TAG = Backend.class.getName();
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

    public enum LoginResult {
        NETWORK_ERROR,
        WRONG_CREDENTIALS
    }

    public void loadImageInto(Context context, String uri, ImageView view) {
        loadImageInto(context, Uri.parse(uri), view);
    }

    public void loadImageInto(Context context, Uri uri, ImageView view) {
        loadImageInto(context, uri, view, R.drawable.loading_placeholder);
    }

    public void loadImageInto(Context context, Uri uri, ImageView view, int placeHolder) {
        loadImageInto(context, uri, view, placeHolder, android.R.drawable.stat_notify_error);
    }

    public void loadImageInto(Context context, Uri uri, ImageView view, int placeHolder, int errorImage) {

        //Picasso.with(context).setIndicatorsEnabled(true);
        Picasso.with(context).load(uri).placeholder(placeHolder).error(errorImage).into(view);
    }

    private RestAdapter.Builder doConfig(RestAdapter.Builder adapter) {
        return adapter
                .setEndpoint("http://192.168.0.204:8080/backend/api/")
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

    public void getDailyChallenges(final Context context, final Callback<List<Challenge>> callback) {
        final Database database = Database.getInstance(context);
        List<Challenge> challenges = database.getDailyChallenges();

        if (challenges != null && challenges.size() >= 3) {
            callback.success(challenges, null);
            return;
        }

        backendAPI.getDailyChallenges(new Callback<List<Challenge>>() {
            @Override
            public void success(List<Challenge> challenges, Response response) {
                database.saveChallenges(challenges);
                callback.success(challenges, response);
            }

            @Override
            public void failure(RetrofitError error) {
                callback.failure(error);
            }
        });
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

    public void getCompletedChallenges(final Callback<List<Challenge>> callback) {
        backendAPI.getCompletedChallenges(new Callback<List<Challenge>>() {
            @Override
            public void success(List<Challenge> challenges, Response response) {
                for (Challenge c : challenges) {
                    c.setShowAcceptChallengeButton(false);
                }

                callback.success(challenges, response);
            }

            @Override
            public void failure(RetrofitError error) {
                callback.failure(error);
            }
        });
    }
}
