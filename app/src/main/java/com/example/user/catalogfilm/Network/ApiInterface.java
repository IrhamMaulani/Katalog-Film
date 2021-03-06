package com.example.user.catalogfilm.Network;


import com.example.user.catalogfilm.BuildConfig;
import com.example.user.catalogfilm.Model.GetFilmItems;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {



        String API_KEY = BuildConfig.ApiKey;

//
    @GET("3/movie/{status}?api_key=" + API_KEY + "&language=en-US")
    Call<GetFilmItems> getUpcoming(@Path("status") String status);

    @GET("3/search/movie?api_key=" + API_KEY + "&language=en-US&")
    Call<GetFilmItems> getSearchFilm(@Query("query") String film);



}
