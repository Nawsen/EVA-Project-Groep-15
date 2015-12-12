package hogent.group15.ui;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.share.model.ShareLinkContent;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hogent.group15.domain.FBShare;
import hogent.group15.service.Backend;
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

    @Bind(R.id.challenge_accept)
    Button acceptButton;

    private Challenge currentChallenge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge_details);

        ButterKnife.bind(this);
        updateContents((Challenge) getIntent().getSerializableExtra("challenge"));
        final boolean shouldShowButton = getIntent().hasExtra("showAcceptButton") && getIntent().getBooleanExtra("showAcceptButton", true);
        acceptButton.setVisibility(shouldShowButton ? View.VISIBLE : View.GONE);
        if (shouldShowButton) {
            FBShare.with(this).setShareAction(new ShareLinkContent.Builder()
                    .setContentTitle("I'm about to start " + currentChallenge.getTitle())
                    .setContentDescription(currentChallenge.getDetailedDescription().isEmpty() ? "No description" : currentChallenge.getDetailedDescription())
                    .setImageUrl(Uri.parse("http://www.evavzw.be/sites/all/themes/wieni-subtheme/apple-touch-icon-152x152.png"))
                    .setContentUrl(Uri.parse("http://evavzw.be"))
                    .build());
        } else {
            FBShare.with(this).setShareAction(new ShareLinkContent.Builder()
                    .setContentTitle("I have completed " + currentChallenge.getTitle())
                    .setContentDescription(currentChallenge.getDetailedDescription().isEmpty() ? "No description" : currentChallenge.getDetailedDescription())
                    .setImageUrl(Uri.parse("http://www.evavzw.be/sites/all/themes/wieni-subtheme/apple-touch-icon-152x152.png"))
                    .setContentUrl(Uri.parse("http://evavzw.be"))
                    .build());
        }
        Backend.getBackend(this).getDetailedChallenge(currentChallenge.getId(), new Callback<Challenge>() {

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
        Backend.getBackend(this).loadImageInto(challenge.getHeaderImageUri().toString(), image);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return ActionBarConfig.getInstance(this).onCreateOptionsMenu(menu, this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return ActionBarConfig.getInstance(this).onOptionsItemSelected(item);
    }

    @OnClick(R.id.challenge_accept)
    public void onAcceptChallenge(Button b) {
        Backend.getBackend(this).acceptChallenge(currentChallenge, new Callback<Challenge>() {

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
