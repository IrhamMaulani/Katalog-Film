package com.example.contentprovider;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;



import static android.provider.BaseColumns._ID;
import static com.example.contentprovider.DatabaseContract.getColumnInt;
import static com.example.contentprovider.DatabaseContract.getColumnString;
import static com.example.contentprovider.DatabaseContract.getColumnInt;
import static com.example.contentprovider.DatabaseContract.getColumnString;

public class FilmItems implements Parcelable {

    private int id;


    private String judul;


    private String overview;


    private String tanggalRilis;


    private String gambar;


    private String skorFilm;


    public FilmItems() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getTanggalRilis() {
        return tanggalRilis;
    }

    public void setTanggalRilis(String tanggalRilis) {
        this.tanggalRilis = tanggalRilis;
    }

    public String getGambar() {
        return  gambar;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }

    public String getSkorFilm() {
        return skorFilm;
    }

    public void setSkorFilm(String skorFilm) {
        this.skorFilm = skorFilm;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.judul);
        dest.writeString(this.overview);
        dest.writeString(this.tanggalRilis);
        dest.writeString(this.gambar);
        dest.writeString(this.skorFilm);
    }

    public FilmItems(Cursor cursor){
        this.id = getColumnInt(cursor, _ID);
        this.judul = getColumnString(cursor, DatabaseContract.FavoritesColumns.JUDUL);
        this.overview = getColumnString(cursor, DatabaseContract.FavoritesColumns.DESCRIPTION);
        this.tanggalRilis = getColumnString(cursor, DatabaseContract.FavoritesColumns.TANGGAL);
        this.gambar = getColumnString(cursor, DatabaseContract.FavoritesColumns.GAMBAR);
        this.skorFilm = getColumnString(cursor, DatabaseContract.FavoritesColumns.SKORFILM);
    }

    protected FilmItems(Parcel in) {
        this.id = in.readInt();
        this.judul = in.readString();
        this.overview = in.readString();
        this.tanggalRilis = in.readString();
        this.gambar = in.readString();
        this.skorFilm = in.readString();
    }

    public static final Creator<FilmItems> CREATOR = new Creator<FilmItems>() {
        @Override
        public FilmItems createFromParcel(Parcel source) {
            return new FilmItems(source);
        }

        @Override
        public FilmItems[] newArray(int size) {
            return new FilmItems[size];
        }
    };
}
