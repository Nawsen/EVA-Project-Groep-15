package hogent.group15.data;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import hogent.group15.domain.Achievement;
import hogent.group15.service.Backend;
import hogent.group15.service.BackendAPI;
import hogent.group15.ui.MainMenuActivity;
import hogent.group15.ui.R;
import hogent.group15.ui.controls.ListEntry;
import hogent.group15.ui.controls.list.EmptyListEntry;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Brent on 10/31/2015.
 */
public class AchievementsRepository {

    private static AchievementsRepository instance;

    private List<ListEntry> achievements = new ArrayList<>();
    private Context context;

    public static AchievementsRepository getInstance(Context context) {
        if (instance == null) {
            instance = new AchievementsRepository(context);
        }

        return instance;
    }

    private AchievementsRepository(Context context) {
        this.context = context;
        achievements.add(new EmptyListEntry(context, context.getString(R.string.no_achievements), android.R.drawable.ic_dialog_alert));
    }

    public void refreshAchievements(final Callback<List<Achievement>> onReceive) {
        Backend.getBackend(context).getAchievements(new Callback<List<Achievement>>() {
            @Override
            public void success(List<Achievement> achievements, Response response) {
                getAchievements().clear();
                if (achievements.isEmpty()) {
                    final ListEntry emptyEntry = new EmptyListEntry(MainMenuActivity.appContext, MainMenuActivity.appContext.getString(R.string.no_achievements), android.R.drawable.ic_dialog_alert);
                    getAchievements().add(emptyEntry);
                } else {
                    getAchievements().addAll(achievements);
                }
                onReceive.success(achievements, response);
            }

            @Override
            public void failure(RetrofitError error) {
                onReceive.failure(error);
            }
        });
    }

    public List<ListEntry> getAchievements() {
        return achievements;
    }
}
