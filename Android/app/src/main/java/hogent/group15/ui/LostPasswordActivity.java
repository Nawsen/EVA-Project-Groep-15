package hogent.group15.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LostPasswordActivity extends AppCompatActivity {

    @Bind(R.id.lost_password_email)
    public EditText email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lost_password);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.lost_password_button)
    public void onSendPasswordLink(View v) {
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
    }
}
