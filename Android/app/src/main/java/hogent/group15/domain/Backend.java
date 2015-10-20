package hogent.group15.domain;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
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

    private URL backendServerUri;
    private static Backend backend;
    private final static String TAG = "BACKEND";

    private Backend() {
        try {
            backendServerUri = URI.create("http://localhost:8080/Backend/api").toURL();
        } catch (MalformedURLException e) {
            Log.e(TAG, "Server URI is malformed");
        }
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
                    URLConnection connection = backendServerUri.openConnection();
                    PrintWriter out = new PrintWriter(connection.getOutputStream());

                    JSONObject user = new JSONObject();
                    user.put("firstName", firstName);
                    user.put("lastName", lastName);
                    user.put("email", email);
                    user.put("sex", sex.asInt());
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
                    return "unknown";
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
