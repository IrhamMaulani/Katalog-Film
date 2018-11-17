package com.example.user.catalogfilm.Activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.user.catalogfilm.Model.FilmItems;
import com.example.user.catalogfilm.R;

import butterknife.BindView;
import butterknife.ButterKnife;


public class DetailFilmActivity extends AppCompatActivity {


    public static String EXTRA_FILM= "extra_person";


    @BindView(R.id.txt_judul) TextView textJudul;
    @BindView(R.id.txt_skor) TextView textSkor;
    @BindView(R.id.txt_overview) TextView textPreview;
    @BindView(R.id.txt_tanggal) TextView textTanggal;
    @BindView(R.id.header_cover_image) ImageView imageSampul;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_film);
        ButterKnife.bind(this);

        context=DetailFilmActivity.this;



        FilmItems filmItems = getIntent().getParcelableExtra(EXTRA_FILM);

        textJudul.setText(filmItems.getJudul());
        textSkor.setText(filmItems.getSkorFilm());
        textTanggal.setText("Release Date : " + filmItems.getTanggalRilis());
        textPreview.setText(filmItems.getOverview());


        Glide.with(context).load(filmItems.getGambar()).into(imageSampul);


    }
}
