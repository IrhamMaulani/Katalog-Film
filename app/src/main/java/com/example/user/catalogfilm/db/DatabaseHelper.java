package com.example.user.catalogfilm.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static String DATABASE_NAME = "dbfilmfavorite";
    private static final int DATABASE_VERSION = 1;
    private static final String SQL_CREATE_TABLE_FAVORITES = String.format("CREATE TABLE %s"
                    + " (%s INTEGER PRIMARY KEY autoincrement," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL)",
            DatabaseContract.TABLE_FAVORITES,
            DatabaseContract.FavoritesColumns._ID,
            DatabaseContract.FavoritesColumns.JUDUL,
            DatabaseContract.FavoritesColumns.SKORFILM,
            DatabaseContract.FavoritesColumns.TANGGAL,
            DatabaseContract.FavoritesColumns.DESCRIPTION,
            DatabaseContract.FavoritesColumns.GAMBAR
    );
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_FAVORITES);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.TABLE_FAVORITES);
        onCreate(db);
    }
}
