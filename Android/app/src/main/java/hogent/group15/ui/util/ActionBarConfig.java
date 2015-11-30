package hogent.group15.ui.util;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.facebook.share.widget.ShareDialog;

import hogent.group15.androidapp.CurrentChallenge;
import hogent.group15.domain.Challenge;
import hogent.group15.domain.Domain;
import hogent.group15.ui.R;
import hogent.group15.ui.fragments.OngoingChallengeFragment;

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
        switch (item.getItemId()) {
            case R.id.item_share:
                switch (Domain.activePage) {
                    case ONGOING_CHALLENGES:
                        shareOngoingChallenge();
                        break;
                }
                break;
        }
        return false;
    }

    private static void shareOngoingChallenge() {
        OngoingChallengeFragment ocf = (OngoingChallengeFragment) Domain.activeFragment;
        Challenge ch = ocf.getCurrentChallenge().getCurrentChallenge();
        Domain.createShareDialogForChallenge(ocf, ch);
    }
}
