package com.example.user.catalogfilm.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class FilmItems implements Parcelable {
    @SerializedName("id")
    private int id;

    @SerializedName("title")
    private String judul;

    @SerializedName("overview")
    private String overview;

    @SerializedName("release_date")
    private String tanggalRilis;

    @SerializedName("poster_path")
    private String gambar;

    @SerializedName("vote_average")
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

    protected FilmItems(Parcel in) {
        this.id = in.readInt();
        this.judul = in.readString();
        this.overview = in.readString();
        this.tanggalRilis = in.readString();
        this.gambar = in.readString();
        this.skorFilm = in.readString();
    }

    public static final Parcelable.Creator<FilmItems> CREATOR = new Parcelable.Creator<FilmItems>() {
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
