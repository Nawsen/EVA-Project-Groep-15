package hogent.group15;

import hogent.group15.Challenge.Difficulty;
import hogent.group15.User;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-10-17T14:00:07")
@StaticMetamodel(Challenge.class)
public class Challenge_ { 

    public static volatile SingularAttribute<Challenge, Difficulty> difficulty;
    public static volatile SingularAttribute<Challenge, String> imageUrl;
    public static volatile SingularAttribute<Challenge, String> description;
    public static volatile SingularAttribute<Challenge, Integer> id;
    public static volatile SingularAttribute<Challenge, String> title;
    public static volatile ListAttribute<Challenge, User> users;

}