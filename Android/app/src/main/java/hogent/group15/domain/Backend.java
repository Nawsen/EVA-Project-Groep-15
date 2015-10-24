package hogent.group15.domain;

import android.os.AsyncTask;
import android.util.Log;

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
        backendServerUri = URI.create("http://192.168.0.252:8080/backend/api/");
    }

    public static Backend getBackend() {
        if (backend == null) {
            backend = new Backend();
        }

        return backend;
    }

    public AsyncTask<Void, Void, String> registerUser(final CharSequence firstName, final CharSequence lastName, final CharSequence email, final Sex sex, final CharSequence password, final VegetarianGrade grade, final Consumer<String> onComplete) {
        AsyncTask<Void, Void, String> task = new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                try {
                    HttpURLConnection connection = (HttpURLConnection) backendServerUri.resolve("users/register").toURL().openConnection();
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
                    return "json";
                } catch (IOException ioex) {
                    return "io{" + ioex.getMessage() + "}";
                }
            }

            @Override
            protected void onPostExecute(String s) {
                onComplete.consume(s);
            }
        };

        task.execute();
        return task;
    }
}
