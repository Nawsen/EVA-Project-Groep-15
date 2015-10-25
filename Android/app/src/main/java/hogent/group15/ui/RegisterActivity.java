package hogent.group15.ui;

import android.content.Intent;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hogent.group15.Consumer;
import hogent.group15.StringInterpolator;
import hogent.group15.domain.Backend;
import hogent.group15.domain.OnNetworkResponseListener;
import hogent.group15.domain.Sex;
import hogent.group15.domain.VegetarianGrade;
import hogent.group15.ui.fragments.RegisterMainFragment;
import hogent.group15.ui.fragments.RegisterPasswordFragment;
import hogent.group15.ui.util.ActionBarConfig;

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
            Backend.getBackend().registerUser(mainFragment.firstName.getText(), mainFragment.lastName.getText(), mainFragment.email.getText(), mainFragment.getSelectedSex(),
                    passwordFragment.getPassword(), mainFragment.getSelectedGrade(), new OnNetworkResponseListener<String, IOException>() {

                        @Override
                        public void onResponse(String data) {
                            startActivity(new Intent(getApplicationContext(), LoginActivity.class).putExtra("username", mainFragment.email.getText().toString()));
                        }

                        @Override
                        public void onError(IOException ex) {
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
