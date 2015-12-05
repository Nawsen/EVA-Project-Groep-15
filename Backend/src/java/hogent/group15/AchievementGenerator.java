package hogent.group15;

import java.util.HashMap;
import java.util.Map;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Frederik
 */
@ApplicationScoped
@ConcurrencyManagement(ConcurrencyManagementType.BEAN)
public class AchievementGenerator {

    @PersistenceContext
    private EntityManager manager;

    private final Map<Integer, Achievement.Builder> achievementCompletedChallenges = new HashMap<Integer, Achievement.Builder>() {
	{
	    put(1, new Achievement.Builder().name("Beginner").description("Je hebt de eerste uitdaging voltooid!").score(1).type(Achievement.AchievementType.COMPLETED));
	    put(2, new Achievement.Builder().name("Teruggekomen").description("Je hebt al twee uitdagingen voltooid, doe zo verder!").score(2).type(Achievement.AchievementType.COMPLETED));
	    put(5, new Achievement.Builder().name("Onderweg").description("Je hebt de eerste stappen genomen en bent op weg om gezonder te eten!").score(5).type(Achievement.AchievementType.COMPLETED));
	    put(10, new Achievement.Builder().name("Halfweg").description("Je bent halfweg!").score(10).type(Achievement.AchievementType.COMPLETED));
	    put(15, new Achievement.Builder().name("Bijna").description("Nog even doorzetten, je bent er bijna!").score(15).type(Achievement.AchievementType.COMPLETED));
	    put(20, new Achievement.Builder().name("Nog eentje").description("Nog één te gaan, je kan het!").score(20).type(Achievement.AchievementType.COMPLETED));
	    put(21, new Achievement.Builder().name("Proficiat").description("Proficiat! Je hebt de 21 dagen voltooid nu is het aan jou om deze levensstijl verder te zetten!").score(21).type(Achievement.AchievementType.COMPLETED));
	}
    };

    public void generateAchievements(User user, Achievement.AchievementType change) {
	if (change == Achievement.AchievementType.COMPLETED) {
	    int completedChallengesCount = user.getCompletedChallenges().size();
	    if (achievementCompletedChallenges.containsKey(completedChallengesCount)) {
		Achievement achievement = achievementCompletedChallenges.get(completedChallengesCount).user(user).build();
		user.getAchievements().add(achievement);
	    }

	    manager.merge(user);
	} else if (change == Achievement.AchievementType.ACCEPTED) {
	    boolean any = user.getAchievements().stream().anyMatch(a -> a.getType() == Achievement.AchievementType.ACCEPTED);
	    if (!any) {
		user.getAchievements().add(new Achievement("Eerste uitdaging geaccepteerd", "Je hebt de eerste uitdaging geaccepteerd!", 1, Achievement.AchievementType.ACCEPTED, user));
		manager.merge(user);
	    }
	}
    }
}
