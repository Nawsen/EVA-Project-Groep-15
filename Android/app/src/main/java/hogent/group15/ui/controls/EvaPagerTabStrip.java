package hogent.group15.ui.controls;

import android.content.Context;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.PagerTabStripV22;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import hogent.group15.FontManager;

/**
 * Created by Brent on 10/24/2015.
 */
public class EvaPagerTabStrip extends PagerTabStripV22 {

    public EvaPagerTabStrip(Context context) {
        super(context);
        init(context);
    }

    public EvaPagerTabStrip(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        for (int i = 0; i < getChildCount(); ++i) {
            View nextChild = getChildAt(i);
            if (nextChild instanceof TextView) {
                TextView textViewToConvert = (TextView) nextChild;
                textViewToConvert.setTypeface(FontManager.getInstance(context).MUSEO_900);
                textViewToConvert.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            }
        }

        this.buildLayer();
    }
}
