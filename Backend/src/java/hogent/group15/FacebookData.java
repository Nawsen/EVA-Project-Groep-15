package hogent.group15;

import java.util.Map;

public class FacebookData {

    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private int gender;
    private String pictureUrl;
    private Map<String, Boolean> permissions;

    public FacebookData(long id, String firstName, String lastName, String email, int gender, String pictureUrl, Map<String, Boolean> permissions) {
	this.id = id;
	this.firstName = firstName;
	this.lastName = lastName;
	this.email = email;
	this.gender = gender;
	this.pictureUrl = pictureUrl;
	this.permissions = permissions;
    }

    public long getId() {
	return id;
    }

    public void setId(long id) {
	this.id = id;
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

    public String getEmail() {
	return email;
    }

    public void setEmail(String email) {
	this.email = email;
    }

    public int getGender() {
	return gender;
    }

    public void setGender(int gender) {
	this.gender = gender;
    }

    public String getPictureUrl() {
	return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
	this.pictureUrl = pictureUrl;
    }

    public Map<String, Boolean> getPermissions() {
	return permissions;
    }

    public void setPermissions(Map<String, Boolean> permissions) {
	this.permissions = permissions;
    }

}
