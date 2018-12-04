package com.example.contentprovider;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

public class DatabaseContract {

    public static String TABLE_FAVORITES = "favorites";

    public  static final class FavoritesColumns implements BaseColumns {

        //favorites title
        public static String JUDUL = "judul";

        public static String SKORFILM = "skor_film";

        public static String TANGGAL = "tanggal";

        //favorites description
        public static String DESCRIPTION = "description";

        public static String GAMBAR = "gambar";
    }

    public static final String AUTHORITY = "com.example.user.catalogfilm";
    public static final Uri CONTENT_URI = new Uri.Builder().scheme("content")
            .authority(AUTHORITY)
            .appendPath(TABLE_FAVORITES)
            .build();
    public static String getColumnString(Cursor cursor, String columnName) {
        return cursor.getString( cursor.getColumnIndex(columnName) );
    }
    public static int getColumnInt(Cursor cursor, String columnName) {
        return cursor.getInt( cursor.getColumnIndex(columnName) );
    }
    public static long getColumnLong(Cursor cursor, String columnName) {
        return cursor.getLong( cursor.getColumnIndex(columnName) );
    }
}
