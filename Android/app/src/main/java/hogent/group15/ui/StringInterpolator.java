package hogent.group15.ui;

import java.util.HashMap;
import java.util.Map;

/**
 * Utility class that can interpolate strings typically used in the user interface. In the
 * interpolated {@link String} keys are represented by encapsulating them with double semicolons,
 * example: <code>"Hello {{username}}"</code>. This either avoids complex user interfaces by using
 * multiple {@link android.widget.TextView}s or avoids boilerplate code used to change a
 * {@link android.widget.TextView} without losing the initial text.
 */
public class StringInterpolator {

    private String expression;

    private Map<String, String> replacementMap = new HashMap<>();

    private StringInterpolator(String expression) {
        this.expression = expression;
    }

    private StringInterpolator(String expression, Map<String, String> replacements) {
        this.expression = expression;
        this.replacementMap.putAll(replacements);
    }

    /**
     * Simple Factory providing a Fluent API to interpolate {@link String}s.
     *
     * @param expression the {@link String} that should be interpolated
     * @return a new {@link StringInterpolator}
     */
    public static StringInterpolator interpolate(String expression) {
        return new StringInterpolator(expression);
    }

    /**
     * Interpolates a {@link String} by using the values given, note that in the interpolation
     * process the keys are ignored and are replaced based on the index of the values.
     * Should be avoided when dealing with complex interpolation yet can be useful when
     * interpolating {@link String}s containing a limited amount of keys.
     *
     * @param expression the {@link String} that should be interpolated
     * @param values a list of all the values that will replace parts of the expression
     * @return the interpolated {@link String}
     */
    public static String interpolate(String expression, Object... values) {
        String result = expression;

        for(Object value : values) {
            result = result.replaceFirst("\\{\\{.*\\}\\}", value.toString());
        }

        return result;
    }

    /**
     * Interpolates a {@link String} based on the given configuration. The configuration exists of
     * key-value pairs that will be used in the interpolation process.
     *
     * @param expression the {@link String} that should be interpolated
     * @param configuration the configuration containing key-value pairs
     * @return the interpolated {@link String}
     */
    public static String interpolate(String expression, Map<String, String> configuration) {
        return new StringInterpolator(expression, configuration).interpolate();
    }

    /**
     * Adds a key-value pair in the existing configuration.
     *
     * @param key the key used in the expression
     * @param value the value by which it should be replaced
     * @return the current {@link StringInterpolator}
     */
    public StringInterpolator with(String key, Object value) {
        replacementMap.put(key, value.toString());
        return this;
    }

    /**
     * Interpolates the {@link String} based on the configuration created using
     * {@link StringInterpolator#with(String, Object)}.
     *
     * @return the interpolated {@link String}
     */
    public String interpolate() {
        String result = expression;
        for(Map.Entry<String, String> entry : replacementMap.entrySet()) {
            result = result.replaceAll(String.format("\\{\\{%s\\}\\}", entry.getKey()), entry.getValue());
        }

        return result;
    }
}
