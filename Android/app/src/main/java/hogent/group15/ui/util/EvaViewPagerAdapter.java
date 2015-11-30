package hogent.group15.ui.util;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import hogent.group15.androidapp.CurrentChallenge;
import hogent.group15.domain.Backend;
import hogent.group15.domain.Domain;
import hogent.group15.ui.R;
import hogent.group15.ui.fragments.AchievementsFragment;
import hogent.group15.ui.fragments.CompletedChallengesFragment;
import hogent.group15.ui.fragments.NextChallenge;
import hogent.group15.ui.fragments.OngoingChallengeFragment;

/**
 * Created by Brent on 10/24/2015.
 */
public class EvaViewPagerAdapter extends FragmentPagerAdapter {

    private Context context;
    private CompletedChallengesFragment completedChallengesFragment = new CompletedChallengesFragment();
    private AchievementsFragment achievementsFragment = new AchievementsFragment();
    private OngoingChallengeFragment ongoingChallengesFragment = new OngoingChallengeFragment();

    public EvaViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public EvaViewPagerAdapter(FragmentManager fm, Context context) {
        this(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                Domain.activePage = Domain.ActivePage.ONGOING_CHALLENGES;
                Domain.activeFragment = ongoingChallengesFragment;
                return ongoingChallengesFragment;
            case 1:
                Domain.activePage = Domain.ActivePage.COMPLETED_CHALLENGES;
                Domain.activeFragment = completedChallengesFragment;
                return completedChallengesFragment;
            case 2:
                Domain.activePage = Domain.ActivePage.ACHIEVEMENTS;
                Domain.activeFragment = achievementsFragment;
                return achievementsFragment;
            default:
                return null;
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

    public NextChallenge getNextChallengeFragment() {
        return ongoingChallengesFragment.getNextChallenge();
    }

    public CompletedChallengesFragment getCompletedChallengesFragment() {
        return completedChallengesFragment;
    }

    public AchievementsFragment getAchievementsFragment() {
        return achievementsFragment;
    }

    public void setNextChallengeFragment(NextChallenge nextChallengeFragment) {
        ongoingChallengesFragment.setNextChallenge(nextChallengeFragment);
    }

    public void setCompletedChallengesFragment(CompletedChallengesFragment completedChallengesFragment) {
        this.completedChallengesFragment = completedChallengesFragment;
    }

    public void setAchievementsFragment(AchievementsFragment achievementsFragment) {
        this.achievementsFragment = achievementsFragment;
    }

    public CurrentChallenge getCurrentChallengeFragment() {
        return ongoingChallengesFragment.getCurrentChallenge();
    }

    public void setCurrentChallengeFragment(CurrentChallenge currentChallengeFragment) {
        ongoingChallengesFragment.setCurrentChallenge(currentChallengeFragment);
    }
}
