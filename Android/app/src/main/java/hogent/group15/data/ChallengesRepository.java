package hogent.group15.data;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import hogent.group15.Consumer;
import hogent.group15.service.Backend;
import hogent.group15.domain.Challenge;
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
public class ChallengesRepository {

    private static final ChallengesRepository INSTANCE = new ChallengesRepository();

    private List<ListEntry> completedChallenges = new ArrayList<>();
    private Challenge currentChallenge;

    public static ChallengesRepository getInstance() {
        return INSTANCE;
    }

    private ChallengesRepository() {
        refreshCurrentChallenge();
    }

    public void refreshCompletedChallenges(final Runnable callback) {
        final ListEntry emptyEntry = new EmptyListEntry(MainMenuActivity.appContext, MainMenuActivity.appContext.getString(R.string.no_completed_challenges), android.R.drawable.ic_dialog_alert);
        completedChallenges.clear();
        Backend.getBackend().getCompletedChallenges(new Callback<List<Challenge>>() {
            @Override
            public void success(List<Challenge> challenges, Response response) {
                if (challenges.isEmpty()) {
                    completedChallenges.add(emptyEntry);
                }

                completedChallenges.addAll(challenges);
                callback.run();
            }

            @Override
            public void failure(RetrofitError error) {
                completedChallenges.add(emptyEntry);
                callback.run();
            }
        });
    }

    public void refreshCurrentChallenge() {
        Backend.getBackend().getAcceptedChallenge(new Callback<Challenge>() {
            @Override
            public void success(Challenge challenge, Response response) {
                setCurrentChallenge(challenge);
            }

            @Override
            public void failure(RetrofitError error) {
                currentChallenge = null;
                Log.e(Backend.TAG, "Couldn't retrieve current challenge: " + error.getResponse());
            }
        });
    }

    private Runnable onCompletedChallengesChanged;

    public void setOnCompletedChallengesChanged(Runnable callback) {
        onCompletedChallengesChanged = callback;
    }

    public void addCompletedChallenge(Challenge challenge) {
        if (completedChallenges.size() == 1 && completedChallenges.get(0) instanceof EmptyListEntry) {
            completedChallenges.clear();
        }

        completedChallenges.add(challenge);
        if (onCompletedChallengesChanged != null) {
            onCompletedChallengesChanged.run();
        }
    }

    public List<ListEntry> getCompletedChallenges() {
        return completedChallenges;
    }

    public Challenge getCurrentChallenge() {
        return currentChallenge;
    }

    private Consumer<Challenge> currentChallengeChangeCallback;

    public void setOnCurrentChallengeChange(Consumer<Challenge> callback) {
        this.currentChallengeChangeCallback = callback;
        callback.consume(currentChallenge);
    }

    public void setCurrentChallenge(Challenge currentChallenge) {
        this.currentChallenge = currentChallenge;
        if (currentChallengeChangeCallback != null) {
            currentChallengeChangeCallback.consume(currentChallenge);
        }
    }
}
