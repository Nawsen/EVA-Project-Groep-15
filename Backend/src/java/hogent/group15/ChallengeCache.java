package hogent.group15;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.Schedule;
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

    @Schedule(hour = "0,2,4,6,8,10,12,14,16,18,20,22")
    public void onSchedule() {
	init();
    }
    
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

    public List<DailyChallenges> createDailyChallenges(User user, int days) {
	List<DailyChallenges> result = new ArrayList<>();

	for (int i = 0; i < days; i++) {
	    Calendar today = Calendar.getInstance();
	    today.add(Calendar.DAY_OF_MONTH, i);
	    result.add(user.getDailyChallengesForDay(today).orElse(generateDailyChallengesFor(today, result)));
	}

	return result;
    }
    
    private DailyChallenges generateDailyChallengesFor(Calendar date, List<DailyChallenges> existing) {
	List<Challenge> challenges = existing.stream().map(DailyChallenges::getChallenges).flatMap(List::stream).collect(Collectors.toList());
	Map<Integer, Integer> groupMap = new HashMap<>();
	challenges.forEach(c -> groupMap.compute(c.getGroup(), (k, v) -> v == null || v == 0 ? 1 : v + 1));
	List<Challenge> result = new ArrayList<>();
	
	Random rand = new Random();

	while (result.size() < 3) {
	    Challenge randomChallenge = randomChallenge(rand);
	    if (result.contains(randomChallenge)) {
		continue;
	    }
	    
	    float score = 4f;
	    
	    if (challenges.contains(randomChallenge)) {
		score -= 3.9;
	    } else {
		int total = groupMap.keySet().stream().map(groupMap::get).reduce(0, (lhs, rhs) -> lhs + rhs);
		total = total == 0 ? 1 : total;
		score -= 4 * groupMap.getOrDefault(randomChallenge.getGroup(), 0) / total;
		
		if (result.stream().anyMatch(c -> c.getDifficulty() == randomChallenge.getDifficulty())) {
		    score = Math.max(0, score - 3.5f);
		}
	    }
	    
	    if (rand.nextFloat() * 4 < score) {
		result.add(randomChallenge);
	    }
	}
	
	DailyChallenges finalResult = new DailyChallenges(result.get(0), result.get(1), result.get(2));
	finalResult.setDate(new Date(date.getTimeInMillis()));
	em.persist(finalResult);
	return finalResult;
    }
    
    private Challenge randomChallenge(Random random) {
	return allChallenges.get(random.nextInt(allChallenges.size()));
    }

    public Challenge getChallenge(int id) {
	return em.find(Challenge.class, id);
    }

    public int amount() {
	return allChallenges.size();
    }

}
