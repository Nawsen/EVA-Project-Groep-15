package hogent.group15.domain;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;
import java.net.URI;
import java.util.Date;

import hogent.group15.ui.R;
import hogent.group15.ui.controls.list.ChallengeListEntry;
import hogent.group15.ui.controls.ListEntry;
import hogent.group15.ui.util.ListEntryAdapter;

/**
 * Created by Frederik on 10/11/2015.
 */
@DatabaseTable(tableName = "Challenge")
public class Challenge implements Serializable, ListEntry<ChallengeListEntry> {

    @Override
    public void bindToView(ListEntryAdapter.EntryViewHolder<ChallengeListEntry> holder) {
        holder.getView().setShowAcceptChallengeButton(showAcceptChallengeButton);
        holder.getView().updateContents(this);
    }

    public enum Difficulty {

        EASY(R.string.difficulty_easy), MEDIUM(R.string.difficulty_medium), HARD(R.string.diffculty_hard);

        private int score;

        private Difficulty(int score) {
            this.score = score;
        }

        public int getResourceId() {
            return score;
        }

        public static int getResourceIdFor(String difficulty) {
            return Difficulty.valueOf(difficulty).getResourceId();
        }
    }

    @DatabaseField(uniqueCombo = true)
    int id;

    @DatabaseField(generatedId = true)
    int dbId;

    @SerializedName("imageUrl")
    @DatabaseField(columnName = "imageUri", canBeNull = false)
    String headerImageUri;

    @DatabaseField(columnName = "title", canBeNull = false)
    String title;

    @SerializedName("description")
    @DatabaseField(columnName = "description", canBeNull = false)
    String detailedDescription;

    @SerializedName("difficulty")
    @DatabaseField(columnName = "difficulty", canBeNull = false)
    String difficulty;

    @DatabaseField(columnName = "date", canBeNull = false, uniqueCombo = true)
    String date;

    @DatabaseField(columnName = "token", canBeNull = false, uniqueCombo = true)
    String jsonWebToken;

    private boolean showAcceptChallengeButton = true;

    public Challenge() {
    }

    public Challenge(int id, String headerImageUri, String title, String detailedDescription, String difficulty) {
        this.id = id;
        this.headerImageUri = headerImageUri;
        this.title = title;
        this.detailedDescription = detailedDescription;
        this.difficulty = difficulty;
    }

    public String getJsonWebToken() {
        return jsonWebToken;
    }

    public void setJsonWebToken(String jsonWebToken) {
        this.jsonWebToken = jsonWebToken;
    }

    public String getDetailedDescription() {
        return detailedDescription;
    }

    public void setDetailedDescription(String detailedDescription) {
        this.detailedDescription = detailedDescription;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getHeaderImageUri() {
        return headerImageUri;
    }

    public void setHeaderImageUri(String headerImageUri) {
        this.headerImageUri = headerImageUri;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean shouldShowAcceptChallengeButton() {
        return showAcceptChallengeButton;
    }

    public void setShowAcceptChallengeButton(boolean showAcceptChallengeButton) {
        this.showAcceptChallengeButton = showAcceptChallengeButton;
    }
}
