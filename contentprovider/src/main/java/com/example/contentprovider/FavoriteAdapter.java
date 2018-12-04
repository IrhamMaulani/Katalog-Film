package com.example.contentprovider;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import static com.example.contentprovider.DatabaseContract.FavoritesColumns.DESCRIPTION;
import static com.example.contentprovider.DatabaseContract.FavoritesColumns.JUDUL;
import static com.example.contentprovider.DatabaseContract.FavoritesColumns.TANGGAL;
import static com.example.contentprovider.DatabaseContract.getColumnString;

public class FavoriteAdapter extends CursorAdapter {

    public FavoriteAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
    }


    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        View view = LayoutInflater.from(context).inflate(R.layout.film_items, viewGroup, false);
        return view;
    }


    @Override
    public Cursor getCursor() {
        return super.getCursor();
    }


    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        if (cursor != null){
            TextView tvTitle = (TextView)view.findViewById(R.id.tv_item_title);
            TextView tvDate = (TextView)view.findViewById(R.id.tv_item_date);
            TextView tvDescription = (TextView)view.findViewById(R.id.tv_item_description);

            tvTitle.setText(getColumnString(cursor,JUDUL));
            tvDescription.setText(getColumnString(cursor,DESCRIPTION));
            tvDate.setText(getColumnString(cursor,TANGGAL));
        }
    }
}