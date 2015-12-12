package hogent.group15;

import java.util.Arrays;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author Wannes
 */
@Entity
@Table(name = "TBL_DAILY")
public class DailyChallenges {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne
    private Challenge first;

    @ManyToOne
    private Challenge second;

    @ManyToOne
    private Challenge third;

    private java.sql.Date challengesDate;
    
    @Transient
    private boolean detailed = true;

    public DailyChallenges() {
    }
    
    public DailyChallenges(Challenge first, Challenge second, Challenge third) {
	this.first = first;
	this.second = second;
	this.third = third;
    }

    public boolean isDetailed() {
	return detailed;
    }

    public void setDetailed(boolean detailed) {
	this.detailed = detailed;
    }
    
    public int getId() {
	return id;
    }

    public void setId(int id) {
	this.id = id;
    }

    public Challenge getFirst() {
	return first;
    }

    public void setFirst(Challenge first) {
	this.first = first;
    }

    public Challenge getSecond() {
	return second;
    }

    public void setSecond(Challenge second) {
	this.second = second;
    }

    public Challenge getThird() {
	return third;
    }

    public void setThird(Challenge third) {
	this.third = third;
    }

    public java.sql.Date getDate() {
	return challengesDate;
    }

    public void setDate(java.sql.Date date) {
	this.challengesDate = date;
    }
    
    public List<Challenge> getChallenges() {
	return Arrays.asList(new Challenge[]{
	    getFirst(), getSecond(), getThird()
	});
    }

}
