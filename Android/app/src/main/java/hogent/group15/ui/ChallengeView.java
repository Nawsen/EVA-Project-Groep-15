package hogent.group15.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import hogent.group15.AsyncUtil;
import hogent.group15.domain.Challenge;
import hogent.group15.Consumer;
import hogent.group15.StringInterpolator;

/**
 * Created by Frederik on 10/11/2015.
 */
public class ChallengeView extends LinearLayout {

    private View root;
    private TextView title;
    private TextView score;
    private ImageView image;
    private String scoreExpression;
    private Challenge currentChallenge;

    public ChallengeView(Context context) {
        super(context);
        initialize();
    }

    public ChallengeView(Context context, AttributeSet attrs) {
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
        initComponents();
    }

    private void initComponents() {
        title = (TextView) findViewById(R.id.challenge_title);
        score = (TextView) findViewById(R.id.challenge_score);
        image = (ImageView) findViewById(R.id.challenge_image);
        scoreExpression = score.getText().toString();
    }

    public void updateContents(Challenge challenge) {
        updateContents(challenge, null);
    }

    public void updateContents(Challenge challenge, final Runnable onComplete) {
        if (challenge != null) {
            currentChallenge = challenge;
            title.setText(challenge.getTitle());
            score.setText(StringInterpolator.interpolate(scoreExpression, challenge.getScore()));
            AsyncUtil.getBitmapAsync(new AsyncUtil.BitmapParameter(challenge.getHeaderImageUri(), getResources()), new Consumer<Bitmap>() {
                @Override
                public void consume(Bitmap bitmap) {
                    image.setImageBitmap(bitmap);
                    if (onComplete != null) {
                        onComplete.run();
                        ChallengeView.this.setAlpha(1f);
                    }
                }
            });
        }
    }
}
