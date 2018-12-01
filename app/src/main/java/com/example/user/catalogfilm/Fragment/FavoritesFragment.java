package com.example.user.catalogfilm.Fragment;


import android.content.Intent;
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

import static com.example.user.catalogfilm.Activity.DetailFilmActivity.REQUEST_UPDATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoritesFragment extends Fragment  {
    @BindView(R.id.rv_category)
    RecyclerView mRecyclerView;
    @BindView(R.id.progressbar)
    ProgressBar progressBar;

    private LinkedList<FilmItems> list;
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

        list = new LinkedList<>();

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

    private class LoadNoteAsync extends AsyncTask<Void, Void, ArrayList<FilmItems>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);

            if (list.size() > 0){
                list.clear();
            }
        }

        @Override
        protected ArrayList<FilmItems> doInBackground(Void... voids) {
            return favoriteHelper.query();
        }

        @Override
        protected void onPostExecute(ArrayList<FilmItems> filmItems) {
            super.onPostExecute(filmItems);
            progressBar.setVisibility(View.GONE);

            list.addAll(filmItems);
            adapter.setListNotes(list);
            adapter.notifyDataSetChanged();

            ItemClickSupport.addTo(mRecyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                @Override
                public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                    showSelected(list.get(position));
                }
            });

            if (list.size() == 0){
                showSnackbarMessage("Tidak ada data saat ini");
            }
        }
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
        filmItems.setJudul(filmItems.getJudul());
        filmItems.setOverview(filmItems.getOverview());
        filmItems.setTanggalRilis(filmItems.getTanggalRilis());
        filmItems.setSkorFilm(filmItems.getSkorFilm());
        filmItems.setGambar(filmItems.getGambar());
        filmItems.setId(filmItems.getId());
        Intent moveWithObjectIntent = new Intent(getContext(), DetailFilmActivity.class);
        moveWithObjectIntent.putExtra(DetailFilmActivity.EXTRA_FILM, filmItems);
        startActivity(moveWithObjectIntent);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == DetailFilmActivity.RESULT_DELETE) {
            int position = data.getIntExtra(DetailFilmActivity.EXTRA_POSITION, 0);
            list.remove(position);
            adapter.setListNotes(list);
            adapter.notifyDataSetChanged();
            showSnackbarMessage("Satu item berhasil dihapus");
        }
    }

}
