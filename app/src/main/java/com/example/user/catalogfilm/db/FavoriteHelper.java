package com.example.user.catalogfilm.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.user.catalogfilm.Model.FilmItems;

import java.util.ArrayList;

import static android.os.Build.ID;
import static android.provider.BaseColumns._ID;
import static com.example.user.catalogfilm.db.DatabaseContract.FavoritesColumns.DESCRIPTION;
import static com.example.user.catalogfilm.db.DatabaseContract.FavoritesColumns.GAMBAR;
import static com.example.user.catalogfilm.db.DatabaseContract.FavoritesColumns.JUDUL;
import static com.example.user.catalogfilm.db.DatabaseContract.FavoritesColumns.SKORFILM;
import static com.example.user.catalogfilm.db.DatabaseContract.FavoritesColumns.TANGGAL;
import static com.example.user.catalogfilm.db.DatabaseContract.TABLE_FAVORITES;

public class FavoriteHelper  {

    private static String DATABASE_TABLE = TABLE_FAVORITES;
    private Context context;
    private DatabaseHelper dataBaseHelper;

    private SQLiteDatabase database;

    public FavoriteHelper(Context context){
        this.context = context;
    }

    public FavoriteHelper open() throws SQLException {
        dataBaseHelper = new DatabaseHelper(context);
        database = dataBaseHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        dataBaseHelper.close();
    }

    public String getdataById(String nama){
        
       Cursor cursor =  database.query(DATABASE_TABLE,null,JUDUL+" LIKE ?",new String[]{nama},null,null,_ID + " ASC",null);
       String judul = "JUDUL";
        cursor.moveToFirst();
//        cursor = cursor.getColumnIndex(JUDUL);

        if (cursor.moveToFirst()) {
            judul = cursor.getString(cursor.getColumnIndex(JUDUL ));
        }
        cursor.close();
        return judul;
    }


    public ArrayList<FilmItems> query(){
        ArrayList<FilmItems> arrayList = new ArrayList<FilmItems>();
        Cursor cursor = database.query(DATABASE_TABLE,null,null,null,null,null,_ID +" DESC",null);
        cursor.moveToFirst();
        FilmItems filmItems;
        if (cursor.getCount()>0) {
            do {

                filmItems = new FilmItems();
                filmItems.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                filmItems.setJudul(cursor.getString(cursor.getColumnIndexOrThrow(JUDUL)));
                filmItems.setSkorFilm(cursor.getString(cursor.getColumnIndexOrThrow(SKORFILM)));
                filmItems.setTanggalRilis(cursor.getString(cursor.getColumnIndexOrThrow(TANGGAL)));
                filmItems.setOverview(cursor.getString(cursor.getColumnIndexOrThrow(DESCRIPTION)));
                filmItems.setGambar(cursor.getString(cursor.getColumnIndexOrThrow(GAMBAR)));
                arrayList.add(filmItems);
                cursor.moveToNext();

            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public long insert(FilmItems filmItems){
        ContentValues initialValues =  new ContentValues();
        initialValues.put(JUDUL, filmItems.getJudul());
        initialValues.put(SKORFILM, filmItems.getSkorFilm());
        initialValues.put(TANGGAL, filmItems.getTanggalRilis());
        initialValues.put(DESCRIPTION, filmItems.getOverview());
        initialValues.put(GAMBAR, filmItems.getGambar());
        return database.insert(DATABASE_TABLE, null, initialValues);
    }

    public int delete(String judul){
        return database.delete(TABLE_FAVORITES, JUDUL + " = '"+judul+"'", null);
    }

    public Cursor queryByJudulProvider(String nama){
        return database.query(DATABASE_TABLE,null
                ,_ID + " = ?"
                ,new String[]{JUDUL}
                ,null
                ,null
                ,null
                ,null);
    }
    public Cursor queryProvider(){
        return database.query(DATABASE_TABLE
                ,null
                ,null
                ,null
                ,null
                ,null
                ,_ID + " DESC");
    }
    public long insertProvider(ContentValues values){
        return database.insert(DATABASE_TABLE,null,values);
    }

    public int updateProvider(String id,ContentValues values){
        return database.update(DATABASE_TABLE,values,_ID +" = ?",new String[]{id} );
    }

    public int deleteProvider(String judul){
        return database.delete(DATABASE_TABLE,JUDUL + " = ?", new String[]{judul});
    }
}
