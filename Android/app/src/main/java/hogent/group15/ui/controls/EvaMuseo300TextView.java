package hogent.group15.ui.controls;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.widget.TextView;

import hogent.group15.FontManager;
import hogent.group15.ui.R;

/**
 * Created by Frederik on 10/18/2015.
 */
public class EvaMuseo300TextView extends TextView {

    public EvaMuseo300TextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setTypeface(FontManager.getInstance(context).MUSEO);
    }
}
