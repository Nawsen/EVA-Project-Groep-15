/**
 * Created by Mathias on 14-Dec-15.
 */
angular.module('eva').factory('grades', function (translation) {
    valueFactory = {};
    valueFactory.values = [
        {
            "val": "OMNIVORE"
        },
        {
            "val": "PESCETARIAR"
        },
        {
            "val": "PARTTIME_VEGETARIAN"
        },
        {
            "val": "VEGETARIAN"
        },
        {
            "val": "VEGAN"
        }
    ];
    valueFactory.getTranslation = function (val) {
        return translation.getCurrentlySelected()["grade_" + val.toLowerCase()]
    };
    return valueFactory;
});