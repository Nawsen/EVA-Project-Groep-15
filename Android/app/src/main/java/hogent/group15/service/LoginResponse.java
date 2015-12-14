package hogent.group15.service;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by Frederik on 11/2/2015.
 */
public class LoginResponse implements Serializable {

    String token = "";

    long id;
    String firstName = "";
    String lastName = "";
    String email = "";
    int gender;
    String pictureUrl = "";

    String fbAccessToken;

    public String getFbAccessToken() {
        return fbAccessToken;
    }

    public void setFbAccessToken(String fbAccessToken) {
        this.fbAccessToken = fbAccessToken;
    }

    private LoginResponseType type = LoginResponseType.REGULAR;

    public LoginResponse() {
    }

//    @Override
//    public int describeContents() {
//        return 0;
//    }
//
//    @Override
//    public void writeToParcel(Parcel dest, int flags) {
//        dest.writeLong(id);
//        dest.writeString(firstName);
//        dest.writeString(lastName);
//        dest.writeString(email);
//        dest.writeInt(gender);
//        dest.writeString(pictureUrl);
//        dest.writeString(token);
//    }

    public static final Parcelable.Creator<LoginResponse> CREATOR = new Parcelable.Creator<LoginResponse>() {

        @Override
        public LoginResponse createFromParcel(Parcel source) {
            LoginResponse lr = new LoginResponse();
            lr.setId(source.readLong());
            lr.setFirstName(source.readString());
            lr.setLastName(source.readString());
            lr.setEmail(source.readString());
            lr.setGender(source.readInt());
            lr.setPictureUrl(source.readString());
            lr.setToken(source.readString());
            lr.setType(LoginResponseType.FACEBOOK_REGISTER);
            return lr;
        }

        @Override
        public LoginResponse[] newArray(int size) {
            return new LoginResponse[size];
        }
    };

    public enum LoginResponseType {
        REGULAR, FACEBOOK_REGISTER
    }

    public LoginResponseType getType() {
        return type;
    }

    public void setType(LoginResponseType type) {
        this.type = type;
    }

    public LoginResponse(String token) {
        this.token = token;
    }

    public LoginResponse(String token, long id, String firstName, String lastName, String email, int gender, String pictureUrl) {
        this.token = token;
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.gender = gender;
        this.pictureUrl = pictureUrl;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
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
}
