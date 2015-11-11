package hogent.group15.domain;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.net.URI;

import hogent.group15.ui.R;
import hogent.group15.ui.controls.list.ChallengeListEntry;
import hogent.group15.ui.controls.ListEntry;

/**
 * Created by Frederik on 10/11/2015.
 */
public class Challenge implements Serializable, ListEntry {

    @Override
    public View retrieveView(LayoutInflater inflater, ViewGroup parent) {
        ChallengeListEntry view = new ChallengeListEntry(inflater.getContext());
        view.setShowAcceptChallengeButton(showAcceptChallengeButton);
        view.updateContents(this);
        return view;
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

    int id;

    @SerializedName("imageUrl")
    URI headerImageUri;

    String title;

    @SerializedName("description")
    String detailedDescription;

    @SerializedName("difficulty")
    String difficulty;

    private boolean showAcceptChallengeButton = true;

    public Challenge() {
    }

    public Challenge(int id, URI headerImageUri, String title, String detailedDescription, String difficulty) {
        this.id = id;
        this.headerImageUri = headerImageUri;
        this.title = title;
        this.detailedDescription = detailedDescription;
        this.difficulty = difficulty;
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

    public URI getHeaderImageUri() {
        return headerImageUri;
    }

    public void setHeaderImageUri(URI headerImageUri) {
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
