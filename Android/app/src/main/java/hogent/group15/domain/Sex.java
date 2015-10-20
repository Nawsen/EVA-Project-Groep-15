package hogent.group15.domain;

import hogent.group15.ui.R;

/**
 * Created by Frederik on 10/20/2015.
 */
public enum Sex {

    MALE(0, R.string.register_sex_male), FEMALE(1, R.string.register_sex_female);

    private final int value;
    private final int id;

    private Sex(int value, int id) {
        this.value = value;
        this.id = id;
    }

    public int asInt() {
        return value;
    }

    public int getAndroidId() {
        return id;
    }
}
