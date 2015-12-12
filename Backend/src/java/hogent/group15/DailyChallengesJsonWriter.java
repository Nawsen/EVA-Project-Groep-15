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
@Produces(MediaType.APPLICATION_JSON)
@Provider
public class DailyChallengesJsonWriter implements MessageBodyWriter<List<DailyChallenges>> {

    @Override
    public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
	if (!List.class.isAssignableFrom(type)) {
	    return false;
	}

	if (genericType instanceof ParameterizedType) {
	    Type[] arguments = ((ParameterizedType) genericType).getActualTypeArguments();
	    return arguments.length == 1 && arguments[0].equals(DailyChallenges.class);
	} else {
	    return false;
	}
    }

    @Override
    public long getSize(List<DailyChallenges> t, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
	return -1;
    }

    @Override
    public void writeTo(List<DailyChallenges> list, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream) throws IOException, WebApplicationException {
	JsonArrayBuilder jsonChallenges = Json.createArrayBuilder();

	for (DailyChallenges dc : list) {
	    for (Challenge c : dc.getChallenges()) {
		JsonObjectBuilder jsonCha = Json.createObjectBuilder();

		jsonCha.add("id", c.getId());
		jsonCha.add("title", c.getTitle());
		jsonCha.add("difficulty", c.getDifficulty().name());
		jsonCha.add("imageUrl", c.getImageUrl());
		if (c.getDescription() != null && !c.getDescription().isEmpty() && dc.isDetailed()) {
		    jsonCha.add("description", c.getDescription());
		}

		if (dc.getDate() != null) {
		    jsonCha.add("date", dc.getDate().toString());
		}

		jsonChallenges.add(jsonCha);
	    }
	}

	try (JsonWriter out = Json.createWriter(entityStream)) {
	    out.writeArray(jsonChallenges.build());
	}
    }
}
