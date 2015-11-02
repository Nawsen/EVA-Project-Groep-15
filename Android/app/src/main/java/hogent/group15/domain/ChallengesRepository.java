package hogent.group15.domain;

import android.util.Log;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import hogent.group15.Consumer;
import hogent.group15.ui.controls.ListEntry;
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
        // completedChallenges.add(new EmptyListEntry(MainMenuActivity.appContext, MainMenuActivity.appContext.getString(R.string.no_completed_challenges), android.R.drawable.ic_dialog_alert));
        completedChallenges.add(new Challenge(1, URI.create("http://www.evavzw.be/sites/default/files/styles/header_image/public/recipe/header/chili.jpg?itok=LLuipNqt"), "Chili sin Carne",
                "A detailed description of the preparation for Chili sin Carne", "HARD"));
        refreshCurrentChallenge();
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
        if(currentChallengeChangeCallback != null) {
            currentChallengeChangeCallback.consume(currentChallenge);
        }
    }
}
