/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hogent.group15;

import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

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

    public DailyChallenges() {
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

}
