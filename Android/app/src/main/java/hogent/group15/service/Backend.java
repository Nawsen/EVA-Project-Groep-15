package hogent.group15.service;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.util.Log;
import android.widget.ImageView;

import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;

import java.util.List;

import hogent.group15.Consumer;
import hogent.group15.data.ChallengesRepository;
import hogent.group15.data.Database;
import hogent.group15.domain.Achievement;
import hogent.group15.domain.Challenge;
import hogent.group15.domain.Gender;
import hogent.group15.domain.User;
import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.ResponseCallback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.converter.GsonConverter;
import hogent.group15.ui.R;

/**
 * Created by Frederik on 10/20/2015.
 */
public class Backend {

    private static Backend backend;
    private final RestAdapter restAdapter = doConfig(new RestAdapter.Builder()).build();
    private final BackendAPI backendAPI = restAdapter.create(BackendAPI.class);
    private LoginResponse jwtToken;
    private Context context;

    public static final String TAG = Backend.class.getName();
    private static final String TOKEN_KEY = "token";

    private Backend(Context context) {
        this.context = context;
    }

    public static Backend getBackend(Context context) {
        if (backend == null) {
            backend = new Backend(context);
        }

        return backend;
    }

    public enum LoginResult {
        NETWORK_ERROR,
        WRONG_CREDENTIALS
    }

    public void loadImageInto(String uri, com.squareup.picasso.Callback callback, ImageView view) {
        loadImageInto(Uri.parse(uri), callback, view);
    }

    public void loadImageInto(String uri, ImageView view) {
        loadImageInto(Uri.parse(uri), null, view);
    }

    public void loadImageInto(Uri uri, com.squareup.picasso.Callback callback, ImageView view) {
        loadImageInto(uri, callback, view, 0);
    }

    public void loadImageInto(Uri uri, com.squareup.picasso.Callback callback, ImageView view, int placeHolder) {
        loadImageInto(uri, callback, view, placeHolder, android.R.drawable.stat_notify_error);
    }

    public void loadImageInto(Uri uri, com.squareup.picasso.Callback callback, ImageView view, int placeHolder, int errorImage) {
        if (callback != null) {
            Picasso.with(context).load(uri).error(errorImage).into(view, callback);
        } else {
            Picasso.with(context).load(uri).error(errorImage).into(view);
        }
    }

    public void loadImageInto(Uri uri, ImageView view, int placeHolder, int errorImage) {
        //Picasso.with(context).setIndicatorsEnabled(true);
        Picasso.with(context).load(uri).error(errorImage).into(view);
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

    public void getAchievements(Callback<List<Achievement>> achievements) {
        backendAPI.getAchievements(achievements);
    }

    public void registerUser(User user, ResponseCallback callback) {
        backendAPI.register(user, callback);
    }

    private SharedPreferences getSharedPreferences() {
        String key = context.getString(R.string.preference_file_key);
        Log.i(TAG, "Retrieving SharedPreferences for file: " + key);
        return context.getSharedPreferences(key, Context.MODE_PRIVATE);
    }

    /**
     * Try logging in using the existing token stored in shared preferences.
     *
     * @return login succeeded
     */
    public boolean loginUser() {
        SharedPreferences preferences = getSharedPreferences();
        if (preferences.contains(TOKEN_KEY)) {
            final String token = preferences.getString(TOKEN_KEY, "");
            if (token.isEmpty()) {
                return false;
            }

            jwtToken = new LoginResponse(token);
            return true;
        }

        return false;
    }

    public void logoutUser() {
        getSharedPreferences().edit().remove(TOKEN_KEY).commit();
        jwtToken = null;
        LoginManager.getInstance().logOut();
        Log.i(TAG, "Logged out");
    }

    public void loginUser(User user, final Callback<LoginResponse> callback) {
        backendAPI.login(user, new Callback<LoginResponse>() {
            @Override
            public void success(LoginResponse s, Response response) {
                if (response.getStatus() == 200) {
                    s.setType(LoginResponse.LoginResponseType.REGULAR);
                    onTokenReceived(s, response);
                } else if (response.getStatus() == 202) {
                    s.setType(LoginResponse.LoginResponseType.FACEBOOK_REGISTER);
                    callback.success(s, response);
                }
            }

            private void onTokenReceived(LoginResponse s, Response response) {
                jwtToken = s;
                SharedPreferences preferences = getSharedPreferences();
                preferences.edit().putString(TOKEN_KEY, s.getToken()).commit();
                Log.i(TAG, "Saved JWT: '" + s.getToken() + "' in preference file");
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
        database.getDailyChallenges(new Consumer<List<Challenge>>() {
            @Override
            public void consume(List<Challenge> challenges) {
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
        });
    }

    public void getDetailedChallenge(final int challengeId, final Callback<Challenge> callback) {

        backendAPI.getDetailedChallenge(challengeId, callback);
    }

    public void acceptChallenge(final Challenge challenge, final Callback<Challenge> callback) {
        backendAPI.acceptChallenge(challenge.getId(), "", new ResponseCallback() {

            @Override
            public void success(Response response) {
                ChallengesRepository.getInstance(context).setCurrentChallenge(challenge);
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
                ChallengesRepository.getInstance(context).setCurrentChallenge(null);
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
