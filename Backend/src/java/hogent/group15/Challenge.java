package hogent.group15;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;

/**
 *
 * @author Frederik
 */
@Entity
@NamedQuery(name = "Challenge.findAll", query = "SELECT c FROM Challenge c")
public class Challenge implements Serializable {

    public enum Difficulty {

	EASY, MEDIUM, HARD
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String title;
    private String description;
    private String imageUrl;

    @ManyToMany(fetch = FetchType.LAZY)
    private List<User> users;

    @Enumerated(EnumType.ORDINAL)
    private Difficulty difficulty;

    public Challenge() {
    }

    public Challenge(int id, String title, String description, String imageUrl, Difficulty difficulty) {
	this.id = id;
	this.title = title;
	this.description = description;
	this.imageUrl = imageUrl;
	this.difficulty = difficulty;
    }

    public Challenge(String title, String description, String imageUrl, Difficulty difficulty) {
	this.title = title;
	this.description = description;
	this.imageUrl = imageUrl;
	this.difficulty = difficulty;
    }

    public int getId() {
	return id;
    }

    public void setId(int id) {
	this.id = id;
    }

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

    public String getImageUrl() {
	return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
	this.imageUrl = imageUrl;
    }

    public Difficulty getDifficulty() {
	return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
	this.difficulty = difficulty;
    }

}
