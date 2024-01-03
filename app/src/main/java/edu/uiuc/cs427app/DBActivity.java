package edu.uiuc.cs427app;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * DBActivity is responsible for creating and managing the SQLite database used in the application.
 * The primary function is to store user account information like usernames, passwords, and their selected themes.
 */
public class DBActivity extends SQLiteOpenHelper {

    // Constants for the database
    private static final String DATABASE_NAME = "UserDatabase.db";
    private static final int DATABASE_VERSION = 4;

    // Table and column name definitions
    public static final String TABLE_USERS = "users";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_THEME = "theme"; // New column for storing user themes

    // SQL command to create the users table
    private static final String TABLE_CREATE =
            "create table " + TABLE_USERS + " (" +
                    COLUMN_ID + " integer primary key autoincrement, " +
                    COLUMN_USERNAME + " text not null, " +
                    COLUMN_PASSWORD + " text not null, " +
                    COLUMN_THEME + " text);";

    /**
     * Constructor for the DBActivity class.
     * @param context Application context.
     */
    public DBActivity(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Called when the database is created for the first time.
     * @param db SQLiteDatabase object.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
    }

    /**
     * Called when the database version is updated.
     * It drops the existing users table and recreates it.
     * @param db SQLiteDatabase object.
     * @param oldVersion Old version of the database.
     * @param newVersion New version of the database.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }

    /**
     * Inserts user data into the database.
     * @param username User's name.
     * @param password User's password.
     * @param theme User's selected theme.
     * @return An integer indicating the insert result (1: user exists, 2: error, 0: successful).
     */
    public int insertData(String username, String password, String theme) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("SELECT * FROM users WHERE username = ?", new String[] {username});
        if(cursor.getCount() > 0) {
            cursor.close();
            MyDB.close();
            return 1;
        } else {
            cursor.close();
            ContentValues values = new ContentValues();
            values.put("username", username);
            values.put("password", password);
            values.put("theme", theme);

            long result = MyDB.insert("users", null, values);
            if(result == -1) {
                MyDB.close();
                return 2;
            } else {
                MyDB.close();
                return 0;
            }
        }
    }

    /**
     * Verifies the user's credentials by checking their username and password in the database.
     * @param username User's name.
     * @param password User's password.
     * @return Boolean indicating whether the credentials are correct.
     */
    public Boolean checkUserPassword(String username, String password) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("SELECT * FROM users WHERE username = ? AND password = ?", new String[] {username, password});
        if(cursor.getCount() > 0) {
            cursor.close();
            MyDB.close();
            return true;
        } else {
            cursor.close();
            MyDB.close();
            return false;
        }
    }

    /**
     * Retrieves the theme associated with a specific user from the database.
     * @param username User's name.
     * @return The theme associated with the user, or null if not found.
     */
    public String getUserTheme(String username) {
        SQLiteDatabase MyDB = this.getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = MyDB.rawQuery("SELECT " + COLUMN_THEME + " FROM " + TABLE_USERS + " WHERE " + COLUMN_USERNAME + " = ?", new String[]{username});
            if (cursor.moveToFirst()) {
                int themeIndex = cursor.getColumnIndex(COLUMN_THEME);
                if (themeIndex != -1) {
                    return cursor.getString(themeIndex);
                }
            }
            return null;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            MyDB.close();
        }
    }
}
