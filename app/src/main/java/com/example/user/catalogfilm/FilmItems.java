package com.example.user.catalogfilm;

import org.json.JSONObject;

import java.text.DecimalFormat;

public class FilmItems {
    private int id;
    private String judul;
    private String overview;
    private String tanggalRilis;
    private String gambar;
    private String skorFilm;
    public  FilmItems(JSONObject object){
        try {
            int id = object.getInt("id");
            String judul = object.getString("title");
            String overview = object.getString("overview");
            String tanggalRilis =  object.getString("release_date");
            String gambar = object.getString("poster_path");
            String skorFilm = object.getString("vote_average");
            this.id = id;
            this.judul = judul;
            this.overview = overview;
            this.tanggalRilis = tanggalRilis;
            this.gambar = gambar;
            this.skorFilm = skorFilm;
        }catch (Exception e){
            e.printStackTrace();
        }
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
}
