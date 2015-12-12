package hogent.group15.ui.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import hogent.group15.data.AchievementsRepository;
import hogent.group15.domain.Achievement;
import hogent.group15.ui.R;
import hogent.group15.ui.util.ListEntryAdapter;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

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
    }

    @Override
    public void onResume() {
        super.onResume();
        AchievementsRepository.getInstance(getContext()).refreshAchievements(new Callback<List<Achievement>>() {
            @Override
            public void success(List<Achievement> achievements, Response response) {
                listView.setAdapter(new ListEntryAdapter(getActivity(), AchievementsRepository.getInstance(getContext()).getAchievements()));
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
