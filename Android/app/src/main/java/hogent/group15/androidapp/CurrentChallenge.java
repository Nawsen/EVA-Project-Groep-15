package hogent.group15.androidapp;


import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.telecom.Call;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hogent.group15.AsyncUtil;
import hogent.group15.Consumer;
import hogent.group15.domain.Backend;
import hogent.group15.domain.Challenge;
import hogent.group15.ui.R;
import retrofit.Callback;
import retrofit.ResponseCallback;
import retrofit.RetrofitError;
import retrofit.client.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class CurrentChallenge extends FrameLayout {

    @Bind(R.id.challenge_image)
    ImageView image;

    @Bind(R.id.challenge_title)
    TextView title;

    @Bind(R.id.challenge_long_description)
    TextView description;

    private Challenge currentChallenge;

    public CurrentChallenge(Context context) {
        super(context);
        inflate(getContext(), R.layout.fragment_current_challenge, this);
        ButterKnife.bind(this);
    }

    public void updateContents(Challenge currentChallenge) {
        this.currentChallenge = currentChallenge;
        title.setText(currentChallenge.getTitle());
        description.setText(Html.fromHtml(currentChallenge.getDetailedDescription()));
        AsyncUtil.getBitmapAsync(new AsyncUtil.BitmapParameter(currentChallenge.getHeaderImageUri(), getResources()), new Consumer<Bitmap>() {
            @Override
            public void consume(Bitmap bitmap) {
                image.setImageBitmap(bitmap);
            }
        });
    }

    private Consumer<Challenge> onCompleteCallback;

    public void setOnCompleteChallenge(Consumer<Challenge> onCompleteChallenge) {
        onCompleteCallback = onCompleteChallenge;
    }

    @OnClick(R.id.challenge_accept)
    public void onCompleteChallenge(Button b) {
        Backend.getBackend().completeCurrentChallenge(new ResponseCallback() {
            @Override
            public void success(Response response) {
                if(onCompleteCallback != null) {
                    onCompleteCallback.consume(currentChallenge);
                }
            }

            @Override
            public void failure(RetrofitError error) {
            }
        });

        Toast.makeText(getContext(), getContext().getString(R.string.congratulations), Toast.LENGTH_SHORT);
    }

    public Challenge getCurrentChallenge() {
        return currentChallenge;
    }

    public void setCurrentChallenge(Challenge currentChallenge) {
        this.currentChallenge = currentChallenge;
    }
}
