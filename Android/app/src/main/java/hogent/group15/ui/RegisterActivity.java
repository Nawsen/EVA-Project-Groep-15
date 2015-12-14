package hogent.group15.ui;

import android.content.Intent;
import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.login.LoginManager;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hogent.group15.service.Backend;
import hogent.group15.domain.User;
import hogent.group15.service.LoginResponse;
import hogent.group15.ui.fragments.RegisterMainFragment;
import hogent.group15.ui.fragments.RegisterPasswordFragment;
import hogent.group15.ui.util.ActionBarConfig;
import retrofit.Callback;
import retrofit.ResponseCallback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedByteArray;

public class RegisterActivity extends AppCompatActivity {

    private enum Mode {
        MAIN, PASSWORD
    }

    private RegisterMainFragment mainFragment;
    private RegisterPasswordFragment passwordFragment;

    private static final String MAIN_FRAGMENT = "mainFragment";

    private Mode currentMode = Mode.MAIN;

    private boolean isFacebookRegister;

    private LoginResponse fbLoginData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        if (savedInstanceState == null) {
            mainFragment = new RegisterMainFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, mainFragment).commit();
        } else {
            mainFragment = (RegisterMainFragment) getSupportFragmentManager().getFragment(savedInstanceState, MAIN_FRAGMENT);
        }

        ButterKnife.bind(this);

        if (getIntent() != null) {
            if (getIntent().getSerializableExtra("facebookData") != null) {
                LoginResponse lr = (LoginResponse) getIntent().getSerializableExtra("facebookData");
                Bundle b = new Bundle();
                isFacebookRegister = true;
                fbLoginData = lr;
                b.putSerializable("facebookData", lr);
                if (mainFragment != null)
                    mainFragment.setArguments(b);
            }
        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        getSupportFragmentManager().putFragment(outState, MAIN_FRAGMENT, mainFragment);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return ActionBarConfig.getInstance(this).onCreateOptionsMenu(menu, this, R.id.item_logout, R.id.item_share, R.id.item_search);
    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        if (fragment instanceof RegisterMainFragment) {
            mainFragment = (RegisterMainFragment) fragment;
        }
    }

    @Override
    public void onBackPressed() {
        if (currentMode == Mode.PASSWORD) {
            registerButton.setText(R.string.next);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, mainFragment)
                    .setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right).commit();
            currentMode = Mode.MAIN;
        } else {
            super.onBackPressed();
        }
    }

    @Bind(R.id.register_submit)
    public Button registerButton;

    @OnClick(R.id.register_submit)
    public void onRegister(final Button registerButton) {
        if (isFacebookRegister) {
            User user = new User(mainFragment.email.getText().toString(), "", mainFragment.firstName.getText().toString(), mainFragment.lastName.getText().toString(), mainFragment.getSelectedSex(), mainFragment.getSelectedGrade());
            doRegister(user);

        } else if (currentMode == Mode.MAIN && mainFragment.validate()) {
            doPreRegister(mainFragment.email.getText().toString(), new ResponseCallback() {

                @Override
                public void failure(RetrofitError error) {
                    if (error.getMessage() != null) {
                        String body = new String(((TypedByteArray) error.getResponse().getBody()).getBytes());
                        if (body.equalsIgnoreCase("used")) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    mainFragment.email.setError("This email is already in use!");
                                }
                            });
                        } else {
                            success(null);
                        }
                    }
                }

                @Override
                public void success(Response response) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (passwordFragment == null) {
                                passwordFragment = new RegisterPasswordFragment();
                            }

                            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, passwordFragment)
                                    .setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right).commit();
                            registerButton.setText(R.string.register_register_button);
                            currentMode = Mode.PASSWORD;
                        }
                    });
                }
            });
        } else if (currentMode == Mode.PASSWORD && passwordFragment.validate()) {
            User user = new User(mainFragment.email.getText().toString(), passwordFragment.getPassword(), mainFragment.firstName.getText().toString(), mainFragment.lastName.getText().toString(), mainFragment.getSelectedSex(), mainFragment.getSelectedGrade());
            doRegister(user);
        }
    }

    private void doPreRegister(String email, final ResponseCallback callback) {
        User u = new User();
        u.setEmail(email);
        registerButton.setEnabled(false);
        Backend.getBackend(this).registerUser(u, new ResponseCallback() {
            @Override
            public void success(Response response) {
                callback.success(response);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        registerButton.setEnabled(true);
                    }
                });
            }

            @Override
            public void failure(RetrofitError error) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        registerButton.setEnabled(true);
                    }
                });
                callback.failure(error);
            }
        });
    }

    private void doRegister(User user) {
        registerButton.setEnabled(false);
        Backend.getBackend(this).registerUser(user, new ResponseCallback() {
            @Override
            public void success(Response response) {
                if (isFacebookRegister) {
                    if (fbLoginData != null && fbLoginData.getFbAccessToken() != null) {
                        Backend.getBackend(getApplicationContext()).loginUser(new User(String.valueOf(fbLoginData.getId()), fbLoginData.getFbAccessToken(), true),
                                new Callback<LoginResponse>() {
                                    @Override
                                    public void success(LoginResponse loginResponse, Response response) {
                                        startActivity(new Intent(getApplicationContext(), MainMenuActivity.class));
                                    }

                                    @Override
                                    public void failure(RetrofitError error) {
                                        Log.i("RETROFIT", "Retrofit failed to login!");
                                    }
                                });
                    }
                } else {
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class).putExtra("username", mainFragment.email.getText().toString()));
                }
            }

            @Override
            public void failure(RetrofitError error) {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), R.string.network_error, Toast.LENGTH_LONG).show();
                        registerButton.setEnabled(true);
                    }
                });
            }
        });
    }
}
