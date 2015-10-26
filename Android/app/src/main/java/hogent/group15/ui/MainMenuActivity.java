package hogent.group15.ui;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import hogent.group15.ui.util.EvaViewPagerAdapter;

public class MainMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        ViewPager pager = (ViewPager) findViewById(R.id.progressViewpager);
        EvaViewPagerAdapter evaViewPagerAdapter = new EvaViewPagerAdapter(getSupportFragmentManager(), this);
        pager.setAdapter(evaViewPagerAdapter);
    }
}
