package com.mad.it19073774.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import java.util.ArrayList;
import java.util.List;

public class DBHandler extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Database.db";

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES_USER);
        db.execSQL(SQL_CREATE_ENTRIES_MOVIE);
        db.execSQL(SQL_CREATE_ENTRIES_COMMENT);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES_USER);
        db.execSQL(SQL_DELETE_ENTRIES_COMMENT);
        db.execSQL(SQL_DELETE_ENTRIES_MOVIE);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    private static final String SQL_CREATE_ENTRIES_USER =
            "CREATE TABLE " + DatabaseMaster.Users.TABLE_NAME + " (" +
                    DatabaseMaster.Users._ID + " INTEGER PRIMARY KEY," +
                    DatabaseMaster.Users.USERNAME + " TEXT," +
                    DatabaseMaster.Users.USERTYPE + " TEXT," +
                    DatabaseMaster.Users.PASSWORD + " TEXT)";

    private static final String SQL_CREATE_ENTRIES_MOVIE =
            "CREATE TABLE " + DatabaseMaster.Movie.TABLE_NAME + " (" +
                    DatabaseMaster.Movie._ID + " INTEGER PRIMARY KEY," +
                    DatabaseMaster.Movie.MOVIENAME + " TEXT," +
                    DatabaseMaster.Movie.MOVIEYEAR + " INTEGER)";

    private static final String SQL_CREATE_ENTRIES_COMMENT =
            "CREATE TABLE " + DatabaseMaster.Comments.TABLE_NAME + " (" +
                    DatabaseMaster.Comments._ID + " INTEGER PRIMARY KEY," +
                    DatabaseMaster.Comments.MOVIENAME + " TEXT," +
                    DatabaseMaster.Comments.COMMENTS + " TEXT," +
                    DatabaseMaster.Comments.RATING + " INTEGER)";

    private static final String SQL_DELETE_ENTRIES_USER =
            "DROP TABLE IF EXISTS " + DatabaseMaster.Users.TABLE_NAME;

    private static final String SQL_DELETE_ENTRIES_MOVIE =
            "DROP TABLE IF EXISTS " + DatabaseMaster.Movie.TABLE_NAME;

    private static final String SQL_DELETE_ENTRIES_COMMENT =
            "DROP TABLE IF EXISTS " + DatabaseMaster.Comments.TABLE_NAME;

    public long registerUser (String username, String usertype, String password) {
        // Gets the data repository in write mode
        SQLiteDatabase db = getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(DatabaseMaster.Users.USERNAME, username);
        values.put(DatabaseMaster.Users.USERTYPE, usertype);
        values.put(DatabaseMaster.Users.PASSWORD, password);

        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(DatabaseMaster.Users.TABLE_NAME, null, values);
        return newRowId;
    }

    public long addMovie (String moviename, int movieyear) {
        // Gets the data repository in write mode
        SQLiteDatabase db = getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(DatabaseMaster.Movie.MOVIENAME, moviename);
        values.put(DatabaseMaster.Movie.MOVIEYEAR, movieyear);

        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(DatabaseMaster.Movie.TABLE_NAME, null, values);
        return newRowId;
    }

    public long insertComments (String moviename, int rating, String comment) {
        // Gets the data repository in write mode
        SQLiteDatabase db = getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(DatabaseMaster.Comments.MOVIENAME, moviename);
        values.put(DatabaseMaster.Comments.RATING, rating);
        values.put(DatabaseMaster.Comments.COMMENTS, comment);

        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(DatabaseMaster.Comments.TABLE_NAME, null, values);
        return newRowId;
    }

    public int loginUser(String username, String password) {
        SQLiteDatabase db = getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                BaseColumns._ID,
                DatabaseMaster.Users.USERNAME,
                DatabaseMaster.Users.USERTYPE,
                DatabaseMaster.Users.PASSWORD
        };

        // Filter results WHERE "title" = 'My Title'
        String selection = DatabaseMaster.Users.USERNAME + " LIKE ?";
        String[] selectionArgs = { username };

        // How you want the results sorted in the resulting Cursor
        String sortOrder =
                DatabaseMaster.Users.USERNAME + " ASC";

        Cursor cursor = db.query(
                DatabaseMaster.Users.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                sortOrder               // The sort order
        );

        String user = "", pass = "";
        //List userInfo = new ArrayList<>();
        while(cursor.moveToNext()) {
            user = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseMaster.Users.USERNAME));
            pass = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseMaster.Users.PASSWORD));
            //userInfo.add(user);//0
            //userInfo.add(pass);//1
        }
        cursor.close();

        if (username.equals("") || password.equals("")) {
            return 0;
        }
        if (user.equals(username) && pass.equals(password)) {
            if (user.equals("admin")) {
                return 1;
            } else {
                return 2;
            }
        } else {
            return 3;
        }
    }

    public List viewMovies() {
        SQLiteDatabase db = getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                BaseColumns._ID,
                DatabaseMaster.Movie.MOVIENAME,
                DatabaseMaster.Movie.MOVIEYEAR
        };

        // How you want the results sorted in the resulting Cursor
        String sortOrder =
                DatabaseMaster.Movie.MOVIENAME + " ASC";

        Cursor cursor = db.query(
                DatabaseMaster.Movie.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                null,              // The columns for the WHERE clause
                null   ,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                sortOrder               // The sort order
        );

        List movienames = new ArrayList<>();
        while(cursor.moveToNext()) {
            String movie = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseMaster.Movie.MOVIENAME)) + " - " +
                    cursor.getString(cursor.getColumnIndexOrThrow(DatabaseMaster.Movie.MOVIEYEAR));
            movienames.add(movie);
        }
        cursor.close();

        return movienames;
    }

    public List viewComments(String movieName) {
        SQLiteDatabase db = getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                BaseColumns._ID,
                DatabaseMaster.Comments.MOVIENAME,
                DatabaseMaster.Comments.RATING,
                DatabaseMaster.Comments.COMMENTS
        };

        // Filter results WHERE "title" = 'My Title'
        String selection = DatabaseMaster.Comments.MOVIENAME + " LIKE ?";
        String[] selectionArgs = { movieName };

        // How you want the results sorted in the resulting Cursor
        String sortOrder =
                DatabaseMaster.Comments.MOVIENAME + " ASC";

        Cursor cursor = db.query(
                DatabaseMaster.Comments.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                sortOrder               // The sort order
        );

        List comments = new ArrayList<>();
        while(cursor.moveToNext()) {
            String comment = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseMaster.Comments.COMMENTS));
            comments.add(comment);
        }
        cursor.close();

        return comments;
    }

    public float AverageRating(String movieName) {
        SQLiteDatabase db = getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                BaseColumns._ID,
                DatabaseMaster.Comments.MOVIENAME,
                DatabaseMaster.Comments.RATING,
                DatabaseMaster.Comments.COMMENTS
        };

        // Filter results WHERE "title" = 'My Title'
        String selection = DatabaseMaster.Comments.MOVIENAME + " LIKE ?";
        String[] selectionArgs = { movieName };

        // How you want the results sorted in the resulting Cursor
        String sortOrder =
                DatabaseMaster.Comments.MOVIENAME + " ASC";

        Cursor cursor = db.query(
                DatabaseMaster.Comments.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                sortOrder               // The sort order
        );

        float Total = 0;
        int count = 0;
        while(cursor.moveToNext()) {
            Integer rate = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseMaster.Comments.RATING));
            Total += rate;
            count++;
        }
        cursor.close();

        return (Total / count);
    }
}