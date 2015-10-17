package hogent.group15;

import hogent.group15.configuration.KnownVegetarianGrade;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 *
 * @author Frederik
 */
@Entity
@Table(name = "TBL_USER")
public class User implements Serializable {
    
    public enum Gender {
	MALE, FEMALE
    }
    
    public enum Role {
	USER, ADMIN, ROOT
    }
    
    public enum VegetarianGrade {
	
	VEGAN, LACTO_VEGETARIAN, OVO_VEGETARIAN, LACTO_OVO_VEGETARIAN,
	POLLOTARIAN, PESCATARIAN, FLEXITARIAN, UNKOWN
    }
    
    @Id
    @Pattern(regexp = "^([A-Z|a-z|0-9](\\.|_){0,1})+[A-Z|a-z|0-9]\\@([A-Z|a-z|0-9])+((\\.){0,1}[A-Z|a-z|0-9]){2}\\.[a-z]{2,3}$", message = "email")
    private String email;
    
    private int facebookId;
    
    @NotNull(message = "firstName")
    @Size(min = 1, message = "firstName")
    private String firstName;
    
    @NotNull(message = "lastName")
    @Size(min = 1, message = "lastName")
    private String lastName;
    
    @NotNull(message = "password")
    @Size(min = 7, message = "password")
    private String password;
    
    @Enumerated(EnumType.ORDINAL)
    @NotNull(message = "gender")
    private Gender gender;
    
    @Embedded
    private Address address;
    
    @Enumerated(EnumType.ORDINAL)
    @KnownVegetarianGrade
    private VegetarianGrade grade;
    
    @ManyToOne(fetch = FetchType.EAGER)
    private Challenge currentChallenge;
    
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "users")
    private List<Challenge> completedChallenges;
    
    @Lob
    private byte[] salt = new byte[32];
    
    @Pattern(regexp = "[(http(s)?):\\/\\/(www\\.)?a-zA-Z0-9@:%._\\+~#=]{2,256}\\.[a-z]{2,6}\\b([-a-zA-Z0-9@:%_\\+.~#?&//=]*)", message = "imageUrl")
    private String imageUrl;
    
    @NotNull(message = "birthdate")
    private LocalDate birthDate;

    public User() {
    }

    public User(String firstName, String lastName, String email, String password, Gender gender, Address address, VegetarianGrade grade, LocalDate birthDate) {
	this.firstName = firstName;
	this.lastName = lastName;
	this.email = email;
	this.password = password;
	this.gender = gender;
	this.address = address;
	this.grade = grade;
	this.birthDate = birthDate;
    }
    
    public int getFacebookId() {
	return facebookId;
    }

    public void setFacebookId(int facebookId) {
	this.facebookId = facebookId;
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

    public String getPassword() {
	return password;
    }

    public void setPassword(String password) {
	this.password = password;
    }

    public Gender getGender() {
	return gender;
    }

    public void setGender(Gender gender) {
	this.gender = gender;
    }

    public Address getAddress() {
	return address;
    }

    public void setAddress(Address address) {
	this.address = address;
    }

    public VegetarianGrade getGrade() {
	return grade;
    }

    public void setGrade(VegetarianGrade grade) {
	this.grade = grade;
    }

    public Challenge getCurrentChallenge() {
	return currentChallenge;
    }

    public void setCurrentChallenge(Challenge currentChallenge) {
	this.currentChallenge = currentChallenge;
    }

    public List<Challenge> getCompletedChallenges() {
	return completedChallenges;
    }

    public void setCompletedChallenges(List<Challenge> completedChallenges) {
	this.completedChallenges = completedChallenges;
    }

    public byte[] getSalt() {
	return salt;
    }

    public void setSalt(byte[] salt) {
	this.salt = salt;
    }

    public String getImageUrl() {
	return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
	this.imageUrl = imageUrl;
    }

    public LocalDate getBirthDate() {
	return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
	this.birthDate = birthDate;
    }

    public String getEmail() {
	return email;
    }

    public void setEmail(String email) {
	this.email = email;
    }

    @Override
    public int hashCode() {
	int hash = 3;
	hash = 29 * hash + Objects.hashCode(this.email);
	hash = 29 * hash + this.facebookId;
	hash = 29 * hash + Objects.hashCode(this.firstName);
	hash = 29 * hash + Objects.hashCode(this.lastName);
	return hash;
    }

    @Override
    public boolean equals(Object obj) {
	if (obj == null) {
	    return false;
	}
	if (getClass() != obj.getClass()) {
	    return false;
	}
	final User other = (User) obj;
	if (!Objects.equals(this.email, other.email)) {
	    return false;
	}
	return true;
    }
}
