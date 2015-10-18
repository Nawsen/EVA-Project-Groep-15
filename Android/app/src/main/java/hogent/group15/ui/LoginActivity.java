package hogent.group15.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.facebook.FacebookSdk;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_login);
        //((EditText)findViewById(R.id.login_email)).setError("Mail cannot be empty!");
    }

    public void onLogin(View v) {
        startActivity(new Intent(this, MainMenuActivity.class));
    }
}
