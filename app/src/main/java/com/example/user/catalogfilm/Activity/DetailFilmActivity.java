package com.example.user.catalogfilm.Activity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.user.catalogfilm.Model.FilmItems;
import com.example.user.catalogfilm.R;
import com.example.user.catalogfilm.db.FavoriteHelper;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.user.catalogfilm.db.DatabaseContract.CONTENT_URI;
import static com.example.user.catalogfilm.db.DatabaseContract.FavoritesColumns.DESCRIPTION;
import static com.example.user.catalogfilm.db.DatabaseContract.FavoritesColumns.GAMBAR;
import static com.example.user.catalogfilm.db.DatabaseContract.FavoritesColumns.JUDUL;
import static com.example.user.catalogfilm.db.DatabaseContract.FavoritesColumns.SKORFILM;
import static com.example.user.catalogfilm.db.DatabaseContract.FavoritesColumns.TANGGAL;


public class DetailFilmActivity extends AppCompatActivity {


    public static String EXTRA_FILM= "extra_person";
    public static String EXTRA_POSITION = "extra_position";


    @BindView(R.id.txt_judul) TextView textJudul;
    @BindView(R.id.txt_skor) TextView textSkor;
    @BindView(R.id.txt_overview) TextView textPreview;
    @BindView(R.id.txt_tanggal) TextView textTanggal;
    @BindView(R.id.header_cover_image) ImageView imageSampul;
    @BindView(R.id.btn_favorite) ImageView btnFavorite;
    public static int REQUEST_ADD = 100;
    public static int RESULT_ADD = 101;
    public static int REQUEST_UPDATE = 200;
    public static int RESULT_UPDATE = 201;
    public static int RESULT_DELETE = 301;
    private FilmItems filmItems;
    String coba;

    private FavoriteHelper favoriteHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_film);
        ButterKnife.bind(this);



        favoriteHelper = new FavoriteHelper(this);
        favoriteHelper.open();

        Uri uri = getIntent().getData();

        if (uri != null) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            if (cursor != null){
                if(cursor.moveToFirst()) filmItems = new FilmItems(cursor);
                cursor.close();
            }
        }

         filmItems = getIntent().getParcelableExtra(EXTRA_FILM);


         coba = favoriteHelper.getdataById(filmItems.getJudul());

        if(!coba.equals("JUDUL")) {
            DrawableCompat.setTint(btnFavorite.getDrawable(), ContextCompat.getColor(getBaseContext(), R.color.Gold));
        }else{
            DrawableCompat.setTint(btnFavorite.getDrawable(), ContextCompat.getColor(getBaseContext(), R.color.White));
        }




        textJudul.setText(filmItems.getJudul());
        textSkor.setText(filmItems.getSkorFilm());
        textTanggal.setText(String.format("%s%s", getString(R.string.release_date), filmItems.getTanggalRilis()));
        textPreview.setText(filmItems.getOverview());

        btnFavorite.setOnClickListener(myListener);


        Glide.with(this)
                .load("https://image.tmdb.org/t/p/w342" + filmItems.getGambar())
                .into(imageSampul);



    }

    View.OnClickListener myListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //Hapus favorites
            if(!coba.equals("JUDUL")) {


                favoriteHelper.delete(coba);
//                getContentResolver().delete(CONTENT_URI,coba,null);
                DrawableCompat.setTint(btnFavorite.getDrawable(), ContextCompat.getColor(getBaseContext(), R.color.White));
                coba = "JUDUL";
                setResult(RESULT_DELETE);
                finish();
            }else{

                ContentValues values = new ContentValues();
                values.put(JUDUL,filmItems.getJudul());
                values.put(SKORFILM,filmItems.getSkorFilm());
                values.put(TANGGAL,filmItems.getTanggalRilis());
                values.put(DESCRIPTION,filmItems.getOverview());
                values.put(GAMBAR,filmItems.getGambar());
                Log.v("Tag","Heeee");
//                favoriteHelper.delete(coba);

                getContentResolver().insert(CONTENT_URI,values);
                setResult(RESULT_ADD);
                finish();
                DrawableCompat.setTint(btnFavorite.getDrawable(), ContextCompat.getColor(getBaseContext(), R.color.Gold));
            }
        }
    };


}
