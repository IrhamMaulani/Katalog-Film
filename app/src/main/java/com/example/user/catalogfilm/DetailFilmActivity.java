package com.example.user.catalogfilm;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

public class DetailFilmActivity extends AppCompatActivity {


    public static String EXTRA_TITLE = "extra_title";
    public static String EXTRA_OVERVIEW = "extra_overview";
    public static String EXTRA_POSTER_URL = "extra_poster_url";
    public static String EXTRA_SKOR = "extra_skor";
    public static String EXTRA_TANGGAl = "extra_vote";
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

        EXTRA_TITLE = getIntent().getStringExtra("EXTRA_TITLE");
        EXTRA_OVERVIEW = getIntent().getStringExtra("EXTRA_OVERVIEW");
        EXTRA_POSTER_URL = getIntent().getStringExtra("EXTRA_POSTER_URL");
        EXTRA_SKOR = getIntent().getStringExtra("EXTRA_SKOR");
        EXTRA_TANGGAl = getIntent().getStringExtra("EXTRA_TANGGAl");

        textJudul.setText(EXTRA_TITLE);
        textSkor.setText(EXTRA_SKOR);
        textTanggal.setText("Release Date : " + EXTRA_TANGGAl);
        textPreview.setText(EXTRA_OVERVIEW);

Log.i("isi dari image",EXTRA_POSTER_URL);
        Picasso.with(context).load(EXTRA_POSTER_URL).into(imageSampul);


    }
}
