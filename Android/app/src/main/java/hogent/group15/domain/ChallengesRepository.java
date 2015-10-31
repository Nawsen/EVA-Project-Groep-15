package hogent.group15.domain;

import java.util.ArrayList;
import java.util.List;

import hogent.group15.ui.MainMenuActivity;
import hogent.group15.ui.R;
import hogent.group15.ui.controls.ListEntry;
import hogent.group15.ui.controls.list.EmptyListEntry;

/**
 * Created by Brent on 10/31/2015.
 */
public class ChallengesRepository {
    private static final ChallengesRepository INSTANCE = new ChallengesRepository();

    private List<ListEntry> challenges = new ArrayList<>();

    public static ChallengesRepository getInstance() {
        return INSTANCE;
    }

    private ChallengesRepository() {
        challenges.add(new EmptyListEntry(MainMenuActivity.appContext, MainMenuActivity.appContext.getString(R.string.no_completed_challenges), android.R.drawable.ic_dialog_alert));
    }

    public List<ListEntry> getChallenges() {
        return challenges;
    }
}
