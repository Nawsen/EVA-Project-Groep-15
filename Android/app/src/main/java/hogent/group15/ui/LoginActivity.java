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

import com.facebook.FacebookSdk;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hogent.group15.Validator;
import hogent.group15.domain.Backend;
import hogent.group15.domain.OnNetworkResponseListener;
import hogent.group15.ui.util.ActionBarConfig;

public class LoginActivity extends AppCompatActivity implements Validator {

    @Bind(R.id.login_email)
    public EditText email;

    @Bind(R.id.login_password)
    public EditText password;

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
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        outState.putString("email", email.getText().toString());
    }

    public void onLogin(View v) {

        if (!validate()) {
            return;
        }

        Backend.getBackend().loginUser(email.getText().toString(), password.getText().toString(), new OnNetworkResponseListener<String, Backend.LoginResult>() {
            @Override
            public void onResponse(String data) {
                password.setText("");
                startActivity(new Intent(getApplicationContext(), MainMenuActivity.class));
            }

            @Override
            public void onError(final Backend.LoginResult result) {
                Log.e("LOGIN", "result: " + result);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        switch (result) {
                            case NETWORK_ERROR:
                                Toast.makeText(getApplicationContext(), R.string.network_error, Toast.LENGTH_LONG).show();
                                break;
                            case WRONG_CREDENTIALS:
                                Toast.makeText(getApplicationContext(), R.string.wrong_credentials, Toast.LENGTH_LONG).show();
                                break;
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
