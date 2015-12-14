package hogent.group15.ui.fragments;


import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.UiThread;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.test.UiThreadTest;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import hogent.group15.Consumer;
import hogent.group15.data.AchievementsRepository;
import hogent.group15.data.ChallengesRepository;
import hogent.group15.domain.Achievement;
import hogent.group15.ui.R;
import hogent.group15.ui.StringInterpolator;
import hogent.group15.ui.util.ListEntryAdapter;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class AchievementsFragment extends Fragment {

    @Bind(R.id.achievementsListView)
    public RecyclerView recyclerView;

    @Bind(R.id.challengeProgress)
    ProgressBar progressBar;

    @Bind(R.id.challenges_left)
    TextView challengesLeft;

    private String challengesLeftUninterpolated;

    public AchievementsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onStart() {
        super.onStart();
        ButterKnife.bind(this, getView());
        challengesLeftUninterpolated = challengesLeft.getText().toString();
        ChallengesRepository.getInstance(getContext()).setOnProgressUpdate(new Consumer<Integer>() {
            @Override
            public void consume(final Integer integer) {
                if (progressBar != null) {
                    progressBar.setProgress(integer);
                    getActivity().runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            if (integer < 21) {
                                challengesLeft.setText(StringInterpolator.interpolate(challengesLeftUninterpolated, 21 - integer));
                            } else {
                                challengesLeft.setText(R.string.all_challenges_completed);
                            }
                        }
                    });

                }
            }
        });

        ChallengesRepository.getInstance(getContext()).updateCompletedCount(new Callback<Integer>() {
            @Override
            public void success(Integer integer, Response response) {
                Log.d("CompletedChallenges", "Setting progress to: " + integer);
                progressBar.setProgress(integer);
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        AchievementsRepository.getInstance(getContext()).refreshAchievements(new Callback<List<Achievement>>() {
            @Override
            public void success(List<Achievement> achievements, Response response) {
                LinearLayoutManager llm = null;
                Configuration config = getActivity().getResources().getConfiguration();
                //llm = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                llm = new GridLayoutManager(getContext(), config.smallestScreenWidthDp > 720 ? 2 : 1);
                recyclerView.setLayoutManager(llm);

                recyclerView.setAdapter(new ListEntryAdapter(getActivity(), AchievementsRepository.getInstance(getContext()).getAchievements()));
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(getContext(), "Couldn't get all your achievements", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_achievements, container, false);
    }

    // Unnecessary since AchievementsRepository saves achievements
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}
