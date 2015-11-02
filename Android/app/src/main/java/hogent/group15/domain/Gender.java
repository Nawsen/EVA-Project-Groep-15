package hogent.group15.domain;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.stream.JsonWriter;

import java.lang.reflect.Type;

import hogent.group15.ui.R;

/**
 * Created by Frederik on 10/20/2015.
 */
public enum Gender {

    MALE(0, R.string.register_sex_male), FEMALE(1, R.string.register_sex_female);

    private final int value;
    private final int id;

    private Gender(int value, int id) {
        this.value = value;
        this.id = id;
    }

    public int asInt() {
        return value;
    }

    public int getAndroidId() {
        return id;
    }

    public static class GenderSerializer implements JsonSerializer<Gender> {

        private final JsonParser parser = new JsonParser();

        @Override
        public JsonElement serialize(Gender src, Type typeOfSrc, JsonSerializationContext context) {
            return parser.parse(((Integer) src.ordinal()).toString());
        }
    }
}
