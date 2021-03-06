package hogent.group15.ui.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import hogent.group15.Validator;
import hogent.group15.domain.Gender;
import hogent.group15.domain.VegetarianGrade;
import hogent.group15.service.LoginResponse;
import hogent.group15.ui.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterMainFragment extends Fragment implements Validator {

    @Bind(R.id.register_firstName)
    public TextView firstName;

    @Bind(R.id.register_lastName)
    public TextView lastName;

    @Bind(R.id.register_email)
    public TextView email;

    @Bind(R.id.register_sex)
    public Spinner sex;

    @Bind(R.id.register_grade)
    public Spinner grade;

    private Map<String, Gender> sexMap = new HashMap<>();
    private Map<String, VegetarianGrade> vegetarianGradeMap = new HashMap<>();

    public RegisterMainFragment() {
    }

    public Gender getSelectedSex() {
        return sexMap.get(sex.getSelectedItem());
    }

    public VegetarianGrade getSelectedGrade() {
        return vegetarianGradeMap.get(grade.getSelectedItem());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_register_main, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Spinner sex = (Spinner) getView().findViewById(R.id.register_sex);
        Spinner grade = (Spinner) getView().findViewById(R.id.register_grade);

        for (Gender s : Gender.values()) {
            sexMap.put(getString(s.getAndroidId()), s);
        }

        for (VegetarianGrade g : VegetarianGrade.values()) {
            vegetarianGradeMap.put(getString(g.getAndroidId()), g);
        }

        ArrayAdapter<String> sexAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, new ArrayList<>(sexMap.keySet()));
        sexAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        sex.setAdapter(sexAdapter);

        ArrayAdapter<String> gradeAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, new ArrayList<>(vegetarianGradeMap.keySet()));
        gradeAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        grade.setAdapter(gradeAdapter);
        ButterKnife.bind(this, getView());
        if (getArguments() != null) {
            LoginResponse lr = (LoginResponse) getArguments().getSerializable("facebookData");
            if (lr.getType() == LoginResponse.LoginResponseType.FACEBOOK_REGISTER) {
                firstName.setText(lr.getFirstName());
                lastName.setText(lr.getLastName());
                email.setText(lr.getEmail());
                sex.setSelection(lr.getGender() == 0 ? 1 : 0);
            }
        }
    }

    @Override
    public boolean validate() {
        boolean valid = true;
        if (firstName.getText().toString().trim().isEmpty()) {
            firstName.setError(getString(R.string.validation_submit_name));
            valid = false;
        }
        if (lastName.getText().toString().trim().isEmpty()) {
            lastName.setError(getString(R.string.validation_submit_name));
            valid = false;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email.getText()).matches()) {
            email.setError(getString(R.string.validation_submit_email));
            valid = false;
        }

        return valid;
    }
}
