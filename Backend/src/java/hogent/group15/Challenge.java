package hogent.group15;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;
import javax.persistence.Column;
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
import org.hibernate.validator.constraints.Length;

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
    
    
    @Column(length = 10000)
    private String description;
    
    private String imageUrl;

    @ManyToMany(fetch = FetchType.LAZY)
    private List<User> users;

    @Enumerated(EnumType.ORDINAL)
    private Difficulty difficulty;
    
    @Column(name = "ChallengeGroup")
    private int group;

    private boolean usable = true;

    public Challenge() {
    }

    public Challenge(int id, String title, String description, String imageUrl, Difficulty difficulty, int group) {
	this(title, description, imageUrl, difficulty, group);
	this.id = id;
    }

    public Challenge(String title, String description, String imageUrl, Difficulty difficulty, int group) {
	this.title = title;
	this.description = description;
	this.imageUrl = imageUrl;
	this.difficulty = difficulty;
	this.group = group;
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

    public int getGroup() {
	return group;
    }

    public void setGroup(int group) {
	this.group = group;
    }
    
    @Override
    public int hashCode() {
	int hash = 3;
	hash = 47 * hash + this.id;
	return hash;
    }

    @Override
    public boolean equals(Object obj) {
	if (obj == null) {
	    return false;
	}
	if (getClass() != obj.getClass()) {
	    return false;
	}
	final Challenge other = (Challenge) obj;
	if (this.id != other.id) {
	    return false;
	}
	return true;
    }
}
