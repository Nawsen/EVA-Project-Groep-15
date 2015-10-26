package hogent.group15.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import hogent.group15.domain.Backend;
import hogent.group15.domain.Challenge;
import hogent.group15.domain.OnNetworkResponseListener;
import hogent.group15.ui.ChallengeView;
import hogent.group15.ui.R;

public class NextChallengeFragment extends Fragment {

    @Bind(R.id.first_challenge)
    public ChallengeView firstChallenge;

    @Bind(R.id.second_challenge)
    public ChallengeView secondChallenge;

    @Bind(R.id.third_challenge)
    public ChallengeView thirdChallenge;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_challenge_next, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ButterKnife.bind(this, getView());
        refreshChallenges();
    }

    public void refreshChallenges() {
        Backend.getBackend().getDailyChallenges(new OnNetworkResponseListener<List<Challenge>, IOException>() {

            @Override
            public void onResponse(List<Challenge> data) {
                firstChallenge.updateContents(data.get(0));
                secondChallenge.updateContents(data.get(1));
                thirdChallenge.updateContents(data.get(2));
            }

            @Override
            public void onError(final IOException ex) {
                NextChallengeFragment.this.getActivity().runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        Toast.makeText(NextChallengeFragment.this.getContext(), getString(R.string.network_error), Toast.LENGTH_LONG);
                        Log.e("NEXT CHALLENGES", "Couldn't retrieve new challenges: " + ex.getMessage());
                    }
                });
            }
        });
    }
}
