package com.mck.icndbclient.provider;

import android.content.ContentResolver;
import android.net.Uri;

/**
 * Created by mike on 4/25/2015.
 */
public class JokeProviderContract {
    public static String AUTHORITY = "com.mck.icndbclient";
    public static Uri URI = Uri.parse("content://" + AUTHORITY);

    public static String DIR_CONTENT_TYPE =
            ContentResolver.CURSOR_DIR_BASE_TYPE + " vnd.mck.joke";
    public static String ITEM_CONTENT_TYPE =
            ContentResolver.CURSOR_ITEM_BASE_TYPE + " vnd.mck.joke";

    public static class JokesTable {
        public static String TABLE_NAME = "jokes";

        public static String PATH = TABLE_NAME;
        public static Uri URI = Uri.withAppendedPath(JokeProviderContract.URI, PATH);
        public static Uri getItemUri(int id){
            return Uri.withAppendedPath(URI, "/" + String.valueOf(id));
        }

        public static String _ID = "_ID";
        public static String type = "type";

        public static String joke_id = "joke_id";
        public static String joke = "joke";
        public static String categories = "categ";

        public static String[] DEFAULT_PROJECTION = {_ID, type, joke_id, joke, categories};
        public static String DEFAULT_ORDER = _ID + " ASC";

        public static String SELECT_BY_ID =  _ID + " = ? ";
    }
}
