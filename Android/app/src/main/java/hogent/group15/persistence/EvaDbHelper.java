package hogent.group15.persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Brent on 11/22/2015.
 */
public class EvaDbHelper extends SQLiteOpenHelper {

    private Context context;

    public EvaDbHelper(Context context) {
        super(context, PersistenceConstants.DATABASE_NAME, null, PersistenceConstants.DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
