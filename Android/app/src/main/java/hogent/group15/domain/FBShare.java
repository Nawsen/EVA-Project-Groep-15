package hogent.group15.domain;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;

import com.facebook.share.model.ShareContent;
import com.facebook.share.widget.ShareButton;
import com.facebook.share.widget.ShareDialog;

/**
 * Created by Brent on 12/12/2015.
 */
public class FBShare {

    private static final FBShare INSTANCE = new FBShare();

    private Activity currentActivity;

    private Fragment currentFragment;

    private ShareContent shareAction;

    private FBShare() {

    }

    public static FBShare with(Activity a) {
        INSTANCE.currentFragment = null;
        INSTANCE.currentActivity = a;
        return INSTANCE;
    }

    public static FBShare with() {
        return INSTANCE;
    }

    public static FBShare with(Fragment f) {
        INSTANCE.currentActivity = null;
        INSTANCE.currentFragment = f;
        return INSTANCE;
    }

    public void setShareAction(ShareContent r) {
        shareAction = r;
    }

    public void share() {
        if (shareAction != null) {
            if (currentActivity != null) {
                ShareDialog.show(currentActivity, shareAction);
            } else if (currentFragment != null) {
                ShareDialog.show(currentFragment, shareAction);
            }
        }

    }
}
