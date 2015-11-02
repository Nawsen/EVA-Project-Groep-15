package hogent.group15.domain;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URI;
import java.util.List;
import java.util.Scanner;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Frederik on 10/20/2015.
 */
public class Backend {

    private final URI backendServerUri;
    private static Backend backend;
    private final static String TAG = "BACKEND";
    private final RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint("http://192.168.0.188:8080/backend/api/").setLogLevel(RestAdapter.LogLevel.FULL).build();
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

    private String getToken() {
        if (jwtToken == null) {
            return null;
        } else {
            return "Bearer " + jwtToken.getToken();
        }
    }

    public AsyncTask<Void, Void, String> registerUser(final CharSequence firstName, final CharSequence lastName, final CharSequence email, final Sex sex, final CharSequence password,
                                                      final VegetarianGrade grade, final OnNetworkResponseListener<String, IOException> callback) {
        AsyncTask<Void, Void, String> task = new AsyncTask<Void, Void, String>() {

            private boolean isError;

            @Override
            protected String doInBackground(Void... params) {
                try {
                    HttpURLConnection connection = (HttpURLConnection) backendServerUri.resolve("user/register").toURL().openConnection();
                    connection.addRequestProperty("Content-Type", "application/json");
                    connection.setRequestMethod("POST");
                    connection.setDoInput(true);
                    connection.setDoOutput(true);

                    PrintWriter out = new PrintWriter(connection.getOutputStream());

                    JSONObject user = new JSONObject();
                    user.put("firstName", firstName);
                    user.put("lastName", lastName);
                    user.put("email", email);
                    user.put("gender", sex.asInt());
                    user.put("password", password);
                    user.put("grade", grade.toString());
                    out.println(user.toString());
                    out.flush();

                    Scanner in = new Scanner(connection.getInputStream());
                    StringBuilder dataIn = new StringBuilder();

                    while (in.hasNext()) {
                        dataIn.append(in.next());
                    }

                    return dataIn.toString();
                } catch (JSONException e) {
                    Log.e(TAG, "Couldn't create JSON: " + e.getMessage());
                    isError = true;
                    return "json";
                } catch (IOException ioex) {
                    isError = true;
                    callback.onError(ioex);
                    return "";
                }
            }

            @Override
            protected void onPostExecute(String s) {

                if (!isError) {
                    callback.onResponse(s);
                }
            }
        };

        task.execute();
        return task;
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
            backendAPI.getDailyChallenges(getToken(), callback);
        }
    }

    public void getDetailedChallenge(final int descriptionId, final Callback<Challenge> callback) {
        if(jwtToken != null) {
            backendAPI.getDetailedChallenge(getToken(), descriptionId, callback);
        }
    }
}
