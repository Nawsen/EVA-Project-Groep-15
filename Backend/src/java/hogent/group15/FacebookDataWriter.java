package hogent.group15;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import javax.json.Json;
import javax.json.JsonObject;
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
 * @author Brent C.
 */
@Produces(MediaType.APPLICATION_JSON)
@Provider
public class FacebookDataWriter implements MessageBodyWriter<FacebookData> {

    @Override
    public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
	return FacebookData.class.isAssignableFrom(type);
    }

    @Override
    public long getSize(FacebookData t, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
	return -1;
    }

    @Override
    public void writeTo(FacebookData data, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream) throws IOException, WebApplicationException {
	JsonObjectBuilder o = Json.createObjectBuilder();

	o.add("id", data.getId())
		.add("email", data.getEmail())
		.add("firstName", data.getFirstName())
		.add("lastName", data.getLastName())
		.add("gender", data.getGender())
		.add("pictureUrl", data.getPictureUrl());
	try (JsonWriter w = Json.createWriter(entityStream)) {
	    w.writeObject(o.build());
	}
    }

}
