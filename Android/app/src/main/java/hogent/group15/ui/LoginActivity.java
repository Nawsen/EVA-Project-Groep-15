package hogent.group15.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.facebook.FacebookSdk;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hogent.group15.ui.controls.EvaButton;
import hogent.group15.ui.util.ActionBarConfig;

public class LoginActivity extends AppCompatActivity {

    @Bind(R.id.login_email)
    public EditText email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        if (getIntent() != null) {
            String username = getIntent().getStringExtra("username");
            email.setText(username);
        }
    }

    public void onLogin(View v) {
        startActivity(new Intent(this, MainMenuActivity.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return ActionBarConfig.onCreateOptionsMenu(menu, this, R.id.item_logout);
    }

    @OnClick(R.id.login_register)
    public void onRegister(View v) {
        startActivity(new Intent(this, RegisterActivity.class));
    }
}
