package hogent.group15;

import java.io.Serializable;
import java.net.URI;

/**
 * Created by Frederik on 10/11/2015.
 */
public class Challenge implements Serializable {

    private URI thumbnailImageUri;
    private URI headerImageUri;
    private String title;
    private String shortDescription;
    private String detailedDescription;
    private int score;

    public Challenge() {
    }

    public Challenge(URI thumbnailImageUri, URI headerImageUri, String title, String shortDescription, String detailedDescription, int score) {
        this.thumbnailImageUri = thumbnailImageUri;
        this.headerImageUri = headerImageUri;
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

    public URI getThumbnailImageUri() {
        return thumbnailImageUri;
    }

    public void setThumbnailImageUri(URI thumbnailImageUri) {
        this.thumbnailImageUri = thumbnailImageUri;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public URI getHeaderImageUri() {
        return headerImageUri;
    }

    public void setHeaderImageUri(URI headerImageUri) {
        this.headerImageUri = headerImageUri;
    }
}
