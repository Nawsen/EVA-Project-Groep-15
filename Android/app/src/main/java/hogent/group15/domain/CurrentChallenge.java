package hogent.group15.domain;


import android.content.Context;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hogent.group15.Consumer;
import hogent.group15.data.ChallengesRepository;
import hogent.group15.service.Backend;
import hogent.group15.ui.R;
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
        Backend.getBackend().loadImageInto(this.getContext(), currentChallenge.getHeaderImageUri().toString(), image);
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
                currentChallenge.setShowAcceptChallengeButton(false);
                ChallengesRepository.getInstance().addCompletedChallenge(currentChallenge);
                if(onCompleteCallback != null) {
                    onCompleteCallback.consume(currentChallenge);
                }
            }

            @Override
            public void failure(RetrofitError error) {
            }
        });

        Toast.makeText(getContext(), getContext().getString(R.string.congratulations), Toast.LENGTH_SHORT).show();
    }

    public Challenge getCurrentChallenge() {
        return currentChallenge;
    }

    public void setCurrentChallenge(Challenge currentChallenge) {
        this.currentChallenge = currentChallenge;
    }
}
