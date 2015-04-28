package com.mck.icndbclient.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.util.Log;

/**
 * Created by mike on 4/25/2015
 */
public class JokeProvider extends ContentProvider {

    private DBHelper dbHelper;

    // URI Matcher for joke table or for joke.
    private static final UriMatcher uriMatcher;
    private static final int JOKE = 1;
    private static final int JOKES = 2;
    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        uriMatcher.addURI(JokeProviderContract.AUTHORITY,
                JokeProviderContract.JokesTable.PATH, JOKES);
        uriMatcher.addURI(JokeProviderContract.AUTHORITY,
                JokeProviderContract.JokesTable.PATH + "/#", JOKE);
    }


    @Override
    public boolean onCreate() {
        dbHelper = new DBHelper(getContext(), DBSchema.DB_NAME, DBSchema.DB_VERSION);
        return true;
    }

    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)){
            case (JOKES):
                return JokeProviderContract.DIR_CONTENT_TYPE;
            case (JOKE):
                return JokeProviderContract.ITEM_CONTENT_TYPE;
        }
        return null;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
        builder.setTables(JokeProviderContract.JokesTable.TABLE_NAME);
        if (projection == null){
            projection = JokeProviderContract.JokesTable.DEFAULT_PROJECTION;
        }
        if (sortOrder == null){
            sortOrder = JokeProviderContract.JokesTable.DEFAULT_ORDER;
        }

        if (uriMatcher.match(uri) == JOKE){

        }

        switch (uriMatcher.match(uri)){
            case (JOKE):
                // Get a single joke by it's _id.
                selectionArgs = new String[1];
                selectionArgs[0] = uri.getLastPathSegment();
                selection = JokeProviderContract.JokesTable.SELECT_BY_ID;
                break;
            case (JOKES):
                // get all the jokes in the table.
                selectionArgs = null;
                selection = null;
                break;
        }
        Cursor result = builder.query(db, projection, selection, selectionArgs, null, null, sortOrder);
        result.setNotificationUri(getContext().getContentResolver(), uri);
        return result;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        if(uriMatcher.match(uri) == JOKES){
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            long id = db.insert(JokeProviderContract.JokesTable.TABLE_NAME, null, values);
            Uri result =  Uri.withAppendedPath(uri, String.valueOf(id));
            getContext().getContentResolver().notifyChange(result, null);
            return result;
        }
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }

    private static class  DBSchema{
        public static int     DB_VERSION = 1;
        public static String  DB_NAME = "jokes_db";

        public static final String CREATE_TABLE =
                "CREATE TABLE " + JokeProviderContract.JokesTable.TABLE_NAME + "("
                        + JokeProviderContract.JokesTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + JokeProviderContract.JokesTable.type + " TEXT, "
                        + JokeProviderContract.JokesTable.joke + " TEXT NOT NULL, "
                        + JokeProviderContract.JokesTable.joke_id + " INTEGER, "
                        + JokeProviderContract.JokesTable.categories + " TEXT " + ")";

        public static final String DROP_TABLE =
                "DROP TABLE IF EXISTS " + JokeProviderContract.JokesTable.TABLE_NAME;
    }

    private class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context, String name, int version) {
            super(context, name, null, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DBSchema.CREATE_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(DBSchema.DROP_TABLE);
        }
    }
}
