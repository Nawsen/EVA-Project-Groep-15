package hogent.group15.ui.controls;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import hogent.group15.ui.FontManager;

/**
 * Created by Frederik on 10/18/2015.
 */
public class EvaMuseo500TextView extends TextView {

    public EvaMuseo500TextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setTypeface(FontManager.getInstance(context).MUSEO_500);
    }
}
