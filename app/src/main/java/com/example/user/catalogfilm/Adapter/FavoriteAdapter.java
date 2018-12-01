package com.example.user.catalogfilm.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.catalogfilm.Model.FilmItems;
import com.example.user.catalogfilm.R;

import java.util.ArrayList;
import java.util.LinkedList;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.NoteViewholder>{
    private LinkedList<FilmItems> listNotes;
    private ArrayList<FilmItems> mData = new ArrayList<>();
    private Context context;

    public FavoriteAdapter(Context context) {
        this.context = context;
    }

    public LinkedList<FilmItems> getListNotes() {
        return listNotes;
    }

    public void setListNotes(LinkedList<FilmItems> listNotes) {
        this.listNotes = listNotes;
    }

    public void addItem(ArrayList<FilmItems> mData) {
        this.mData = mData;
        notifyDataSetChanged();
    }

    @Override
    public NoteViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.film_items, parent, false);
        return new NoteViewholder(view);
    }

    @Override
    public void onBindViewHolder(NoteViewholder holder, int position) {
        holder.textViewJudulFilm.setText(getListNotes().get(position).getJudul());
        holder.textViewDeskripsi.setText(getListNotes().get(position).getOverview());
        holder.textViewWaktuRelease.setText(getListNotes().get(position).getTanggalRilis());
        holder.textViewSkor.setText(getListNotes().get(position).getSkorFilm());
    }

    @Override
    public int getItemCount() {
        return getListNotes().size();
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