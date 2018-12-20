package com.example.user.catalogfilm.Fragment;


import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.user.catalogfilm.Activity.DetailFilmActivity;
import com.example.user.catalogfilm.Adapter.FavoriteAdapter;
import com.example.user.catalogfilm.Adapter.ListFilmAdapter;
import com.example.user.catalogfilm.Model.FilmItems;
import com.example.user.catalogfilm.R;
import com.example.user.catalogfilm.Utilities.ItemClickSupport;
import com.example.user.catalogfilm.db.FavoriteHelper;

import java.util.ArrayList;
import java.util.LinkedList;

import butterknife.BindView;
import butterknife.ButterKnife;


import static com.example.user.catalogfilm.db.DatabaseContract.CONTENT_URI;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoritesFragment extends Fragment  {
    @BindView(R.id.rv_category)
    RecyclerView mRecyclerView;
    @BindView(R.id.progressbar)
    ProgressBar progressBar;

//    private LinkedList<FilmItems> list;
    private Cursor list;
    private FavoriteAdapter adapter;
    private FavoriteHelper favoriteHelper;

    public FavoritesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.film_list, container, false);
        ButterKnife.bind(this,rootView);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setHasFixedSize(true);

        favoriteHelper = new FavoriteHelper(getContext());

        favoriteHelper.open();



        adapter = new FavoriteAdapter(getContext());
        adapter.setListNotes(list);
        mRecyclerView.setAdapter(adapter);

        new LoadNoteAsync().execute();

        return  rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private class LoadNoteAsync extends AsyncTask<Void, Void, Cursor> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);

        }

        @Override
        protected Cursor doInBackground(Void... voids) {
            return getActivity().getContentResolver().query(CONTENT_URI,null,null,null,null);
        }

        @Override
        protected void onPostExecute(Cursor films) {
            super.onPostExecute(films);
            progressBar.setVisibility(View.GONE);

            list = films;
            adapter.setListNotes(list);
            adapter.notifyDataSetChanged();

            ItemClickSupport.addTo(mRecyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                @Override
                public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                    final FilmItems filmItems = getItem(position);
                    showSelected(filmItems);
                }
            });

            if (list.getCount() == 0){
                showSnackbarMessage("Tidak ada data saat ini");
            }
        }
    }

    private FilmItems getItem(int position){
        if (!list.moveToPosition(position)) {
            throw new IllegalStateException("Position invalid");
        }
        return new FilmItems(list);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (favoriteHelper != null){
            favoriteHelper.close();
        }
    }

    private void showSnackbarMessage(String message) {
        Snackbar.make(mRecyclerView, message, Snackbar.LENGTH_SHORT).show();
    }

    private void showSelected(FilmItems filmItems){

        Intent moveWithObjectIntent = new Intent(getContext(), DetailFilmActivity.class);
        moveWithObjectIntent.putExtra(DetailFilmActivity.EXTRA_FILM, filmItems);
        startActivityForResult(moveWithObjectIntent, DetailFilmActivity.REQUEST_ADD);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == DetailFilmActivity.RESULT_UPDATE) {
            new LoadNoteAsync().execute();
            showSnackbarMessage("Telah Di upadte");
        }else if(resultCode == DetailFilmActivity.RESULT_DELETE){
            new LoadNoteAsync().execute();
            showSnackbarMessage("Telah Di Delete");
        }
    }

}
