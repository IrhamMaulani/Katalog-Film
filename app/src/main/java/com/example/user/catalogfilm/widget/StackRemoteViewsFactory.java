package com.example.user.catalogfilm.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.os.Binder;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.example.user.catalogfilm.Model.FilmItems;
import com.example.user.catalogfilm.R;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static com.example.user.catalogfilm.db.DatabaseContract.CONTENT_URI;

public class StackRemoteViewsFactory  implements
        RemoteViewsService.RemoteViewsFactory{

    private List<Bitmap> mWidgetItems = new ArrayList<>();
    private Context mContext;
    private int mAppWidgetId;
    String url = "https://image.tmdb.org/t/p/w342";

    private Cursor list;

    public StackRemoteViewsFactory(Context context, Intent intent) {
        mContext = context;
        mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);
    }


    @Override
    public void onCreate() {
        list = mContext.getContentResolver().query(
                CONTENT_URI,
                null,
                null,
                null,
                null
        );

    }

    @Override
    public void onDataSetChanged() {
        final long identityToken = Binder.clearCallingIdentity();

        mContext.getContentResolver().query(
                CONTENT_URI,
                null,
                null,
                null,
                null
        );

        Binder.restoreCallingIdentity(identityToken);
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return list.getCount();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        FilmItems item = getItem(position);
        RemoteViews remoteViews = new RemoteViews(mContext.getPackageName(), R.layout.widget_item);

        Bitmap bmp = null;
        try {


            bmp = Glide.with(mContext)
                    .load(url + item.getGambar())
                    .asBitmap()
                    .error(new ColorDrawable(mContext.getResources().getColor(R.color.colorGray)))
                    .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).get();

        }catch (InterruptedException | ExecutionException e){
            Log.d("Widget Load Error","error");
        }

        remoteViews.setImageViewBitmap(R.id.imageView,bmp);

        Bundle extras = new Bundle();
        extras.putInt(FavoriteMovieWidget.EXTRA_ITEM, position);
        Intent fillInIntent = new Intent();
        fillInIntent.putExtras(extras);

        remoteViews.setOnClickFillInIntent(R.id.imageView, fillInIntent);
        return remoteViews;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    private FilmItems getItem(int position) {
        if (!list.moveToPosition(position)) {
            throw new IllegalStateException("Position invalid!");
        }

        return new FilmItems(list);
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
