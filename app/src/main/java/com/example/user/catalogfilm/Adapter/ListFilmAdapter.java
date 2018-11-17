package com.example.user.catalogfilm.Adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.user.catalogfilm.Model.FilmItems;
import com.example.user.catalogfilm.R;

import java.util.ArrayList;
import java.util.List;

public class ListFilmAdapter extends RecyclerView.Adapter<ListFilmAdapter.CategoryViewHolder>{
    private Context context;
    List<FilmItems> filmList;

    String url = "https://image.tmdb.org/t/p/w185";


    public ListFilmAdapter(List<FilmItems> filmList) {
        this.filmList = filmList;
    }

    public  ListFilmAdapter(Context context, List<FilmItems> filmList){
        this.context = context;
    }


    @Override
    public CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemRow = LayoutInflater.from(parent.getContext()).inflate(R.layout.film_items, parent, false);
        return new CategoryViewHolder(itemRow);
    }

    @Override
    public void onBindViewHolder(CategoryViewHolder holder, int position) {


        holder.textViewJudulFilm.setText(filmList.get(position).getJudul());
        holder.textViewDeskripsi.setText(filmList.get(position).getOverview());
        holder.textViewWaktuRelease.setText(filmList.get(position).getTanggalRilis());
        holder.textViewSkor.setText(filmList.get(position).getSkorFilm());


        Context context = holder.gambarSampul.getContext();

            Glide.with(context)
                    .load(url + filmList.get(position).getGambar())
                    .into(holder.gambarSampul);



    }

    @Override
    public int getItemCount() {
        if(filmList == null){
            return 0;
        }

        return filmList.size();
    }

    class CategoryViewHolder extends RecyclerView.ViewHolder{

        TextView textViewJudulFilm;
        TextView textViewDeskripsi;
        TextView textViewWaktuRelease;
        TextView textViewSkor;
        ImageView gambarSampul;

        CategoryViewHolder(View itemView) {
            super(itemView);
            textViewJudulFilm = (TextView)itemView.findViewById(R.id.judul_film);
            textViewDeskripsi = (TextView)itemView.findViewById(R.id.deskripsi);
            textViewWaktuRelease = (TextView)itemView.findViewById(R.id.waktu_release); ;
            textViewSkor = (TextView)itemView.findViewById(R.id.skor);
            gambarSampul = (ImageView)itemView.findViewById(R.id.img_view_film_item);
        }
    }
}