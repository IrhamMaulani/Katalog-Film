package com.example.user.catalogfilm.db;

import android.provider.BaseColumns;

public class DatabaseContract {

    static String TABLE_FAVORITES = "favorites";

    static final class FavoritesColumns implements BaseColumns {

        //favorites title
        static String JUDUL = "judul";

        static String SKORFILM = "skor_film";

        static String TANGGAL = "tanggal";

        //favorites description
        static String DESCRIPTION = "description";

        static String GAMBAR = "gambar";

    }
}
