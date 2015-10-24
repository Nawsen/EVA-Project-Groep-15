/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hogent.group15;

import java.time.LocalDate;
import java.util.List;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Wannes
 */
@Singleton
public class ChallengeCache {

    private List<Challenge> allChallenges;

    @PersistenceContext
    private EntityManager em;

    public ChallengeCache() {
        allChallenges = em.createNamedQuery("Challenge.findAll", Challenge.class).getResultList();
        //Some demo challenges
        String besch = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. "
                + "Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. "
                + "Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. "
                + "Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.";
        allChallenges.add(new Challenge("Maak eens een volledig groen gerechtje", besch , "/demourl.jpg", Challenge.Difficulty.EASY));
        allChallenges.add(new Challenge("Ga eens naar een restaurant", besch , "/demourl.jpg", Challenge.Difficulty.HARD));
        allChallenges.add(new Challenge("kom eens naar een kookevent", besch , "/demourl.jpg", Challenge.Difficulty.MEDIUM));
    }

    public DailyChallenges createDailyChallenges(User user) {
        if (user.getDailyChallenges() == null) {
            user.setDailyChallenges(new DailyChallenges());
        }
        if (user.getDailyChallenges().getDate().isBefore(LocalDate.now())) {
            return user.getDailyChallenges();
        }
        if (allChallenges.size() > 2) {
            user.getDailyChallenges().setFirst(allChallenges.get(0));
            user.getDailyChallenges().setSecond(allChallenges.get(1));
            user.getDailyChallenges().setThird(allChallenges.get(2));
            user.getDailyChallenges().setDate(LocalDate.now());
        }
        return user.getDailyChallenges();
    }

    public Challenge doesUserHasChallenge(User user, int id) {
        //Check first for dailychallenges as this will be the most commonly used
        if (user.getDailyChallenges().getFirst().getId() == id) {
            return user.getDailyChallenges().getFirst();
        }
        if (user.getDailyChallenges().getSecond().getId() == id) {
            return user.getDailyChallenges().getSecond();
        }
        if (user.getDailyChallenges().getThird().getId() == id) {
            return user.getDailyChallenges().getThird();
        }
        if (user.getCurrentChallenge().getId()==id){
            return user.getCurrentChallenge();
        }
        //if we didn't find it here user asks info about a completed challenge
        for(Challenge c:user.getCompletedChallenges()){
            if (c.getId() == id){
                return c;
            }
        }
        //if we get here it means the user may not view the challenge info yet
        return null;
    }

}