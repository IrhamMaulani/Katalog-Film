package com.example.user.catalogfilm;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;


public class DetailFilmActivity extends AppCompatActivity {


    public static String EXTRA_FILM= "extra_person";
    private Context context;


    TextView textJudul;
    TextView textSkor;
    TextView textPreview;
    TextView textTanggal;
    ImageView imageSampul;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_film);

        context=DetailFilmActivity.this;

        textJudul = findViewById(R.id.txt_judul);
        textSkor = findViewById(R.id.txt_skor);
        textPreview = findViewById(R.id.txt_overview);
        textTanggal = findViewById(R.id.txt_tanggal);
        imageSampul = findViewById(R.id.header_cover_image);

        FilmItems filmItems = getIntent().getParcelableExtra(EXTRA_FILM);

        textJudul.setText(filmItems.getJudul());
        textSkor.setText(filmItems.getSkorFilm());
        textTanggal.setText("Release Date : " + filmItems.getTanggalRilis());
        textPreview.setText(filmItems.getOverview());


        Glide.with(context).load(filmItems.getGambar()).into(imageSampul);


    }
}
