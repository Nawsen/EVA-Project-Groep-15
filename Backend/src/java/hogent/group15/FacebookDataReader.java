package hogent.group15;

import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import javax.json.Json;
import javax.json.JsonArray;
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
 * @author Brent C.
 */
@Consumes(MediaType.APPLICATION_JSON)
@Provider
public class FacebookDataReader implements MessageBodyReader<FacebookData> {

    @Override
    public boolean isReadable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
	return FacebookData.class.isAssignableFrom(type);
    }

    @Override
    public FacebookData readFrom(Class<FacebookData> type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, String> httpHeaders, InputStream entityStream) throws IOException, WebApplicationException {
	FacebookData fdc = null;
	try (JsonReader reader = Json.createReader(entityStream)) {
	    JsonObject root = reader.readObject();
	    long id = Long.parseLong(root.getString("id", "0"));
	    String email = root.getString("email", "");
	    String firstName = root.getString("first_name", "");
	    String lastName = root.getString("last_name", "");
	    JsonObject picture = root.getJsonObject("picture");
	    String pictureUrl = "";
	    String gender = root.getString("gender", "");
	    Map<String, Boolean> permissionsMap = new HashMap<>();
	    if (picture != null) {
		JsonObject pData = picture.getJsonObject("data");
		if (pData != null) {
		    pictureUrl = pData.getString("url", "");
		}
	    }

	    JsonObject permissions = root.getJsonObject("permissions");
	    if (permissions != null) {
		JsonArray pData = permissions.getJsonArray("data");
		if (pData != null) {
		    for (int i = 0; i < pData.size(); i++) {
			JsonObject permissionsObject = pData.getJsonObject(i);
			String permissionKey = permissionsObject.getString("permission", "");
			String status = permissionsObject.getString("status", "");
			permissionsMap.put(permissionKey, status.equalsIgnoreCase("granted"));
		    }

		}
	    }

	    fdc = new FacebookData(id, firstName, lastName, email, gender, pictureUrl, permissionsMap);
	}
	return fdc;
    }

}
