package com.example.user.catalogfilm.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.user.catalogfilm.Activity.DetailFilmActivity;
import com.example.user.catalogfilm.FilmItems;
import com.example.user.catalogfilm.R;


import java.util.ArrayList;

public class FilmAdapter extends BaseAdapter{

    private ArrayList<FilmItems> mData = new ArrayList<>();
    private LayoutInflater mInflater;
    private Context context;

    public FilmAdapter(Context context) {
        this.context = context;
        mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setData(ArrayList<FilmItems> items){
        mData = items;
        notifyDataSetChanged();
    }
    public void addItem(final FilmItems item) {
        mData.add(item);
        notifyDataSetChanged();
    }
    public void clearData(){
        mData.clear();
    }
    @Override
    public int getItemViewType(int position) {
        return 0;
    }
    @Override
    public int getViewTypeCount() {
        return 1;
    }
    @Override
    public int getCount() {
        if (mData == null) return 0;
        return mData.size();
    }
    @Override
    public FilmItems getItem(int position) {
        return mData.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.film_items, null);
            holder.textViewJudulFilm= (TextView)convertView.findViewById(R.id.judul_film);
            holder.textViewDeskripsi = (TextView)convertView.findViewById(R.id.deskripsi);
            holder.textViewWaktuRelease = (TextView)convertView.findViewById(R.id.waktu_release);
            holder.textViewSkor = (TextView)convertView.findViewById(R.id.skor);
            holder.gambarSampul = (ImageView) convertView.findViewById(R.id.img_view_film_item);
            holder.listViewFilmItem = (LinearLayout)convertView.findViewById(R.id.linear_layout_film_item);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.textViewJudulFilm.setText(mData.get(position).getJudul());
        holder.textViewDeskripsi.setText(mData.get(position).getOverview());
        holder.textViewWaktuRelease.setText(mData.get(position).getTanggalRilis());
        holder.textViewSkor.setText("Rating : " + mData.get(position).getSkorFilm());

        String url = "https://image.tmdb.org/t/p/w185";
        Glide.with(context).load(url+mData.get(position).getGambar()).into(holder.gambarSampul);


        holder.listViewFilmItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FilmItems filmItems = new FilmItems();

                filmItems.setJudul(mData.get(position).getJudul());
                filmItems.setOverview(mData.get(position).getOverview());
                filmItems.setTanggalRilis(mData.get(position).getTanggalRilis());

                String posterPath = mData.get(position).getGambar();
                String posterUrl =  "https://image.tmdb.org/t/p/w342" + posterPath;
                filmItems.setGambar(posterUrl);

                filmItems.setSkorFilm(mData.get(position).getSkorFilm());

                Intent moveWithObjectIntent = new Intent(view.getContext(), DetailFilmActivity.class);
                moveWithObjectIntent.putExtra(DetailFilmActivity.EXTRA_FILM, filmItems);
                view.getContext().startActivity(moveWithObjectIntent);

            }
        });

        return convertView;
    }
    private static class ViewHolder {
        TextView textViewJudulFilm;
        TextView textViewDeskripsi;
        TextView textViewWaktuRelease;
        TextView textViewSkor;
        ImageView gambarSampul;
        LinearLayout listViewFilmItem;
    }
}
