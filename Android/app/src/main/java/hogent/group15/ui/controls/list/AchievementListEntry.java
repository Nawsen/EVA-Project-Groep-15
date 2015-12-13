package hogent.group15.ui.controls.list;

import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;
import android.util.TypedValue;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareButton;

import butterknife.Bind;
import butterknife.ButterKnife;
import hogent.group15.domain.Achievement;
import hogent.group15.ui.R;

/**
 * Created by Brent on 12/13/2015.
 */
public class AchievementListEntry extends FrameLayout {

    private View root;

    @Bind(R.id.achievement_description)
    public TextView description;

    @Bind(R.id.achievement_title)
    public TextView title;

    @Bind(R.id.achievement_share_button)
    public ShareButton shareButton;

    @Bind(R.id.achievement_badge)
    public ImageView achievementBadge;

    public AchievementListEntry(Context context) {
        super(context);
        initialize();
    }

    private void initialize() {
        root = inflate(getContext(), R.layout.achievement_entry, this);
        ButterKnife.bind(this);
    }

    public void updateContents(Achievement achievement) {
        title.setText(achievement.getTitle());
        shareButton.setShareContent(new ShareLinkContent.Builder()
                .setContentTitle(achievement.getTitle())
                .setContentDescription(achievement.getDescription().isEmpty() ? "No description" : achievement.getDescription())
                .setImageUrl(Uri.parse("http://www.evavzw.be/sites/all/themes/wieni-subtheme/apple-touch-icon-152x152.png"))
                .setContentUrl(Uri.parse("http://evavzw.be"))
                .build());

        shareButton.setCompoundDrawables(null, null, null, null);

        if (achievement.getDescription() == null || achievement.getDescription().isEmpty()) {
            description.setVisibility(View.INVISIBLE);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(title.getLayoutParams());
            params.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
            params.addRule(RelativeLayout.RIGHT_OF, R.id.achievement_badge);
            title.setLayoutParams(params);

            RelativeLayout.LayoutParams buttonParams = new RelativeLayout.LayoutParams(shareButton.getLayoutParams());
            buttonParams.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
            buttonParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);

            Resources r = getContext().getResources();
            int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, r.getDisplayMetrics());
            buttonParams.setMargins(0, 0, px, 0);
            shareButton.setLayoutParams(buttonParams);
        } else {
            title.setVisibility(View.VISIBLE);
            description.setText(achievement.getDescription());
        }

        findViewById(R.id.achievement_outerContainer).setBackgroundResource(achievement.getBox());

        int badge = R.drawable.tree_1;

        if (achievement.getScore() >= 21) {
            badge = R.drawable.tree_5;
        } else if (achievement.getScore() >= 17) {
            badge = R.drawable.tree_4;
        } else if (achievement.getScore() >= 6) {
            badge = R.drawable.tree_3;
        } else if (achievement.getScore() > 1) {
            badge = R.drawable.tree_2;
        } else {
            badge = R.drawable.tree_1;
        }

        achievementBadge.setImageResource(badge);
    }


}
