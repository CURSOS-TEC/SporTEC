package storage;

import android.provider.BaseColumns;

/**
 *This contract is used to abstracts the user table on the data base*/
public class UserContract {
    /* Inner class that defines the table contents */
    public static class User implements BaseColumns {
        public static final String TABLE_NAME = "SoaUser";
        public static final String COLUMN_NAME_USER_ID = "id";
        public static final String COLUMN_NAME_USER_DB_ID = "userId";
        public static final String COLUMN_NAME_USER_NAME = "username";
        public static final String COLUMN_NAME_USER_EMAIL = "email";
        public static final String COLUMN_NAME_USER_IMAGE = "image";
        public static final String COLUMN_NAME_USER_TOKEN = "token";
    }
}
