package hogent.group15.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;
import hogent.group15.Consumer;
import hogent.group15.StringInterpolator;
import hogent.group15.domain.Backend;
import hogent.group15.domain.Sex;
import hogent.group15.domain.VegetarianGrade;
import hogent.group15.ui.util.ActionBarConfig;

public class RegisterActivity extends AppCompatActivity {

    @Bind(R.id.register_firstName)
    public TextView firstName;

    @Bind(R.id.register_lastName)
    public TextView lastName;

    @Bind(R.id.register_email)
    public TextView email;

    @Bind(R.id.register_sex)
    public Spinner sex;

    @Bind(R.id.register_password)
    public TextView password;

    @Bind(R.id.register_grade)
    public Spinner grade;

    private Map<String, Sex> sexMap = new HashMap<>();
    private Map<String, VegetarianGrade> vegetarianGradeMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Spinner sex = (Spinner) findViewById(R.id.register_sex);
        Spinner grade = (Spinner) findViewById(R.id.register_grade);

        for (Sex s : Sex.values()) {
            sexMap.put(getString(s.getAndroidId()), s);
        }

        for (VegetarianGrade g : VegetarianGrade.values()) {
            vegetarianGradeMap.put(getString(g.getAndroidId()), g);
        }

        ArrayAdapter<String> sexAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, new ArrayList<>(sexMap.keySet()));
        sexAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        sex.setAdapter(sexAdapter);

        ArrayAdapter<String> gradeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, new ArrayList<>(vegetarianGradeMap.keySet()));
        gradeAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        grade.setAdapter(gradeAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return ActionBarConfig.onCreateOptionsMenu(menu, this);
    }

    @OnClick(R.id.register_submit)
    public void onRegister(Button registerButton) {
        Toast.makeText(this, "Clicked", Toast.LENGTH_SHORT);
        Backend.getBackend().registerUser(firstName.getText(), lastName.getText(), email.getText(), sexMap.get(sex.getSelectedItem()), password.getText(),vegetarianGradeMap.get(grade.getSelectedItem()), new Consumer<String>() {

            @Override
            public void consume(String s) {
                Log.i("REGISTRATION", "Result: " + s);
            }
        });
    }
}
