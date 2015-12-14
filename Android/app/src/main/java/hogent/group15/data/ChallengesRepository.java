package hogent.group15.data;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import hogent.group15.Consumer;
import hogent.group15.domain.User;
import hogent.group15.service.Backend;
import hogent.group15.domain.Challenge;
import hogent.group15.ui.LoginActivity;
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

    private static ChallengesRepository INSTANCE;

    private List<ListEntry> completedChallenges = new ArrayList<>();
    private Challenge currentChallenge;
    private Context context;

    private int completedChallengesCount;

    public static ChallengesRepository getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new ChallengesRepository(context);
        }

        return INSTANCE;
    }

    private ChallengesRepository(Context context) {
        this.context = context;
        refreshCurrentChallenge();
        updateCompletedCount(null);
    }

    public int getCompletedChallengesCount() {
        return completedChallengesCount;
    }

    public void incrementCompletedChallenges() {
        completedChallengesCount++;
        if (onProgressUpdate != null) {
            onProgressUpdate.consume(completedChallengesCount);
        }
    }

    public void updateCompletedCount(final Callback<Integer> callback) {
        Backend.getBackend(context).getUserDetails(new Callback<User>() {
            @Override
            public void success(User user, Response response) {
                if(user == null) {
                    Backend.getBackend(context).logoutUser();
                    context.startActivity(new Intent(context, LoginActivity.class));
                    return;
                }
                completedChallengesCount = user.getCompletedCount();
                if (onProgressUpdate != null) {
                    onProgressUpdate.consume(completedChallengesCount);
                }

                if (callback != null) {
                    callback.success(completedChallengesCount, response);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if (callback != null) {
                    callback.failure(error);
                }
            }
        });
    }

    private Consumer<Integer> onProgressUpdate;
    public void setOnProgressUpdate(Consumer<Integer> onProgressUpdate) {
        this.onProgressUpdate = onProgressUpdate;
    }

    public void refreshCompletedChallenges(final Runnable callback) {
        final ListEntry emptyEntry = new EmptyListEntry(context, context.getString(R.string.no_completed_challenges), android.R.drawable.ic_dialog_alert);
        completedChallenges.clear();
        Backend.getBackend(context).getCompletedChallenges(new Callback<List<Challenge>>() {
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
        Backend.getBackend(context).getAcceptedChallenge(new Callback<Challenge>() {
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
        callback.run();
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
