package hogent.group15.ui.pages;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import hogent.group15.ui.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class OngoingChallengesFragment extends Fragment {


    public OngoingChallengesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ongoing_challenges, container, false);
    }


}
