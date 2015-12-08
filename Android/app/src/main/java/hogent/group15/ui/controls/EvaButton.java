package hogent.group15.ui.controls;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;

import hogent.group15.ui.FontManager;

/**
 * Created by Brent on 10/13/2015.
 */
public class EvaButton extends Button {

    public EvaButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        setTypeface(FontManager.getInstance(context).MUSEO);
    }
}
