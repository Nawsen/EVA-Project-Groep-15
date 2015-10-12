package hogent.group15.ui;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;

import hogent.group15.AsyncUtil;
import hogent.group15.Challenge;
import hogent.group15.Consumer;
import hogent.group15.Logging;
import hogent.group15.StringInterpolator;

/**
 * Created by Frederik on 10/11/2015.
 */
public class ChallengeView extends FrameLayout {

    private TextView title;
    private TextView description;
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

    private void initialize() {
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ChallengeDetailsActivity.class);
                intent.putExtra("challenge", currentChallenge);
                getContext().startActivity(intent);
            }
        });
        addView(inflate(getContext(), R.layout.view_challenge, null));
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        initComponents();
    }

    private void initComponents() {
        title = (TextView) findViewById(R.id.challenge_title);
        description = (TextView) findViewById(R.id.challenge_description);
        score = (TextView) findViewById(R.id.challenge_score);
        image = (ImageView) findViewById(R.id.challenge_image);
        scoreExpression = score.getText().toString();
    }

    public void updateContents(Challenge challenge) {
        if (challenge != null) {
            currentChallenge = challenge;
            title.setText(challenge.getTitle());
            description.setText(challenge.getShortDescription());
            score.setText(StringInterpolator.interpolate(scoreExpression, challenge.getScore()));
            AsyncUtil.getBitmapAsync(new AsyncUtil.BitmapParameter(challenge.getImageUri(), getResources()), new Consumer<Bitmap>() {
                @Override
                public void consume(Bitmap bitmap) {
                    image.setImageBitmap(bitmap);
                }
            });
        }
    }
}