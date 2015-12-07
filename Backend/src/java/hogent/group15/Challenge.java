package hogent.group15;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Transient;

/**
 *
 * @author Frederik
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "Challenge.findAll", query = "SELECT c FROM Challenge c"),
    @NamedQuery(name = "Challenge.findUsable", query = "SELECT c FROM Challenge c WHERE c.usable = TRUE")
})
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

    private boolean usable = true;

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

    public boolean isUsable() {
	return usable;
    }

    public void setUsable(boolean usable) {
	this.usable = usable;
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

    public void setUsers(List<User> users) {
	this.users = users;
    }

    public List<User> getUsers() {
	return users;
    }

    @Transient
    private Date date;

    public void setDate(Date date) {
	this.date = date;
    }

    public Date getDate() {
	return date;
    }
}
