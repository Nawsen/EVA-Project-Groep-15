package hogent.group15.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import hogent.group15.ui.NextChallengeActivity;
import hogent.group15.ui.R;

public class MainMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
    }

    public void onGetChallenge(View v) {
        startActivity(new Intent(this, NextChallengeActivity.class));
    }

    public void onGetProgress(View v) {

    }
}
