package hogent.group15.ui.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import hogent.group15.data.ChallengesRepository;
import hogent.group15.ui.R;
import hogent.group15.ui.util.ListEntryAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class CompletedChallengesFragment extends Fragment {

    private RecyclerView recyclerView;

    public CompletedChallengesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onStart() {
        super.onStart();
        recyclerView = (RecyclerView) this.getView().findViewById(R.id.completedChallengesListView);
        LinearLayoutManager llm = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(llm);
        final RecyclerView.Adapter adapter = new ListEntryAdapter(getActivity(), ChallengesRepository.getInstance(getContext()).getCompletedChallenges());
        recyclerView.setAdapter(adapter);

        Log.i("CompletedChallenges", "Starting refresh of completed challenges");
        ChallengesRepository.getInstance(getContext()).refreshCompletedChallenges(new Runnable() {
            @Override
            public void run() {
                ChallengesRepository.getInstance(getContext()).setOnCompletedChallengesChanged(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_completed_challenges, container, false);
    }

    // Unnecessary since ChallengesRepository saves completed challenges
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}
