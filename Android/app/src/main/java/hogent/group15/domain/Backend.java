package hogent.group15.domain;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

import hogent.group15.AsyncUtil;
import hogent.group15.Consumer;

/**
 * Created by Frederik on 10/20/2015.
 */
public class Backend {

    private URI backendServerUri;
    private static Backend backend;
    private final static String TAG = "BACKEND";

    private Backend() {
        backendServerUri = URI.create("http://178.62.232.69/backend/api/");
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
}
