package hogent.group15;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.transaction.Transactional;

/**
 *
 * @author Wannes
 */
@ApplicationScoped
@ConcurrencyManagement(ConcurrencyManagementType.BEAN)
public class ChallengeCache {

    private List<Challenge> allChallenges;

    @PersistenceContext(type = PersistenceContextType.TRANSACTION)
    private EntityManager em;

    @PostConstruct
    @Transactional
    public void init() {
	allChallenges = em.createNamedQuery("Challenge.findUsable", Challenge.class).getResultList();
    }

    public void addToCache(Challenge ch) {
	allChallenges.add(ch);
    }

    public void removeFromCache(Challenge ch) {
	allChallenges.remove(ch);
    }

    public DailyChallenges createDailyChallenges(User user) {
	if (user.getDailyChallenges() != null && user.getDailyChallenges().getDate() != null) {
	    Calendar daily = Calendar.getInstance();
	    daily.setTime(user.getDailyChallenges().getDate());
	    Calendar today = Calendar.getInstance();

	    if (daily.get(Calendar.YEAR) == today.get(Calendar.YEAR) && daily.get(Calendar.MONTH) == today.get(Calendar.MONTH)
		    && daily.get(Calendar.DAY_OF_MONTH) == today.get(Calendar.DAY_OF_MONTH)) {
		return user.getDailyChallenges();
	    }
	}

	if (user.getDailyChallenges() == null) {
	    user.setDailyChallenges(new DailyChallenges());
	}

	if (allChallenges.size() > 2) {
	    user.getDailyChallenges().setFirst(allChallenges.get(0));
	    user.getDailyChallenges().setSecond(allChallenges.get(1));
	    user.getDailyChallenges().setThird(allChallenges.get(2));
	    user.getDailyChallenges().setDate(new java.sql.Date(new Date().getTime()));
	}

	return user.getDailyChallenges();
    }

    public Challenge getChallenge(User user, int id) {
	//Check first for dailychallenges as this will be the most commonly used
	if (user.getDailyChallenges() == null) {
	    return null;
	}

	if (user.getDailyChallenges().getFirst().getId() == id) {
	    return user.getDailyChallenges().getFirst();
	}

	if (user.getDailyChallenges().getSecond().getId() == id) {
	    return user.getDailyChallenges().getSecond();
	}

	if (user.getDailyChallenges().getThird().getId() == id) {
	    return user.getDailyChallenges().getThird();
	}

	if (user.getCurrentChallenge() != null && user.getCurrentChallenge().getId() == id) {
	    return user.getCurrentChallenge();
	}

	//if we didn't find it here user asks info about a completed challenge
	for (Challenge c : user.getCompletedChallenges()) {
	    if (c.getId() == id) {
		return c;
	    }
	}
	//if we get here it means the user may not view the challenge info yet
	return null;
    }

    public int amount() {
	return allChallenges.size();
    }

}
