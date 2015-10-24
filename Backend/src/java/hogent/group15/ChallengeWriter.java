/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hogent.group15;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import javax.json.Json;
import javax.json.JsonObjectBuilder;
import javax.json.JsonWriter;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;

/**
 *
 * @author Wannes
 */
public class ChallengeWriter implements MessageBodyWriter<Challenge> {

    @Override
    public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return User.class.isAssignableFrom(type);
    }

    @Override
    public long getSize(Challenge t, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return -1;
    }

    @Override
    public void writeTo(Challenge t, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream) throws IOException, WebApplicationException {
        JsonObjectBuilder jsonCha = Json.createObjectBuilder();

        jsonCha.add("id", t.getId());
        jsonCha.add("title", t.getTitle());
        jsonCha.add("description", t.getDescription());
        jsonCha.add("difficulty", t.getDifficulty().name());
        
        try(JsonWriter w = Json.createWriter(entityStream)){
            w.writeObject(jsonCha.build());
        }
    }

}
