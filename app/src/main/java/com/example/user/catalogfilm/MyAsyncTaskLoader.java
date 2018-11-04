package com.example.user.catalogfilm;

import android.content.AsyncTaskLoader;

import android.content.Context;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.SyncHttpClient;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MyAsyncTaskLoader extends AsyncTaskLoader<ArrayList<FilmItems>> {
    private ArrayList<FilmItems> mData;
    private boolean mHasResult = false;

    private String films ;

    MyAsyncTaskLoader(final Context context, String films) {
        super(context);

        onContentChanged();
        this.films = films;
    }

    //Ketika data loading,
    @Override
    protected void onStartLoading() {
        if (takeContentChanged())
            forceLoad();
        else if (mHasResult)
            deliverResult(mData);
    }

    @Override
    public void deliverResult(final ArrayList<FilmItems> data) {
        mData = data;
        mHasResult = true;
        super.deliverResult(data);
    }

    @Override
    protected void onReset() {
        super.onReset();
        onStopLoading();
        if (mHasResult) {
            mData = null;
            mHasResult = false;
        }
    }

    private static final String API_KEY = "7cd1d8fb02b9a93c41f413b0e5143765";


    // Format search kota url JAKARTA = 1642911 ,BANDUNG = 1650357, SEMARANG = 1627896
    // http://api.openweathermap.org/data/2.5/group?id=1642911,1650357,1627896&units=metric&appid=API_KEY

    @Override
    public ArrayList<FilmItems> loadInBackground() {
        SyncHttpClient client = new SyncHttpClient();

        films = "a";

        final ArrayList<FilmItems> filmItemses = new ArrayList<>();
        String url = "https://api.themoviedb.org/3/search/movie?api_key=" +
                API_KEY + "&language=en-US&query=" + films;

        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();
                //Menggunakan synchronous karena pada dasarnya thread yang digunakan sudah asynchronous dan method
                //loadInBackground mengembalikan nilai balikan
                setUseSynchronousMode(true);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("results");

                    for (int i = 0; i < list.length(); i++) {
                        JSONObject film = list.getJSONObject(i);
                        FilmItems filmItems = new FilmItems(film);
                        filmItemses.add(filmItems);
                    }
                } catch (Exception e) {
                    //Jika terjadi error pada saat parsing maka akan masuk ke catch()
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                //Jika response gagal maka , do nothing
            }
        });

        return filmItemses;
    }

}
