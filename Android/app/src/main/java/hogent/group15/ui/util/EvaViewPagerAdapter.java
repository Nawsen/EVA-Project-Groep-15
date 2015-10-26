package hogent.group15.ui.util;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import hogent.group15.ui.R;
import hogent.group15.ui.fragments.AchievementsFragment;
import hogent.group15.ui.fragments.CompletedChallengesFragment;
import hogent.group15.ui.fragments.OngoingChallengesFragment;

/**
 * Created by Brent on 10/24/2015.
 */
public class EvaViewPagerAdapter extends FragmentPagerAdapter {

    private Context context;

    public EvaViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public EvaViewPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch(position) {
            case 0:
                return new OngoingChallengesFragment();
            case 1:
                return new CompletedChallengesFragment();
            case 2:
                return new AchievementsFragment();
            default:
                return new CompletedChallengesFragment();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return context.getString(R.string.ongoing_challenges);
            case 1:
                return context.getString(R.string.completed_challenges);
            case 2:
                return context.getString(R.string.achievements);
            default:
                return "UNKNOWN";
        }
    }
}
