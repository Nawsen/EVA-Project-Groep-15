package hogent.group15.ui.controls.list;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;

import butterknife.Bind;
import butterknife.ButterKnife;
import hogent.group15.service.Backend;
import hogent.group15.domain.Challenge;
import hogent.group15.ui.StringInterpolator;
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
    private boolean showAcceptChallenge = true;

    public ChallengeListEntry(Context context) {
        super(context);
        initialize();
    }

    public ChallengeListEntry(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    private void initialize() {
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ChallengeDetailsActivity.class);
                intent.putExtra("challenge", currentChallenge);
                intent.putExtra("showAcceptButton", showAcceptChallenge);
                getContext().startActivity(intent);
            }
        });

        root = inflate(getContext(), R.layout.view_challenge, this);
        ChallengeListEntry.this.setAlpha(0f);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        Resources r = getContext().getResources();
        int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, r.getDisplayMetrics());
        params.setMargins(px, px, px, px);
        setLayoutParams(params);
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
            score.setText(StringInterpolator.interpolate(scoreExpression, getContext().getString(Challenge.Difficulty.getResourceIdFor(challenge.getDifficulty()))));
            Backend.getBackend(getContext()).loadImageInto(challenge.getHeaderImageUri(), new Callback() {
                @Override
                public void onSuccess() {
                    ChallengeListEntry.this.setAlpha(1f);
                }

                @Override
                public void onError() {
                    ChallengeListEntry.this.setAlpha(1f);
                    image.setVisibility(GONE);
                }
            }, image);

            if (onComplete != null)
                onComplete.run();
        }
    }

    public void setShowAcceptChallengeButton(boolean shouldShow) {
        showAcceptChallenge = shouldShow;
    }
}
