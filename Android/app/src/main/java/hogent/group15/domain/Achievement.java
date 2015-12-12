package hogent.group15.domain;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.annotations.SerializedName;

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

        if (this.description == null || this.description.isEmpty()) {
            description.setVisibility(View.INVISIBLE);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(title.getLayoutParams());
            params.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
            params.addRule(RelativeLayout.RIGHT_OF, R.id.achievement_badge);
            title.setLayoutParams(params);
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
