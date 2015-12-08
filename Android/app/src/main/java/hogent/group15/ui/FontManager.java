package hogent.group15.ui;

import android.content.Context;
import android.graphics.Typeface;

/**
 * Created by Brent on 10/13/2015.
 */
public class FontManager {

    public Typeface ARTISTAMP_MEDIUM, GEORGIA, MATHLETE, MUSEO, MYRIAD_PRO, MUSEO_500, MUSEO_900;

    private static FontManager instance;

    private FontManager(Context con) {
        ARTISTAMP_MEDIUM = Typeface.createFromAsset(con.getAssets(), "ArtistampMedium.ttf");
        GEORGIA = Typeface.createFromAsset(con.getAssets(), "Georgia.ttf");
        MATHLETE = Typeface.createFromAsset(con.getAssets(), "Mathlete-Skinny.otf");
        MUSEO = Typeface.createFromAsset(con.getAssets(), "Museo300-Regular.otf");
        MYRIAD_PRO = Typeface.createFromAsset(con.getAssets(), "MyriadPro-Regular.otf");
        MUSEO_500 = Typeface.createFromAsset(con.getAssets(), "Museo500-Regular.otf");
        MUSEO_900 = Typeface.createFromAsset(con.getAssets(), "Museo900-Regular.otf");
    }

    public static FontManager getInstance(Context context) {
        if (instance == null) instance = new FontManager(context);
        return instance;
    }
}
