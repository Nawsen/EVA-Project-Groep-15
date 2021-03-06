package hogent.group15;

import hogent.group15.User.Gender;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.ws.rs.Consumes;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.Provider;

/**
 *
 * @author Frederik
 */
@Consumes(MediaType.APPLICATION_JSON)
@Provider
public class UserJsonReader implements MessageBodyReader<User> {

    @Override
    public boolean isReadable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
	return type.equals(User.class);
    }

    @Override
    public User readFrom(Class<User> type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, String> httpHeaders, InputStream entityStream) throws IOException, WebApplicationException {
	try (JsonReader reader = Json.createReader(entityStream)) {
	    JsonObject jsonUser = reader.readObject();

	    String firstName = jsonUser.getString("firstName", "");
	    String lastName = jsonUser.getString("lastName", "");
	    String email = jsonUser.getString("email", "");
	    String password = jsonUser.getString("password", "");
	    int tempGender = jsonUser.getInt("gender", -1);
	    long facebookId = Long.parseLong(jsonUser.getString("facebookId", "0"));
	    String imageUrl = jsonUser.getString("imageUrl", "http://www.gravatar.com/avatar/00000000000000000000000000000000?d=mm");
	    Gender gender = tempGender < 0 || tempGender > 1 ? null : Gender.values()[tempGender];
	    Address address = new Address(jsonUser.getString("country", ""), jsonUser.getString("street", ""), jsonUser.getString("city", ""));
	    User.VegetarianGrade grade = null;

	    if (!jsonUser.getString("grade", "").equals("")) {
		try {
		    grade = User.VegetarianGrade.valueOf(jsonUser.getString("grade"));
		} catch (IllegalArgumentException ex) {
		    grade = User.VegetarianGrade.UNKNOWN;
		}
	    }
	    
	    String facebookAccessToken = jsonUser.getString("accessToken", "");

	    User user = new User(firstName, lastName, email, password, gender, address, grade);
	    user.setImageUrl(imageUrl);
	    user.setFacebookId(facebookId);
	    user.setAccessToken(facebookAccessToken);
	    return user;
	}
    }

}
