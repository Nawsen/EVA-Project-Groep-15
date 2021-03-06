package hogent.group15;

import hogent.group15.configuration.KnownVegetarianGrade;
import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
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

	USER, ADMIN
    }

    public enum VegetarianGrade {

	OMNIVORE, PESCETARIAR, PARTTIME_VEGETARIAN, VEGETARIAN, VEGAN, UNKNOWN
    }
    
    @Transient
    private static final Random RANDOM = new SecureRandom();

    @Id
    @Pattern(regexp = "^([A-Z|a-z|0-9](\\.|_){0,1})+[A-Z|a-z|0-9]\\@([A-Z|a-z|0-9])+((\\.){0,1}[A-Z|a-z|0-9]){2}\\.[a-z]{2,3}$", message = "email")
    private String email;

    private long facebookId;

    @Transient
    private transient String accessToken;
    
    @NotNull(message = "firstName")
    @Size(min = 1, message = "firstName")
    private String firstName;

    @NotNull(message = "lastName")
    @Size(min = 1, message = "lastName")
    private String lastName;

    @Transient
    @NotNull(message = "password")
    private String password;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    private List<Achievement> achievements;

    public byte[] getEncPassword() {
	return encPassword;
    }

    public void setEncPassword(byte[] encPassword) {
	this.encPassword = encPassword;
    }

    public List<Achievement> getAchievements() {
	return achievements;
    }

    //encrypted password only for local usage!
    private byte[] encPassword;

    @Enumerated(EnumType.ORDINAL)
    @NotNull(message = "gender")
    private Gender gender;

    @Embedded
    private Address address;

    @Enumerated(EnumType.ORDINAL)
    @KnownVegetarianGrade
    private VegetarianGrade grade;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Challenge currentChallenge;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<CompletedChallenge> completedChallenges;

    @Lob
    private byte[] salt = new byte[32];

    private String imageUrl;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    private List<DailyChallenges> dailyChallenges;

    @Enumerated(EnumType.ORDINAL)
    private Role role;

    public User() {
    }

    public User(String firstName, String lastName, String email, String password, Gender gender, Address address, VegetarianGrade grade) {
	this.firstName = firstName;
	this.lastName = lastName;
	this.email = email;
	this.gender = gender;
	this.address = address;
	this.grade = grade;
	this.password = password;
	//set good hashed & salted password
	this.salt = generateSalt();
	this.encPassword = hash(password.toCharArray(), this.salt);
    }

    public Role getRole() {
	return role;
    }

    public void setRole(Role role) {
	this.role = role;
    }

    public long getFacebookId() {
	return facebookId;
    }

    public void setFacebookId(long facebookId) {
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
	byte[] salt = generateSalt();
	setSalt(salt);
	this.encPassword = hash(password.toCharArray(), salt);
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

    public List<CompletedChallenge> getCompletedChallenges() {
	return completedChallenges;
    }

    public void setCompletedChallenges(List<CompletedChallenge> completedChallenges) {
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

    public String getEmail() {
	return email;
    }

    public void setEmail(String email) {
	this.email = email;
    }

    public List<DailyChallenges> getDailyChallenges() {
	return dailyChallenges;
    }

    public void setDailyChallenges(List<DailyChallenges> dailyChallenges) {
	this.dailyChallenges = dailyChallenges;
    }

    public Optional<DailyChallenges> getDailyChallengesForToday() {
	return getDailyChallengesForDay(Calendar.getInstance());
    }

    public Optional<DailyChallenges> getDailyChallengesForDay(Calendar date) {
	for (DailyChallenges dc : getDailyChallenges()) {
	    Calendar daily = Calendar.getInstance();
	    daily.setTime(dc.getDate());
	    if (daily.get(Calendar.YEAR) == date.get(Calendar.YEAR) && daily.get(Calendar.MONTH) == date.get(Calendar.MONTH)
		    && daily.get(Calendar.DAY_OF_MONTH) == date.get(Calendar.DAY_OF_MONTH)) {
		return Optional.of(dc);
	    }
	};
	
	return Optional.empty();
    }

    public static byte[] generateSalt() {
	byte[] salt = new byte[16];
	RANDOM.nextBytes(salt);
	return salt;
    }

    public static byte[] hash(char[] password, byte[] salt) {
	PBEKeySpec spec = new PBEKeySpec(password, salt, 10000, 256);
	Arrays.fill(password, Character.MIN_VALUE);
	
	try {
	    SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
	    return skf.generateSecret(spec).getEncoded();
	} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
	    throw new AssertionError("Error while hashing a password: " + e.getMessage(), e);
	} finally {
	    spec.clearPassword();
	}
    }

    public static boolean isExpectedPassword(char[] password, byte[] salt, byte[] expectedHash) {
	byte[] pwdHash = hash(password, salt);
	if (pwdHash.length != expectedHash.length) {
	    return false;
	}
	for (int i = 0; i < pwdHash.length; i++) {
	    if (pwdHash[i] != expectedHash[i]) {
		return false;
	    }
	}
	return true;
    }

    @Override
    public int hashCode() {
	int hash = 3;
	hash = 29 * hash + Objects.hashCode(this.email);
	hash = 29 * hash + (int) (this.facebookId % Math.round(Math.pow(2, 32))) - 1;
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

    public String getAccessToken() {
	return accessToken;
    }

    public void setAccessToken(String accessToken) {
	this.accessToken = accessToken;
    }

}
