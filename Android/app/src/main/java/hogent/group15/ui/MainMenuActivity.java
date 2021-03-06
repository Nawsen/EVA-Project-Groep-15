package hogent.group15.ui;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.List;

import hogent.group15.ui.fragments.AchievementsFragment;
import hogent.group15.ui.fragments.CompletedChallengesFragment;
import hogent.group15.ui.util.ActionBarConfig;
import hogent.group15.ui.util.EvaViewPagerAdapter;

public class MainMenuActivity extends AppCompatActivity {

    public static Context appContext;
    private EvaViewPagerAdapter evaViewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        appContext = this.getApplicationContext();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        ViewPager pager = (ViewPager) findViewById(R.id.progressViewpager);
        evaViewPagerAdapter = new EvaViewPagerAdapter(getSupportFragmentManager(), this);

        if (savedInstanceState != null) {
            AchievementsFragment achievementsFragment = (AchievementsFragment) getSupportFragmentManager().getFragment(savedInstanceState, "achievements");
            if (achievementsFragment != null) {
                evaViewPagerAdapter.setAchievementsFragment(achievementsFragment);
            }

            CompletedChallengesFragment completedChallengesFragment = (CompletedChallengesFragment) getSupportFragmentManager().getFragment(savedInstanceState, "completedChallenges");
            if (completedChallengesFragment != null) {
                evaViewPagerAdapter.setCompletedChallengesFragment(completedChallengesFragment);
            }

            evaViewPagerAdapter.notifyDataSetChanged();
        }

        pager.setAdapter(evaViewPagerAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return ActionBarConfig.getInstance(this).onCreateOptionsMenu(menu, this, R.id.item_share);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return ActionBarConfig.getInstance(this).onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        List<Fragment> currentFragments = getSupportFragmentManager().getFragments();
        if (evaViewPagerAdapter.getAchievementsFragment().isAdded()) {
            getSupportFragmentManager().putFragment(outState, "achievements", evaViewPagerAdapter.getAchievementsFragment());
        }

        if (evaViewPagerAdapter.getCompletedChallengesFragment().isAdded()) {
            getSupportFragmentManager().putFragment(outState, "nextChallenges", evaViewPagerAdapter.getCompletedChallengesFragment());
        }
    }
}
