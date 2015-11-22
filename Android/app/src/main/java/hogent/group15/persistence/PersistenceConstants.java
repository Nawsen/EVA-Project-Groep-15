package hogent.group15.persistence;

import android.provider.BaseColumns;

/**
 * Created by Brent on 11/22/2015.
 */
public class PersistenceConstants {

    public static final String DATABASE_NAME = "EvaChallenges";
    public static final int DATABASE_VERSION = 1;

    public enum Queries {

        CREATE_CHALLENGES_TABLE("CREATE TABLE %s (%s INTEGER PRIMARY KEY, %s VARCHAR(50))", //TODO: Query verder aanvullen!
                ChallengeEntry.TABLE_NAME,
                ChallengeEntry._ID,
                ChallengeEntry.TITLE,
                ChallengeEntry.LOCAL_IMAGE,
                ChallengeEntry.DESCRIPTION,
                ChallengeEntry.DIFFICULTY),
        DELETE_CHALLENGES_TABLE("DROP TABLE IF EXISTS %s",
                ChallengeEntry.TABLE_NAME);

        private String query;
        private String unformatted;
        Queries(String s, Object ... os) {
            query = String.format(s, os);
            unformatted = s;
        }

        public String getQuery() {
            return query;
        }

        public void reformat(Object ... os) {
            query = String.format(unformatted, os);
        }
    }

    public static abstract class ChallengeEntry implements BaseColumns {
        public static final String TABLE_NAME = "challenge";
        public static final String TITLE = "challengeTitle";
        public static final String LOCAL_IMAGE = "imgPath";
        public static final String DESCRIPTION = "challengeDesc";
        public static final String DIFFICULTY = "difficulty";
    }
}
