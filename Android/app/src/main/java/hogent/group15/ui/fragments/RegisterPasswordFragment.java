package hogent.group15.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import hogent.group15.Validator;
import hogent.group15.ui.R;


public class RegisterPasswordFragment extends Fragment implements Validator {

    @Bind(R.id.register_password)
    protected TextView password;

    @Bind(R.id.register_password_repeat)
    protected TextView passwordRepeat;

    public RegisterPasswordFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_register_password, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ButterKnife.bind(this, getView());
    }

    public String getPassword() {
        if (!validate()) {
            throw new IllegalStateException("Invalid passwords");
        } else {
            return password.getText().toString();
        }
    }

    @Override
    public boolean validate() {
        if (password.getText().toString().length() < 7) {
            password.setError(getString(R.string.validation_password_size));
            return false;
        } else if (!password.getText().toString().equals(passwordRepeat.getText().toString())) {
            passwordRepeat.setError(getString(R.string.validation_password_repeat));
            return false;
        } else {
            return true;
        }
    }
}
