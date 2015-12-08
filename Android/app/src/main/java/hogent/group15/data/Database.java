package hogent.group15.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import hogent.group15.domain.Challenge;

/**
 * Created by Frederik on 11/27/2015.
 */
public class Database {

    private static Database database;

    private static final String DATABASE_NAME = "eva";
    private static final int DATABASE_VERSION = 8;
    private static final String TAG = Database.class.getName();

    private final EvaDatabaseHelper helper;
    private Dao<Challenge, Integer> challengeDao;

    private Database(Context context) {
        helper = new EvaDatabaseHelper(context, DATABASE_NAME, null, DATABASE_VERSION);

        try {
            Log.i(TAG, "Retrieving DAO for Challenge");
            challengeDao = helper.getDao(Challenge.class);
            Log.i(TAG, "Retrieved DAO for Challenge");
        } catch (SQLException e) {
            Log.e(TAG, "Couldn't retrieve DAO for Challenge", e);
        }
    }

    public static Database getInstance(Context context) {
        if (database == null) {
            database = new Database(context);
        }

        return database;
    }

    public synchronized Challenge findChallengeById(int id) {
        Log.i(TAG, "Retrieving challenge with id " + id);
        if (challengeDao == null) {
            return null;
        }

        try {
            return challengeDao.queryForId(id);
        } catch (SQLException e) {
            Log.e(TAG, "Couldn't retrieve challenge with id " + id, e);
            return null;
        }
    }

    public synchronized void saveChallenges(List<Challenge> challenges) {
        Log.i(TAG, "Saving " + challenges.size() + " challenges");

        if (challengeDao == null) {
            Log.e(TAG, "Couldn't save challenges because challengeDAO is not present");
            return;
        }

        for (Challenge c : challenges) {
            try {
                challengeDao.create(c);
            } catch (SQLException e) {
                Log.e(TAG, "Couldn't save challenge with id " + c.getId() + " into database", e);
            }
        }
    }

    public synchronized List<Challenge> getDailyChallenges() {
        Log.i(TAG, "Retrieving today's daily challenges");
        if (challengeDao == null) {
            return null;
        }

        QueryBuilder<Challenge, Integer> builder = challengeDao.queryBuilder();
        Calendar today = new GregorianCalendar();
        String date = today.get(Calendar.YEAR) + "-" + (today.get(Calendar.MONTH) + 1) + "-" + today.get(Calendar.DAY_OF_MONTH);

        try {
            builder.where().eq("date", date);
            return challengeDao.query(builder.prepare());
        } catch (SQLException e) {
            Log.e(TAG + ": getDailyChallenges", "Couldn't construct query", e);
            throw new RuntimeException();
        }
    }

    private static class EvaDatabaseHelper extends OrmLiteSqliteOpenHelper {

        private static final String TAG = EvaDatabaseHelper.class.getName();

        public EvaDatabaseHelper(Context context, String databaseName, SQLiteDatabase.CursorFactory factory, int databaseVersion) {
            super(context, databaseName, factory, databaseVersion);
        }

        @Override
        public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
            try {
                Log.i(TAG, "Creating new database");
                TableUtils.createTable(connectionSource, Challenge.class);
                Log.i(TAG, "Successfully created new database");
            } catch (SQLException e) {
                Log.e(TAG, "Cannot create database: ", e);
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
            if (oldVersion != newVersion) {
                Log.i(TAG, "Upgrading database from version " + oldVersion + " up to " + newVersion);
                try {
                    TableUtils.dropTable(connectionSource, Challenge.class, true);
                } catch (SQLException e) {
                    Log.e(TAG, "Couldn't drop table Challenge", e);
                }

                onCreate(database, connectionSource);
            }
        }
    }
}
