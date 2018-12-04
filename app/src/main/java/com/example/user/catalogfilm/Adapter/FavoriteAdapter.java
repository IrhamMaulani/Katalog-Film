package com.example.user.catalogfilm.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.user.catalogfilm.Model.FilmItems;
import com.example.user.catalogfilm.R;

import java.util.ArrayList;
import java.util.LinkedList;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.NoteViewholder>{
    private Cursor listNotes;
    public Context context;
    String url = "https://image.tmdb.org/t/p/w185";

    public FavoriteAdapter(Context context) {
        this.context = context;
    }



    public void setListNotes(Cursor  listNotes) {
        this.listNotes = listNotes;
    }





    @Override
    public NoteViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.film_items, parent, false);
        return new NoteViewholder(view);
    }

    @Override
    public void onBindViewHolder(NoteViewholder holder, int position) {
        final FilmItems filmItems = getItem(position);
        holder.textViewJudulFilm.setText(filmItems.getJudul());
        holder.textViewDeskripsi.setText(filmItems.getOverview());
        holder.textViewWaktuRelease.setText(filmItems.getTanggalRilis());
        holder.textViewSkor.setText(filmItems.getSkorFilm());

        Context context = holder.gambarSampul.getContext();

        Glide.with(context)
                .load(url + filmItems.getGambar())
                .into(holder.gambarSampul);
    }



    @Override
    public int getItemCount() {
        if (listNotes == null) return 0;
        return listNotes.getCount();
    }

    private FilmItems getItem(int position) {
        if (!listNotes.moveToPosition(position)){
            throw new IllegalStateException("Position invalid");
        }
        return new FilmItems(listNotes);
    }




    public class NoteViewholder extends RecyclerView.ViewHolder{

        TextView textViewJudulFilm;
        TextView textViewDeskripsi;
        TextView textViewWaktuRelease;
        TextView textViewSkor;
        ImageView gambarSampul;

        public NoteViewholder(View itemView) {
            super(itemView);
            textViewJudulFilm = (TextView)itemView.findViewById(R.id.judul_film);
            textViewDeskripsi = (TextView)itemView.findViewById(R.id.deskripsi);
            textViewWaktuRelease = (TextView)itemView.findViewById(R.id.waktu_release);
            textViewSkor = (TextView)itemView.findViewById(R.id.skor);
            gambarSampul = (ImageView)itemView.findViewById(R.id.img_view_film_item);
        }
    }
}