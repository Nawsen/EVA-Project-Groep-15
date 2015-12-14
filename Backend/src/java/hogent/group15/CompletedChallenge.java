package hogent.group15;

import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 *
 * @author Frederik
 */
@Entity
public class CompletedChallenge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @ManyToOne
    private Challenge challenge;

    public CompletedChallenge() {
    }

    public CompletedChallenge(Challenge challenge) {
	this.challenge = challenge;
    }

    public Challenge getChallenge() {
	return challenge;
    }

    public void setChallenge(Challenge challenge) {
	this.challenge = challenge;
    }

    @Override
    public int hashCode() {
	int hash = 3;
	hash = 83 * hash + this.id;
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
	final CompletedChallenge other = (CompletedChallenge) obj;
	if (this.id != other.id) {
	    return false;
	}
	return true;
    }
}
