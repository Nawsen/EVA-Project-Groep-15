package hogent.group15;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import javax.json.Json;
import javax.json.JsonObjectBuilder;
import javax.json.JsonWriter;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;

/**
 *
 * @author Wannes
 */
@Provider
@Produces(MediaType.APPLICATION_JSON)
public class UserWriter implements MessageBodyWriter<User> {

    @Override
    public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
	return User.class.isAssignableFrom(type);
    }

    @Override
    public long getSize(User t, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
	return -1;
    }

    @Override
    public void writeTo(User t, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream) throws IOException, WebApplicationException {
	JsonObjectBuilder jsonCha = Json.createObjectBuilder();

	jsonCha.add("email", t.getEmail());
	jsonCha.add("firstName", t.getFirstName());
	jsonCha.add("lastName", t.getLastName());
	jsonCha.add("gender", t.getGender().name());
	if (t.getFacebookId() != 0) {
	    jsonCha.add("facebookId", t.getFacebookId());
	}
	if (t.getImageUrl() != null) {
	    jsonCha.add("imageUrl", t.getImageUrl());
	}
	if (t.getAddress().getCountry() != null) {
	    jsonCha.add("addressCountry", t.getAddress().getCountry());
	}
	if (t.getAddress().getCity() != null) {
	    jsonCha.add("addressCity", t.getAddress().getCity());
	}
	if (t.getAddress().getStreet() != null) {
	    jsonCha.add("addressStreet", t.getAddress().getStreet());
	}

	int i = t.getCompletedChallenges().size();
	jsonCha.add("completedCount", i);

	try (JsonWriter w = Json.createWriter(entityStream)) {
	    w.writeObject(jsonCha.build());
	}
    }

}
