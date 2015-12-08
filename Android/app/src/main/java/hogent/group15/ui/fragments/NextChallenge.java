package hogent.group15.ui.fragments;

import android.content.Context;
import android.widget.FrameLayout;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import hogent.group15.service.Backend;
import hogent.group15.domain.Challenge;
import hogent.group15.ui.controls.list.ChallengeListEntry;
import hogent.group15.ui.R;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class NextChallenge extends FrameLayout {

    @Bind(R.id.first_challenge)
    public ChallengeListEntry firstChallenge;

    @Bind(R.id.second_challenge)
    public ChallengeListEntry secondChallenge;

    @Bind(R.id.third_challenge)
    public ChallengeListEntry thirdChallenge;

    public NextChallenge(Context context) {
        super(context);
        inflate(getContext(), R.layout.fragment_challenge_next, this);

        ButterKnife.bind(this);
        refreshChallenges();
    }

    public void refreshChallenges() {
        Backend.getBackend().getDailyChallenges(getContext(), new Callback<List<Challenge>>() {

            @Override
            public void success(List<Challenge> data, Response response) {
                firstChallenge.updateContents(data.get(0));
                secondChallenge.updateContents(data.get(1));
                thirdChallenge.updateContents(data.get(2));
            }

            @Override
            public void failure(final RetrofitError error) {
                /*NextChallengeFragment.this.getActivity().runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        Toast.makeText(NextChallengeFragment.this.getContext(), getString(R.string.network_error), Toast.LENGTH_LONG);
                        Log.e("NEXT CHALLENGES", "Couldn't retrieve new challenges: " + error.getMessage());
                    }
                });*/
            }
        });
    }
}
