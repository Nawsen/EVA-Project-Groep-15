package hogent.group15.ui.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import hogent.group15.domain.ChallengesRepository;
import hogent.group15.ui.R;
import hogent.group15.ui.util.ListEntryAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class CompletedChallengesFragment extends Fragment {

    private ListView listView;

    public CompletedChallengesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onStart() {
        super.onStart();
        listView = (ListView) this.getView().findViewById(R.id.completedChallengesListView);

        ChallengesRepository.getInstance().refreshCompletedChallenges(new Runnable() {
            @Override
            public void run() {
                final BaseAdapter adapter = new ListEntryAdapter(getActivity(), ChallengesRepository.getInstance().getCompletedChallenges());
                listView.setAdapter(adapter);
                ChallengesRepository.getInstance().setOnCompletedChallengesChanged(new Runnable() {
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
