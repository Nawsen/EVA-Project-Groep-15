package hogent.group15.service;

/**
 * Created by Frederik on 11/2/2015.
 */
public class JsonWebToken {

    String token;

    public JsonWebToken() {
    }

    public JsonWebToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
