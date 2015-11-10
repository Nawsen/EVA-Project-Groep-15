package hogent.group15;

import java.time.LocalDate;
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
	allChallenges = em.createNamedQuery("Challenge.findAll", Challenge.class).getResultList();
    }

    public void addToCache(Challenge ch) {
	allChallenges.add(ch);
    }

    public DailyChallenges createDailyChallenges(User user) {
	if (user.getDailyChallenges() != null && user.getDailyChallenges().getDate() != null && user.getDailyChallenges().getDate().equals(new java.sql.Date(new Date().getTime()))) {
	    return user.getDailyChallenges();
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
    public int amount(){
        return allChallenges.size();
    }

}
