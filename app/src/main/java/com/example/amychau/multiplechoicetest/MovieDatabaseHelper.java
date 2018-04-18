package com.example.amychau.multiplechoicetest;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MovieDatabaseHelper extends SQLiteOpenHelper {

    protected static final String ACTIVITY_NAME = "MovieDatabaseHelper";

    private static String DATABASE_NAME = "Movies.db";
    private static int VERSION_NUM = 2;
    final static String KEY_ID = "id";
    final static String KEY_TITLE = "title";
    final static String KEY_ACTORS = "actors";
    final static String KEY_DESCRIPTION = "description";
    final static String KEY_RATING = "rating";
    final static String KEY_GENRE = "genre";
    final static String KEY_RUNTIME = "runtime";
    final static String KEY_POSTER = "poster";

    final static String TABLE_NAME = "Movie_History";

    public MovieDatabaseHelper(Context ctx) {
        super(ctx, DATABASE_NAME, null, VERSION_NUM);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(ACTIVITY_NAME, "in onCreate");

        db.execSQL("CREATE TABLE "
                +TABLE_NAME
                +" ("
                +KEY_ID
                +" INTEGER PRIMARY KEY AUTOINCREMENT, "
                +KEY_TITLE
                +" TEXT, "
                +KEY_ACTORS
                +" TEXT, "
                +KEY_DESCRIPTION
                +" TEXT, "
                +KEY_RATING
                +" TEXT,"
                +KEY_GENRE
                +" TEXT,"
                +KEY_RUNTIME
                +" TEXT,"
                +KEY_POSTER
                +" BLOB);"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {  // newVersion > oldVersion

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME ); //delete any existing data
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {  // newVersion < oldVersion

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME ); //delete any existing data
        onCreate(db);
    }
}
