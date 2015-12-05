package hogent.group15;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
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
 * @author Frederik
 */
@Provider
@Produces(MediaType.APPLICATION_JSON)
public class AchievementListWriter implements MessageBodyWriter<List<Achievement>> {

    @Override
    public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
	if (!List.class.isAssignableFrom(type)) {
	    return false;
	}

	if (genericType instanceof ParameterizedType) {
	    Type[] arguments = ((ParameterizedType) genericType).getActualTypeArguments();
	    return arguments.length == 1 && arguments[0].equals(Achievement.class);
	} else {
	    return false;
	}

    }

    @Override
    public long getSize(List<Achievement> t, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
	return -1;
    }

    @Override
    public void writeTo(List<Achievement> achievements, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream) throws IOException, WebApplicationException {
	try (JsonWriter writer = Json.createWriter(entityStream)) {
	    JsonArrayBuilder array = Json.createArrayBuilder();
	    for (Achievement achievement : achievements) {
		JsonObjectBuilder object = Json.createObjectBuilder();
		object.add("name", achievement.getName());
		object.add("score", achievement.getScore());
		object.add("description", achievement.getDescription());
		array.add(object.build());
	    }
	    
	    writer.write(array.build());
	}
    }

}
