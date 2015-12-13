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
import hogent.group15.ui.controls.list.AchievementListEntry;
import hogent.group15.ui.util.ListEntryAdapter;

public class Achievement implements ListEntry<AchievementListEntry> {

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
    public void bindToView(ListEntryAdapter.EntryViewHolder<AchievementListEntry> holder) {
        holder.getView().updateContents(this);
    }
}
