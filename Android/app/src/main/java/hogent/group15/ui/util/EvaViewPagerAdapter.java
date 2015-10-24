package hogent.group15.ui.util;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import hogent.group15.ui.pages.AchievementsFragment;
import hogent.group15.ui.pages.CompletedChallengesFragment;

/**
 * Created by Brent on 10/24/2015.
 */
public class EvaViewPagerAdapter extends FragmentPagerAdapter {

    public EvaViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public EvaViewPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch(position) {
            case 0:
                return new CompletedChallengesFragment();
            case 1:
                return new AchievementsFragment();
            default:
                return new CompletedChallengesFragment();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Completed challenges";
            case 1:
                return "Your achievements";
            default:
                return "UNKNOWN";
        }
    }
}
