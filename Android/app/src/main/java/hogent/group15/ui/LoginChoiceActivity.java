package hogent.group15.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class LoginChoiceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_choice);
    }

    public void onAlternativeLoginClick(View v) {
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
    }
}
