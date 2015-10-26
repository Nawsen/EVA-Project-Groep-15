package hogent.group15.domain;

import android.os.AsyncTask;
import android.util.JsonReader;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Frederik on 10/20/2015.
 */
public class Backend {

    private URI backendServerUri;
    private static Backend backend;
    private final static String TAG = "BACKEND";
    private String email = "";

    private Backend() {
        backendServerUri = URI.create("http://192.168.0.252:8080/backend/api/");
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

    public AsyncTask<Void, Void, String> loginUser(final String email, final String password, final OnNetworkResponseListener<String, LoginResult> callback) {

        AsyncTask<Void, Void, String> task = new AsyncTask<Void, Void, String>() {

            private boolean isError;

            @Override
            protected String doInBackground(Void... params) {
                HttpURLConnection connection = null;
                try {
                    connection = (HttpURLConnection) backendServerUri.resolve("user/login").toURL().openConnection();
                    connection.addRequestProperty("Content-Type", "application/json");
                    connection.setRequestMethod("POST");
                    connection.setDoInput(true);
                    connection.setDoOutput(true);

                    PrintWriter out = new PrintWriter(connection.getOutputStream());

                    JSONObject user = new JSONObject();
                    user.put("email", email);
                    user.put("password", password);
                    out.println(user.toString());
                    out.flush();

                    Scanner in = new Scanner(connection.getInputStream());
                    StringBuilder dataIn = new StringBuilder();

                    while (in.hasNext()) {
                        dataIn.append(in.next());
                    }

                    Backend.this.email = email;
                    return dataIn.toString();
                } catch (JSONException e) {
                    Log.e(TAG, "Couldn't create JSON: " + e.getMessage());
                    isError = true;
                    return "json";
                } catch (IOException ioex) {

                    try {
                        if (connection != null && connection.getResponseCode() == 401) {
                            callback.onError(LoginResult.WRONG_CREDENTIALS);
                        } else {
                            callback.onError(LoginResult.NETWORK_ERROR);
                        }
                    } catch (IOException e) {
                        callback.onError(LoginResult.NETWORK_ERROR);
                    }
                    isError = true;
                    return "";
                } finally {
                    connection.disconnect();
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

    public AsyncTask<Void, Void, List<Challenge>> getDailyChallenges(final OnNetworkResponseListener<List<Challenge>, IOException> callback) {
        AsyncTask<Void, Void, List<Challenge>> task = new AsyncTask<Void, Void, List<Challenge>>() {

            private boolean isError;

            @Override
            protected List<Challenge> doInBackground(Void... params) {
                if (Backend.this.email == null || Backend.this.email.isEmpty()) {
                    throw new IllegalStateException("User should be logged in before requesting daily challenges");
                }

                HttpURLConnection connection = null;
                try {
                    connection = (HttpURLConnection) backendServerUri.resolve("user/" + Backend.this.email + "/daily").toURL().openConnection();
                    connection.addRequestProperty("Content-Type", "application/json");
                    connection.setRequestMethod("GET");
                    connection.setDoInput(true);

                    Scanner in = new Scanner(connection.getInputStream());
                    StringBuilder dataIn = new StringBuilder();

                    while (in.hasNextLine()) {
                        dataIn.append(in.nextLine());
                    }

                    List<Challenge> challenges = new ArrayList<>(3);
                    JsonReader reader = new JsonReader(new StringReader(dataIn.toString()));
                    reader.beginArray();
                    for (int i = 0; i < 3; i++) {
                        Challenge challenge = new Challenge();
                        reader.beginObject();
                        while (reader.hasNext()) {
                            String propertyName = reader.nextName();
                            if (propertyName.equals("imageUrl")) {
                                challenge.setHeaderImageUri(URI.create(reader.nextString()));
                            } else if (propertyName.equals("title")) {
                                challenge.setTitle(reader.nextString());
                            } else if (propertyName.equals("difficulty")) {
                                challenge.setScore(Challenge.Difficulty.getScoreFor(reader.nextString()));
                            } else if (propertyName.equals("id")) {
                                challenge.setId(reader.nextInt());
                            } else if (propertyName.equals("description")) {
                                challenge.setDetailedDescription(reader.nextString());
                            }
                        }
                        reader.endObject();

                        challenges.add(challenge);
                    }

                    reader.close();
                    return challenges;
                } catch (
                        IOException ioex
                        )

                {
                    callback.onError(ioex);
                    isError = true;
                    return new ArrayList<>();
                } finally

                {
                    connection.disconnect();
                }
            }

            @Override
            protected void onPostExecute(List<Challenge> list) {
                if (!isError) {
                    callback.onResponse(list);
                }
            }
        };

        task.execute();
        return task;
    }
}
