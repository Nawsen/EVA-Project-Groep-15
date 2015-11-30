package hogent.group15.domain;

import android.app.Activity;
import android.net.Uri;
import android.support.v4.app.Fragment;

import com.facebook.share.model.ShareContent;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;

/**
 * Created by Brent on 11/30/2015.
 */
public class Domain {

    public static ActivePage activePage = ActivePage.NONE;
    public static Fragment activeFragment = null;

    public enum ActivePage {
        ONGOING_CHALLENGES,
        COMPLETED_CHALLENGES,
        ACHIEVEMENTS,
        NONE
    }

    public static void createShareDialogForChallenge(Activity a, Challenge c) {
        ShareDialog.show(a, new ShareLinkContent.Builder()
                        .setContentTitle(c.getTitle())
                        .setContentDescription(c.getDetailedDescription())
                        .setContentUrl(Uri.parse("http://evavzw.be"))
                        .build()
        );
    }

    public static void createShareDialogForChallenge(Fragment f, Challenge c) {
        ShareDialog.show(f, new ShareLinkContent.Builder()
                        .setContentTitle(c.getTitle())
                        .setContentDescription(c.getDetailedDescription())
                        .setContentUrl(Uri.parse("http://evavzw.be"))
                        .build()
        );
    }
}
