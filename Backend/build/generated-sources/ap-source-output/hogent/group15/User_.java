package hogent.group15;

import hogent.group15.Address;
import hogent.group15.Challenge;
import hogent.group15.User.Gender;
import hogent.group15.User.VegetarianGrade;
import java.time.LocalDate;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-10-17T14:00:07")
@StaticMetamodel(User.class)
public class User_ { 

    public static volatile SingularAttribute<User, String> lastName;
    public static volatile SingularAttribute<User, Address> address;
    public static volatile SingularAttribute<User, byte[]> salt;
    public static volatile SingularAttribute<User, Gender> gender;
    public static volatile SingularAttribute<User, Integer> facebookId;
    public static volatile SingularAttribute<User, LocalDate> birthDate;
    public static volatile SingularAttribute<User, String> firstName;
    public static volatile SingularAttribute<User, Challenge> currentChallenge;
    public static volatile SingularAttribute<User, String> password;
    public static volatile SingularAttribute<User, VegetarianGrade> grade;
    public static volatile ListAttribute<User, Challenge> completedChallenges;
    public static volatile SingularAttribute<User, String> imageUrl;
    public static volatile SingularAttribute<User, String> email;

}