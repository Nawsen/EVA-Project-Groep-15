package hogent.group15;

import android.media.Image;

import java.net.URI;

/**
 * Created by Frederik on 10/11/2015.
 */
public class Challenge {

    private URI imageUri;
    private String title;
    private String shortDescription;
    private String detailedDescription;
    private int score;

    public Challenge() {
    }

    public Challenge(URI imageUri, String title, String shortDescription, String detailedDescription, int score) {
        this.imageUri = imageUri;
        this.title = title;
        this.shortDescription = shortDescription;
        this.detailedDescription = detailedDescription;
        this.score = score;
    }

    public String getDetailedDescription() {
        return detailedDescription;
    }

    public void setDetailedDescription(String detailedDescription) {
        this.detailedDescription = detailedDescription;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public URI getImageUri() {
        return imageUri;
    }

    public void setImageUri(URI imageUri) {
        this.imageUri = imageUri;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
