package hogent.group15.ui.util;

import android.app.Activity;
import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import hogent.group15.domain.FBShare;
import hogent.group15.service.Backend;
import hogent.group15.ui.LoginActivity;
import hogent.group15.ui.R;
import hogent.group15.ui.fragments.OngoingChallengeFragment;

/**
 * Created by Frederik on 10/19/2015.
 */
public class ActionBarConfig {

    private static ActionBarConfig actionBarConfig;

    public static ActionBarConfig getInstance(Context context) {
        if (actionBarConfig == null) {
            actionBarConfig = new ActionBarConfig(context);
        }

        return actionBarConfig;
    }

    private Context context;

    private ActionBarConfig(Context context) {
        this.context = context;
    }

    public boolean onCreateOptionsMenu(Menu menu, AppCompatActivity activity, int... exclusions) {
        onCreateOptionsMenu(menu, activity);
        for (int id : exclusions) {
            menu.findItem(id).setVisible(false);
        }

        return true;
    }

    public boolean onCreateOptionsMenu(Menu menu, AppCompatActivity activity) {
        activity.getMenuInflater().inflate(R.menu.default_menu, menu);
        ActionBar bar = activity.getSupportActionBar();
        bar.setHomeButtonEnabled(true);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.item_logout) {
            Backend.getBackend(context).logoutUser();
            context.startActivity(new Intent(context, LoginActivity.class));
            return true;

        } else if (item.getItemId() == R.id.item_share) {
            FBShare.with().share();
            return true;
        } else {
            return false;
        }
    }
}
