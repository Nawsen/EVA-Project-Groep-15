package hogent.group15.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;

import hogent.group15.FontManager;

/**
 * Created by Frederik on 10/13/2015.
 */
public class EvaEditText extends EditText {

    public EvaEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        setTypeface(FontManager.getInstance(context).MUSEO);
    }
}
