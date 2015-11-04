package hogent.group15.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hogent.group15.domain.Backend;
import hogent.group15.domain.Challenge;
import hogent.group15.ui.util.ActionBarConfig;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class ChallengeDetailsActivity extends AppCompatActivity {

    @Bind(R.id.challenge_title)
    TextView title;

    @Bind(R.id.challenge_image)
    ImageView image;

    @Bind(R.id.challenge_long_description)
    TextView longDescription;

    private Challenge currentChallenge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge_details);

        ButterKnife.bind(this);
        updateContents((Challenge) getIntent().getSerializableExtra("challenge"));
        Backend.getBackend().getDetailedChallenge(currentChallenge.getId(), new Callback<Challenge>() {

            @Override
            public void success(Challenge data, Response response) {
                currentChallenge.setDetailedDescription(data.getDetailedDescription());
                longDescription.setText(currentChallenge.getDetailedDescription() == null ? "" : Html.fromHtml(currentChallenge.getDetailedDescription()));
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e("DESCRIPTION RETRIEVAL", "Couldn't retrieve description for challenge: " + currentChallenge.getTitle());
            }
        });
    }

    public void updateContents(Challenge challenge) {
        currentChallenge = challenge;
        title.setText(challenge.getTitle());
        longDescription.setText(challenge.getDetailedDescription() == null ? "" : Html.fromHtml(challenge.getDetailedDescription()));
        Backend.getBackend().loadImageInto(this,challenge.getHeaderImageUri().toString(), image);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return ActionBarConfig.onCreateOptionsMenu(menu, this);
    }

    @OnClick(R.id.challenge_accept)
    public void onAcceptChallenge(Button b) {
        Backend.getBackend().acceptChallenge(currentChallenge, new Callback<Challenge>() {

            @Override
            public void success(Challenge challenge, Response response) {
                ChallengeDetailsActivity.this.finish();
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e(Backend.TAG, "Accepting challenge failed: " + error.getResponse());
                ChallengeDetailsActivity.this.finish();
            }
        });
    }
}
