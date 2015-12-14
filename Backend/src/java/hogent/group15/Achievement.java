package hogent.group15;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author Frederik
 */
@Entity
public class Achievement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String description;
    private int score;

    @Enumerated(EnumType.ORDINAL)
    private AchievementType type;

    public enum AchievementType {

	COMPLETED, ACCEPTED, CANCELLED
    }

    public Achievement() {
    }

    public Achievement(String name, String description, int score, AchievementType type) {
	this.name = name;
	this.description = description;
	this.score = score;
	this.type = type;
    }

    public AchievementType getType() {
	return type;
    }

    public void setType(AchievementType type) {
	this.type = type;
    }

    public String getDescription() {
	return description;
    }

    public void setDescription(String description) {
	this.description = description;
    }

    public int getId() {
	return id;
    }

    public void setId(int id) {
	this.id = id;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public int getScore() {
	return score;
    }

    public void setScore(int score) {
	this.score = score;
    }

    @Override
    public int hashCode() {
	int hash = 5;
	hash = 59 * hash + this.id;
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
	final Achievement other = (Achievement) obj;
	if (this.id != other.id) {
	    return false;
	}
	return true;
    }

    public static class Builder {

	private String name;
	private String description;
	private int score;
	private User user;
	private AchievementType type;

	public Builder() {
	}

	public Builder name(String name) {
	    this.name = name;
	    return this;
	}

	public Builder description(String description) {
	    this.description = description;
	    return this;
	}

	public Builder score(int score) {
	    this.score = score;
	    return this;
	}

	public Builder type(AchievementType type) {
	    this.type = type;
	    return this;
	}

	public Achievement build() {
	    return new Achievement(name, description, score, type);
	}
    }
}
