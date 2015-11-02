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
import hogent.group15.ui.controls.list.ChallengeListEntry;
import hogent.group15.ui.R;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class NextChallengeFragment extends Fragment {

    @Bind(R.id.first_challenge)
    public ChallengeListEntry firstChallenge;

    @Bind(R.id.second_challenge)
    public ChallengeListEntry secondChallenge;

    @Bind(R.id.third_challenge)
    public ChallengeListEntry thirdChallenge;

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

        if(savedInstanceState != null) {
            if (savedInstanceState.containsKey("first")) {
                firstChallenge.updateContents((Challenge) savedInstanceState.getSerializable("first"));
            }

            if (savedInstanceState.containsKey("second")) {
                firstChallenge.updateContents((Challenge) savedInstanceState.getSerializable("second"));
            }

            if (savedInstanceState.containsKey("third")) {
                firstChallenge.updateContents((Challenge) savedInstanceState.getSerializable("third"));
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("first", firstChallenge.getChallenge());
        outState.putSerializable("second", secondChallenge.getChallenge());
        outState.putSerializable("third", thirdChallenge.getChallenge());
    }

    public void refreshChallenges() {
        Backend.getBackend().getDailyChallenges(new Callback<List<Challenge>>() {

            @Override
            public void success(List<Challenge> data, Response response) {
                firstChallenge.updateContents(data.get(0));
                secondChallenge.updateContents(data.get(1));
                thirdChallenge.updateContents(data.get(2));
            }

            @Override
            public void failure(final RetrofitError error) {
                NextChallengeFragment.this.getActivity().runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        Toast.makeText(NextChallengeFragment.this.getContext(), getString(R.string.network_error), Toast.LENGTH_LONG);
                        Log.e("NEXT CHALLENGES", "Couldn't retrieve new challenges: " + error.getMessage());
                    }
                });
            }
        });
    }
}
