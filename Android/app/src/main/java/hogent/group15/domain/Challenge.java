package hogent.group15.domain;

import java.io.Serializable;
import java.net.URI;

/**
 * Created by Frederik on 10/11/2015.
 */
public class Challenge implements Serializable {

    public enum Difficulty {

        EASY(1), MEDIUM(2), HARD(3);

        private int score;
        private Difficulty(int score) {
            this.score = score;
        }

        public int getScore() {
            return score;
        }

        public static int getScoreFor(String difficulty) {
            return Difficulty.valueOf(difficulty).getScore();
        }
    }

    private int id;
    private URI headerImageUri;
    private String title;
    private String detailedDescription;
    private int score;

    public Challenge() {
    }

    public Challenge(int id, URI headerImageUri, String title, String detailedDescription, int score) {
        this.id = id;
        this.headerImageUri = headerImageUri;
        this.title = title;
        this.detailedDescription = detailedDescription;
        this.score = score;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
