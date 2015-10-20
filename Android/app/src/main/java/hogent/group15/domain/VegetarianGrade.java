package hogent.group15.domain;

import hogent.group15.ui.R;

/**
 * Created by Frederik on 10/20/2015.
 */
public enum VegetarianGrade {

    OMNIVORE(R.string.grade_omnivore), PESCETARIAR(R.string.grade_pescetariar), PARTTIME_VEGETARIAN(R.string.grade_parttime_vegetarian),
    VEGETARIAN(R.string.grade_vegatarian), VEGAN(R.string.grade_vegan);

    private final int descriptionId;

    private VegetarianGrade(int descriptionId) {
        this.descriptionId = descriptionId;
    }

    public int getAndroidId() {
        return descriptionId;
    }
}
