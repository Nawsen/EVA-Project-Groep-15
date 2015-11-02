package hogent.group15.ui.fragments;


import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import hogent.group15.domain.AchievementsRepository;
import hogent.group15.ui.R;
import hogent.group15.ui.controls.ListEntry;
import hogent.group15.ui.controls.list.EmptyListEntry;
import hogent.group15.ui.util.ListEntryAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class AchievementsFragment extends Fragment {

    private ListView listView;

    public AchievementsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onStart() {
        super.onStart();
        listView = (ListView) this.getView().findViewById(R.id.achievementsListView);
        listView.setAdapter(new ListEntryAdapter(getActivity(), AchievementsRepository.getInstance().getAchievements()));
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
