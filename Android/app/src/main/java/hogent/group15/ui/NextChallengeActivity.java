package hogent.group15.ui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;

import java.net.URI;

import hogent.group15.Challenge;

public class NextChallengeActivity extends AppCompatActivity {

    private ChallengeView firstChallenge;
    private ChallengeView secondChallenge;
    private ChallengeView thirdChallenge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge_next);
        firstChallenge = (ChallengeView) findViewById(R.id.first_challenge);
        secondChallenge = (ChallengeView) findViewById(R.id.second_challenge);
        thirdChallenge = (ChallengeView) findViewById(R.id.third_challenge);
        addTestData();
    }

    private void addTestData() {
        URI uri = URI.create("http://www.evavzw.be/sites/default/files/styles/recipe_thumbnail/public/recipe/teaser/congiglie.jpg?itok=UyECvLv_");
        firstChallenge.updateContents(new Challenge(uri, "First challenge", "A description of the first challenge", "The detailed description of the first challenge", 10));
        secondChallenge.updateContents(new Challenge(uri, "Second challenge", "A description of the second challenge", "The detailed description of the second challenge", 12));
        thirdChallenge.updateContents(new Challenge(uri, "Third challenge", "A description of the third challenge", "The detailed description of the third challenge", 15));
    }
}
