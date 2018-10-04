package storage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class DBHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "SOADB.db";
    private static final String SQL_CREATE_USER_TABLE =
            "CREATE TABLE IF NOT EXISTS " + UserContract.User.TABLE_NAME + " (" +
                    UserContract.User.COLUMN_NAME_USER_ID + " INTEGER PRIMARY KEY," +
                    UserContract.User.COLUMN_NAME_USER_DB_ID + " TEXT," +
                    UserContract.User.COLUMN_NAME_USER_NAME + " TEXT," +
                    UserContract.User.COLUMN_NAME_USER_EMAIL + " TEXT, " +
                    UserContract.User.COLUMN_NAME_USER_IMAGE + " TEXT," +
                    UserContract.User.COLUMN_NAME_USER_TOKEN + " TEXT,"+
                    UserContract.User.COLUMN_NAME_USER_SPORTS + " TEXT)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS" + UserContract.User.TABLE_NAME;

    /**
     * Constructor: only gets the context of the application.
     * @param context
     */
    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Called when the database is created for the first time. This is where the
     * creation of tables and the initial population of the tables should happen.
     *
     * @param db The database.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(SQL_CREATE_USER_TABLE);
    }

    /**
     * Called when the database needs to be upgraded. The implementation
     * should use this method to drop tables, add tables, or do anything else it
     * needs to upgrade to the new schema version.
     * <p>
     * <p>
     * The SQLite ALTER TABLE documentation can be found
     * <a href="http://sqlite.org/lang_altertable.html">here</a>. If you add new columns
     * you can use ALTER TABLE to insert them into a live table. If you rename or remove columns
     * you can use ALTER TABLE to rename the old table, then create the new table and then
     * populate the new table with the contents of the old table.
     * </p><p>
     * This method executes within a transaction.  If an exception is thrown, all changes
     * will automatically be rolled back.
     * </p>
     *
     * @param db         The database.
     * @param oldVersion The old database version.
     * @param newVersion The new database version.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    /**
     * Called when the database needs to be downgraded. This is strictly similar to
     * {@link #onUpgrade} method, but is called whenever current version is newer than requested one.
     * However, this method is not abstract, so it is not mandatory for a customer to
     * implement it. If not overridden, default implementation will reject downgrade and
     * throws SQLiteException
     * <p>
     * <p>
     * This method executes within a transaction.  If an exception is thrown, all changes
     * will automatically be rolled back.
     * </p>
     *
     * @param db         The database.
     * @param oldVersion The old database version.
     * @param newVersion The new database version.
     */
    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onDowngrade(db, oldVersion, newVersion);
        onUpgrade(db, oldVersion, newVersion);
    }

    public String checkUserCredentials (){
        String result = "";
        SQLiteDatabase db = this.getReadableDatabase();
        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                UserContract.User.COLUMN_NAME_USER_ID,
                UserContract.User.COLUMN_NAME_USER_DB_ID,
                UserContract.User.COLUMN_NAME_USER_NAME,
                UserContract.User.COLUMN_NAME_USER_EMAIL,
                UserContract.User.COLUMN_NAME_USER_IMAGE,
                UserContract.User.COLUMN_NAME_USER_TOKEN,
                UserContract.User.COLUMN_NAME_USER_SPORTS

        };


        // How you want the results sorted in the resulting Cursor
        String sortOrder =
                UserContract.User.COLUMN_NAME_USER_ID + " DESC";

        try {
            Cursor cursor = db.query(
                    UserContract.User.TABLE_NAME,   // The table to query
                    projection,             // The array of columns to return (pass null to get all)
                    null,              // The columns for the WHERE clause
                    null,          // The values for the WHERE clause
                    null,                   // don't group the rows
                    null,                   // don't filter by row groups
                    sortOrder               // The sort order
            );
            if(cursor.getCount() > 0){
                List itemIds = new ArrayList<>();
                while(cursor.moveToNext()) {
                    String itemId = cursor.getString(
                            cursor.getColumnIndexOrThrow(UserContract.User.COLUMN_NAME_USER_NAME));
                    itemIds.add(itemId);
                    try{
                        JsonObject resultJson = new JsonObject();
                        resultJson.addProperty(UserContract.User.COLUMN_NAME_USER_NAME, itemId);
                        String userId = cursor.getString(
                                cursor.getColumnIndexOrThrow(UserContract.User.COLUMN_NAME_USER_DB_ID));
                        resultJson.addProperty(UserContract.User.COLUMN_NAME_USER_DB_ID, userId);
                        String email = cursor.getString(
                                cursor.getColumnIndexOrThrow(UserContract.User.COLUMN_NAME_USER_EMAIL));
                        resultJson.addProperty(UserContract.User.COLUMN_NAME_USER_EMAIL, email);
                        String sports =cursor.getString(
                                cursor.getColumnIndexOrThrow(UserContract.User.COLUMN_NAME_USER_SPORTS));
                        resultJson.addProperty(UserContract.User.COLUMN_NAME_USER_SPORTS, sports);
                        String photo =cursor.getString(
                                cursor.getColumnIndexOrThrow(UserContract.User.COLUMN_NAME_USER_IMAGE));
                        resultJson.addProperty(UserContract.User.COLUMN_NAME_USER_IMAGE, photo);
                        String token =cursor.getString(
                                cursor.getColumnIndexOrThrow(UserContract.User.COLUMN_NAME_USER_TOKEN));
                        resultJson.addProperty(UserContract.User.COLUMN_NAME_USER_TOKEN, token);
                        Log.i("JSON  result", resultJson.toString());
                    }
                    catch(Exception ee){
                        Log.i("JSON  error fetch data", ee.getMessage());
                    }


                }
                cursor.close();
                result =  String.valueOf(itemIds.get(0)) ;

            }else{
                Log.i("JSON credentials", "Nobody is logged");
            }

        }
        catch (Exception e){
            Log.i("JOSN credentials: error", e.getMessage());
        }
        return result;
    }

    /**
     * Adds a sesión to tha user
     * @param userId
     * @param username
     * @param email
     * @param image
     * @return
     */
    public long addUserCredentials (String userId,String username, String email, String image,String access_token,String sports){
        SQLiteDatabase db = this.getWritableDatabase();
        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(UserContract.User.COLUMN_NAME_USER_DB_ID, userId);
        values.put(UserContract.User.COLUMN_NAME_USER_NAME, username);
        values.put(UserContract.User.COLUMN_NAME_USER_EMAIL, email);
        values.put(UserContract.User.COLUMN_NAME_USER_IMAGE, image);
        values.put(UserContract.User.COLUMN_NAME_USER_TOKEN, access_token);
        values.put(UserContract.User.COLUMN_NAME_USER_SPORTS, sports);
        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(UserContract.User.TABLE_NAME, null, values);
        return newRowId;
    }

    public int removeCredentials (){
        int result = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        String table = UserContract.User.TABLE_NAME;
        result = db.delete(table, null, null);
        return  result;
    }


}