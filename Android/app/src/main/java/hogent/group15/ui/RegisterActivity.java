package hogent.group15.ui;

import android.content.Intent;
import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Button;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hogent.group15.domain.Backend;
import hogent.group15.domain.User;
import hogent.group15.ui.fragments.RegisterMainFragment;
import hogent.group15.ui.fragments.RegisterPasswordFragment;
import hogent.group15.ui.util.ActionBarConfig;
import retrofit.Callback;
import retrofit.ResponseCallback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class RegisterActivity extends AppCompatActivity {

    private enum Mode {
        MAIN, PASSWORD
    }

    private RegisterMainFragment mainFragment;
    private RegisterPasswordFragment passwordFragment;

    private static final String MAIN_FRAGMENT = "mainFragment";

    private Mode currentMode = Mode.MAIN;

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
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        getSupportFragmentManager().putFragment(outState, MAIN_FRAGMENT, mainFragment);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return ActionBarConfig.onCreateOptionsMenu(menu, this);
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
    public void onRegister(Button registerButton) {
        if (currentMode == Mode.MAIN && mainFragment.validate()) {
            if (passwordFragment == null) {
                passwordFragment = new RegisterPasswordFragment();
            }

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, passwordFragment)
                    .setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right).commit();
            registerButton.setText(R.string.register_register_button);
            currentMode = Mode.PASSWORD;
        } else if (currentMode == Mode.PASSWORD && passwordFragment.validate()) {
            User user = new User(mainFragment.email.getText().toString(), passwordFragment.getPassword(), mainFragment.firstName.getText().toString(), mainFragment.lastName.getText().toString(), mainFragment.getSelectedSex(), mainFragment.getSelectedGrade());
            Backend.getBackend().registerUser(user, new ResponseCallback() {
                @Override
                public void success(Response response) {
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class).putExtra("username", mainFragment.email.getText().toString()));
                }

                @Override
                public void failure(RetrofitError error) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), R.string.network_error, Toast.LENGTH_LONG).show();
                        }
                    });
                }
            });
        }
    }
}
