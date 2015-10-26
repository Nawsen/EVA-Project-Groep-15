package hogent.group15.ui;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import hogent.group15.AsyncUtil;
import hogent.group15.Challenge;
import hogent.group15.Consumer;
import hogent.group15.ui.util.ActionBarConfig;

public class ChallengeDetailsActivity extends AppCompatActivity {

    private TextView title;
    private ImageView image;
    private TextView longDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge_details);

        title = (TextView) findViewById(R.id.challenge_title);
        image = (ImageView) findViewById(R.id.challenge_image);
        longDescription = (TextView) findViewById(R.id.challenge_long_description);


        updateContents((Challenge) getIntent().getSerializableExtra("challenge"));
    }

    public void updateContents(Challenge challenge) {
        title.setText(challenge.getTitle());
        longDescription.setText(challenge.getDetailedDescription());
        AsyncUtil.getBitmapAsync(new AsyncUtil.BitmapParameter(challenge.getHeaderImageUri(), getResources()), new Consumer<Bitmap>() {
            @Override
            public void consume(Bitmap bitmap) {
                image.setImageBitmap(bitmap);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return ActionBarConfig.onCreateOptionsMenu(menu, this);
    }

    public void onAcceptChallenge(View v) {

    }
}
