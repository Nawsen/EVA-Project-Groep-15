package hogent.group15.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import hogent.group15.Consumer;
import hogent.group15.domain.CurrentChallenge;
import hogent.group15.domain.Challenge;
import hogent.group15.data.ChallengesRepository;
import hogent.group15.ui.util.ActionBarConfig;

/**
 * Created by Frederik on 11/2/2015.
 */
public class OngoingChallengeFragment extends Fragment {

    private NextChallenge nextChallenge;
    private CurrentChallenge currentChallenge;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        nextChallenge = new NextChallenge(getContext());
        currentChallenge = new CurrentChallenge(getContext());
        RelativeLayout viewGroup = new RelativeLayout(getContext());

        nextChallenge.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        currentChallenge.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        viewGroup.addView(nextChallenge);
        viewGroup.addView(currentChallenge);

        nextChallenge.setVisibility(View.GONE);
        currentChallenge.setVisibility(View.GONE);

        return viewGroup;
    }

    @Override
    public void onStart() {
        super.onStart();
        ChallengesRepository.getInstance(getContext()).setOnCurrentChallengeChange(new Consumer<Challenge>() {

            @Override
            public void consume(Challenge challenge) {
                if (challenge != null) {
                    currentChallenge.setVisibility(View.VISIBLE);
                    currentChallenge.updateContents(challenge);
                    nextChallenge.setVisibility(View.GONE);
                } else {
                    currentChallenge.setVisibility(View.GONE);
                    nextChallenge.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    public NextChallenge getNextChallenge() {
        return nextChallenge;
    }

    public CurrentChallenge getCurrentChallenge() {
        return currentChallenge;
    }

    public void setNextChallenge(NextChallenge nextChallenge) {
        this.nextChallenge = nextChallenge;
    }

    public void setCurrentChallenge(CurrentChallenge currentChallenge) {
        this.currentChallenge = currentChallenge;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}
