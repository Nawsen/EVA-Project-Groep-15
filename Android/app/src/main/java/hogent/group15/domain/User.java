package hogent.group15.domain;

/**
 * Created by Frederik on 11/2/2015.
 */
public class User {

    String email;
    String password;

    String firstName;
    String lastName;
    Gender gender;
    VegetarianGrade grade;
    String facebookId;
    String accessToken;


    public User() {
    }

    public User(String facebookId, String accessToken, boolean facebook) {
        this.facebookId = facebookId;
        this.accessToken = accessToken;
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public User(String email, String password, String firstName, String lastName, Gender gender, VegetarianGrade grade) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.grade = grade;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public VegetarianGrade getGrade() {
        return grade;
    }

    public void setGrade(VegetarianGrade grade) {
        this.grade = grade;
    }
}
