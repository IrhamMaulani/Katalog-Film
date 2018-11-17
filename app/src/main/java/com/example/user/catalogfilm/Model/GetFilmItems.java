package com.example.user.catalogfilm.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetFilmItems {

    @SerializedName("results")
    List<FilmItems> results ;

    public List<FilmItems> getListDataFilm() {
        return results ;
    }

    public void setListDataFilm(List<FilmItems> listDataFilm) {
        this.results  = listDataFilm;
    }
}
