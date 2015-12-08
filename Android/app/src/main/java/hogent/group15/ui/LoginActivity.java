package hogent.group15.ui;

import android.content.Intent;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.internal.PermissionType;
import com.facebook.login.LoginBehavior;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hogent.group15.Validator;
import hogent.group15.domain.Backend;
import hogent.group15.domain.Domain;
import hogent.group15.domain.JsonWebToken;
import hogent.group15.domain.OnNetworkResponseListener;
import hogent.group15.domain.User;
import hogent.group15.ui.util.ActionBarConfig;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class LoginActivity extends AppCompatActivity implements Validator {

    @Bind(R.id.login_email)
    public EditText email;

    @Bind(R.id.login_password)
    public EditText password;

    @Bind(R.id.facebook_login_button)
    public LoginButton loginButton;

    private CallbackManager cm;

    private ProfileTracker profileTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        if (getIntent() != null) {
            String username = getIntent().getStringExtra("username");
            email.setText(username);
        } else if (savedInstanceState != null) {
            if (savedInstanceState.containsKey("email")) {
                email.setText(savedInstanceState.getString("email"));
            }
        }

        loginButton.setReadPermissions("email", "public_profile", "user_about_me", "user_location", "user_friends");

        cm = CallbackManager.Factory.create();
        loginButton.registerCallback(cm, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                System.out.println("LOGIN RESULT " + loginResult.getAccessToken().getToken());
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });

        loginButton.setLoginBehavior(LoginBehavior.NATIVE_WITH_FALLBACK);
        profileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {
                if (currentProfile != null)
                    System.out.println(currentProfile.getId() + " is the ID");
                Domain.facebookProfile = currentProfile;
            }
        };
        profileTracker.startTracking();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        cm.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        outState.putString("email", email.getText().toString());
    }

    public void onLogin(View v) {

        if (!validate()) {
            return;
        }

        Backend.getBackend().loginUser(new User(email.getText().toString(), password.getText().toString()), new Callback<JsonWebToken>() {
            @Override
            public void success(JsonWebToken data, Response response) {
                password.setText("");
                startActivity(new Intent(getApplicationContext(), MainMenuActivity.class));
            }

            @Override
            public void failure(final RetrofitError error) {
                // Log.e("LOGIN", "result: " + error.getResponse());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        switch (error.getResponse().getStatus()) {
                            case 401:
                                Toast.makeText(getApplicationContext(), R.string.wrong_credentials, Toast.LENGTH_LONG).show();
                                break;
                            default:
                                Toast.makeText(getApplicationContext(), R.string.network_error, Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return ActionBarConfig.onCreateOptionsMenu(menu, this, R.id.item_logout);
    }

    @OnClick(R.id.login_register)
    public void onRegister(View v) {
        startActivity(new Intent(this, RegisterActivity.class));
    }

    @Override
    public boolean validate() {
        if (email.getText().toString().isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email.getText()).matches()) {
            email.setError(getString(R.string.validation_submit_email));
            return false;
        }

        if (password.getText().toString().isEmpty()) {
            password.setError(getString(R.string.validation_password));
            return false;
        }

        return true;
    }
}
