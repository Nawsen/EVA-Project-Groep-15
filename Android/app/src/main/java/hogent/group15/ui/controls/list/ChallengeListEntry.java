package hogent.group15.ui.controls.list;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;
import hogent.group15.AsyncUtil;
import hogent.group15.domain.Backend;
import hogent.group15.domain.Challenge;
import hogent.group15.Consumer;
import hogent.group15.StringInterpolator;
import hogent.group15.ui.ChallengeDetailsActivity;
import hogent.group15.ui.R;

/**
 * Created by Frederik on 10/11/2015.
 */
public class ChallengeListEntry extends FrameLayout {

    private View root;

    @Bind(R.id.challenge_title)
    public TextView title;

    @Bind(R.id.challenge_score)
    public TextView score;

    @Bind(R.id.challenge_image)
    public ImageView image;

    private String scoreExpression;
    private Challenge currentChallenge;

    public ChallengeListEntry(Context context) {
        super(context);
        initialize();
    }

    public ChallengeListEntry(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    public Challenge getChallenge() {
        return currentChallenge;
    }

    private void initialize() {
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ChallengeDetailsActivity.class);
                intent.putExtra("challenge", currentChallenge);
                getContext().startActivity(intent);
            }
        });

        root = inflate(getContext(), R.layout.view_challenge, this);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }

    private void initComponents() {
        ButterKnife.bind(this);
        scoreExpression = score.getText().toString();
    }

    private boolean initializedComponents = false;

    public void updateContents(Challenge challenge) {
        updateContents(challenge, null);
    }

    public void updateContents(Challenge challenge, final Runnable onComplete) {
        if (!initializedComponents) {
            initComponents();
            initializedComponents = true;
        }

        if (challenge != null) {
            currentChallenge = challenge;
            title.setText(challenge.getTitle());
            score.setText(StringInterpolator.interpolate(scoreExpression, challenge.getDifficulty()));
            Backend.getBackend().loadImageInto(this.getContext(), challenge.getHeaderImageUri().toString(), image);
            ChallengeListEntry.this.setAlpha(1f);
            if (onComplete != null)
                onComplete.run();
        }
    }
}
