package hogent.group15.ui.controls;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import org.w3c.dom.Text;

import hogent.group15.FontManager;

/**
 * Created by Brent on 10/20/2015.
 */
public class EvaMuseo900TextView extends TextView {
    public EvaMuseo900TextView(Context context) {
        super(context);
        init(context);
    }

    public EvaMuseo900TextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public EvaMuseo900TextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context con) {
        setTypeface(FontManager.getInstance(con).MUSEO_900);
    }
}
