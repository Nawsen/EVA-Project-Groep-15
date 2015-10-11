package hogent.group15;

/**
 * Created by Frederik on 10/11/2015.
 */
public enum Logging {

    ERROR("EVA_Error"), INFORMATION("EVA_Information");

    private final String tag;
    private Logging(String tag) {
        this.tag = tag;
    }

    public String getTag() {
        return tag;
    }
}
