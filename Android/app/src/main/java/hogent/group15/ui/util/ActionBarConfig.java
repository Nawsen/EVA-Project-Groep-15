package hogent.group15.ui.util;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import hogent.group15.ui.R;

/**
 * Created by Frederik on 10/19/2015.
 */
public class ActionBarConfig {

    public static boolean onCreateOptionsMenu(Menu menu, AppCompatActivity activity, int... exclusions) {
        onCreateOptionsMenu(menu, activity);
        for (int id : exclusions) {
            menu.findItem(id).setVisible(false);
        }

        return true;
    }

    public static boolean onCreateOptionsMenu(Menu menu, AppCompatActivity activity) {
        activity.getMenuInflater().inflate(R.menu.default_menu, menu);
        ActionBar bar = activity.getSupportActionBar();
        bar.setHomeButtonEnabled(true);
        return true;
    }

    public static boolean onOptionsItemSelected(MenuItem item) {

        return false;
    }
}
