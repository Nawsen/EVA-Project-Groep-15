package hogent.group15.domain;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.annotations.SerializedName;

import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareButton;

import hogent.group15.ui.R;
import hogent.group15.ui.controls.ListEntry;

public class Achievement implements ListEntry {

    @SerializedName("name")
    String title;
    String description;
    int score;

    private transient int badge;
    private transient int box;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getBadge() {
        return badge;
    }

    public void setBadge(int badge) {
        this.badge = badge;
    }

    public int getBox() {
        return box;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setBox(int box) {
        this.box = box;
    }

    public Achievement() {
        this("");
    }

    public Achievement(String title) {
        this(title, "");
    }

    public Achievement(String title, String description) {
        this(title, description, R.drawable.easy);
    }

    public Achievement(String title, String description, int badge) {
        this(title, description, badge, 0);
    }

    public Achievement(String title, String description, int badge, int box) {
        this.title = title;
        this.description = description;
        this.badge = badge;
        this.box = box;
    }

    @Override
    public View retrieveView(LayoutInflater inflater, ViewGroup parent) {
        View view = inflater.inflate(R.layout.achievement_entry, null);

        TextView description = (TextView) view.findViewById(R.id.achievement_description);
        TextView title = (TextView) view.findViewById(R.id.achievement_title);
        title.setText(this.title);
        ShareButton shareButton = (ShareButton) view.findViewById(R.id.achievement_share_button);

        shareButton.setShareContent(new ShareLinkContent.Builder()
                .setContentTitle(getTitle())
                .setContentDescription(getDescription().isEmpty() ? "No description" : getDescription())
                .setImageUrl(Uri.parse("http://www.evavzw.be/sites/all/themes/wieni-subtheme/apple-touch-icon-152x152.png"))
                .setContentUrl(Uri.parse("http://evavzw.be"))
                .build());

        shareButton.setCompoundDrawables(null, null, null, null);

        if (this.description == null || this.description.isEmpty()) {
            description.setVisibility(View.INVISIBLE);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(title.getLayoutParams());
            params.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
            params.addRule(RelativeLayout.RIGHT_OF, R.id.achievement_badge);
            title.setLayoutParams(params);

            RelativeLayout.LayoutParams buttonParams = new RelativeLayout.LayoutParams(shareButton.getLayoutParams());
            buttonParams.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
            buttonParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);

            Resources r = inflater.getContext().getResources();
            int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, r.getDisplayMetrics());
            buttonParams.setMargins(0, 0, px, 0);
            shareButton.setLayoutParams(buttonParams);
        } else {
            title.setVisibility(View.VISIBLE);
            description.setText(this.description);
        }

        view.findViewById(R.id.achievement_outerContainer).setBackgroundResource(box);

        int badge = R.drawable.tree_1;

        if (score >= 21) {
            badge = R.drawable.tree_5;
        } else if (score >= 17) {
            badge = R.drawable.tree_4;
        } else if (score >= 6) {
            badge = R.drawable.tree_3;
        } else if (score > 1) {
            badge = R.drawable.tree_2;
        } else {
            badge = R.drawable.tree_1;
        }

        ((ImageView) view.findViewById(R.id.achievement_badge)).setImageResource(badge);
        return view;
    }
}
