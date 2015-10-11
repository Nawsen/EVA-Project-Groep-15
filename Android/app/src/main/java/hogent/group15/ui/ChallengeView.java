package hogent.group15.ui;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;

import hogent.group15.Challenge;
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

    public ChallengeView(Context context) {
        super(context);
        initialize();
    }

    public ChallengeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    private void initialize() {
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

    private AsyncTask<BitmapParameter, Void, Bitmap> getBitmap = new AsyncTask<BitmapParameter, Void, Bitmap>() {
        @Override
        protected Bitmap doInBackground(BitmapParameter... params) {
            try {
                return BitmapFactory.decodeStream(params[0].getUri().toURL().openStream());
            } catch (IOException e) {
                Log.e(Logging.ERROR.getTag(), "Couldn't read image from: " + params[0].getUri());
                return BitmapFactory.decodeResource(params[0].getResources(), R.drawable.question_mark);
            }
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            image.setImageBitmap(bitmap);
        }
    };

    public void updateContents(Challenge challenge) {
        title.setText(challenge.getTitle());
        description.setText(challenge.getShortDescription());
        score.setText(StringInterpolator.interpolate(scoreExpression, challenge.getScore()));
        getBitmap.execute(new BitmapParameter(challenge.getImageUri(), getResources()));
    }

    private static class BitmapParameter {

        private final URI uri;
        private final Resources resources;

        public BitmapParameter(URI uri, Resources resources) {
            this.uri = uri;
            this.resources = resources;
        }

        public URI getUri() {
            return uri;
        }

        public Resources getResources() {
            return resources;
        }
    }
}
