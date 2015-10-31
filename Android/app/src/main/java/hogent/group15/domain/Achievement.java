package hogent.group15.domain;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

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

        ((TextView) view.findViewById(R.id.achievement_title)).setText(title);
        if (description == null || description.isEmpty()) {
            view.findViewById(R.id.achievement_description).setVisibility(View.INVISIBLE);
            ((TextView) view.findViewById(R.id.achievement_description)).setText("");
        } else {
            view.findViewById(R.id.achievement_title).setVisibility(View.VISIBLE);
            ((TextView) view.findViewById(R.id.achievement_description)).setText(description);
        }

        view.findViewById(R.id.achievement_outerContainer).setBackground(box);
        ((ImageView) view.findViewById(R.id.achievement_badge)).setImageDrawable(badge);
        return view;
    }
}
