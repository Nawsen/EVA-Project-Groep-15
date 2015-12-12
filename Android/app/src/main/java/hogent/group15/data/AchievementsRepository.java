package hogent.group15.data;

import java.util.ArrayList;
import java.util.List;

import hogent.group15.domain.Achievement;
import hogent.group15.ui.MainMenuActivity;
import hogent.group15.ui.controls.ListEntry;

/**
 * Created by Brent on 10/31/2015.
 */
public class AchievementsRepository {
    private static final AchievementsRepository INSTANCE = new AchievementsRepository();

    private List<ListEntry> achievements = new ArrayList<>();

    public static AchievementsRepository getInstance() {
        return INSTANCE;
    }

    private AchievementsRepository() {
        //achievements.add(new EmptyListEntry(MainMenuActivity.appContext, MainMenuActivity.appContext.getString(R.string.no_achievements), android.R.drawable.ic_dialog_alert));
        achievements.add(new Achievement(MainMenuActivity.appContext, "Achievement #1"));
        achievements.add(new Achievement(MainMenuActivity.appContext, "Achievement #2", "Description of the second achievement"));
    }

    public List<ListEntry> getAchievements() {
        return achievements;
    }
}
