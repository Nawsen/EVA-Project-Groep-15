package hogent.group15;

import hogent.group15.User.Gender;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
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
 * @author Wannes
 */
@Consumes(MediaType.APPLICATION_JSON)
@Provider
public class ChallengeJsonReader implements MessageBodyReader<Challenge> {

    @Override
    public boolean isReadable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
	return type.equals(Challenge.class);
    }

    @Override
    public Challenge readFrom(Class<Challenge> type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, String> httpHeaders, InputStream entityStream) throws IOException, WebApplicationException {
	try (JsonReader reader = Json.createReader(entityStream)) {
	    JsonObject jsonChallenge = reader.readObject();

	    String title = jsonChallenge.getString("title", "");
	    String descr = jsonChallenge.getString("description", "");
	    String imageUrl = jsonChallenge.getString("imageUrl", "");
	    int tempDiff = jsonChallenge.getInt("difficulty", -1);
            if (tempDiff == 0){
                return new Challenge(title, descr, imageUrl, Challenge.Difficulty.EASY);
            }
            if (tempDiff == 1){
                return new Challenge(title, descr, imageUrl, Challenge.Difficulty.MEDIUM);
            }
            if (tempDiff == 2){
                return new Challenge(title, descr, imageUrl, Challenge.Difficulty.HARD);
            }

            return new Challenge(title, descr, imageUrl, Challenge.Difficulty.EASY);
	    
	}
    }



}
