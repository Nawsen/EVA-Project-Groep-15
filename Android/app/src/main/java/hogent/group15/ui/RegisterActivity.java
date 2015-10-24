package hogent.group15.ui;

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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hogent.group15.Consumer;
import hogent.group15.StringInterpolator;
import hogent.group15.domain.Backend;
import hogent.group15.domain.Sex;
import hogent.group15.domain.VegetarianGrade;
import hogent.group15.ui.fragments.RegisterMainFragment;
import hogent.group15.ui.fragments.RegisterPasswordFragment;
import hogent.group15.ui.util.ActionBarConfig;

public class RegisterActivity extends AppCompatActivity {

    private RegisterMainFragment mainFragment;
    private RegisterPasswordFragment passwordFragment;

    private static final String MAIN_FRAGMENT = "mainFragment";

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

    @Bind(R.id.register_submit)
    public Button registerButton;

    @OnClick(R.id.register_submit)
    public void onRegister(Button registerButton) {
        if (passwordFragment == null) {
            passwordFragment = new RegisterPasswordFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, passwordFragment)
                    .setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right).commit();
            registerButton.setText(R.string.register_register_button);
        }

        /*Backend.getBackend().registerUser(mainFragment.firstName.getText(), mainFragment.lastName.getText(), mainFragment.email.getText(), mainFragment.getSelectedSex(), mainFragment.password.getText(),mainFragment.getSelectedGrade(), new Consumer<String>() {

            @Override
            public void consume(String s) {
                Log.i("REGISTRATION", "Result: " + s);
            }
        });*/
    }
}
