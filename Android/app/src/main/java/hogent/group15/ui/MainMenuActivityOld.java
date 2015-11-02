package hogent.group15.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import hogent.group15.ui.fragments.NextChallenge;
import hogent.group15.ui.util.ActionBarConfig;

public class MainMenuActivityOld extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu_old);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return ActionBarConfig.onCreateOptionsMenu(menu, this);
    }

    public void onGetChallenge(View v) {
        startActivity(new Intent(this, NextChallenge.class));
    }

    public void onGetProgress(View v) {
        startActivity(new Intent(this, MainMenuActivity.class));
    }
}
