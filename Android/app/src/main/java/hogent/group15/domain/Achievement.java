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

import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareButton;

import org.w3c.dom.Text;

import butterknife.ButterKnife;
import hogent.group15.ui.R;
import hogent.group15.ui.controls.ListEntry;

public class Achievement implements ListEntry {

    private String title;
    private String description;

    private Drawable badge;
    private Drawable box;

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

    public Drawable getBadge() {
        return badge;
    }

    public void setBadge(Drawable badge) {
        this.badge = badge;
    }

    public Drawable getBox() {
        return box;
    }

    public void setBox(Drawable box) {
        this.box = box;
    }

    public Achievement(Context context, String title) {
        this(context, title, "");
    }

    public Achievement(Context context, String title, String description) {
        this(context, title, description, R.drawable.easy);
    }

    public Achievement(Context context, String title, String description, int badge) {
        this(context, title, description, badge, R.drawable.double_line);
    }

    public Achievement(Context context, String title, String description, int badge, int box) {
        this.title = title;
        this.description = description;
        this.badge = ContextCompat.getDrawable(context, badge);
        this.box = ContextCompat.getDrawable(context, box);
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

        view.findViewById(R.id.achievement_outerContainer).setBackground(box);
        ((ImageView) view.findViewById(R.id.achievement_badge)).setImageDrawable(badge);
        return view;
    }
}
