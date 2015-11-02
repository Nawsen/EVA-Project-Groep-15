package hogent.group15.ui;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;

import hogent.group15.ui.util.ActionBarConfig;
import hogent.group15.ui.util.EvaViewPagerAdapter;

public class MainMenuActivity extends AppCompatActivity {

    public static Context appContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        appContext = this.getApplicationContext();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        ViewPager pager = (ViewPager) findViewById(R.id.progressViewpager);
        EvaViewPagerAdapter evaViewPagerAdapter = new EvaViewPagerAdapter(getSupportFragmentManager(), this);
        pager.setAdapter(evaViewPagerAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return ActionBarConfig.onCreateOptionsMenu(menu, this);
    }
}
