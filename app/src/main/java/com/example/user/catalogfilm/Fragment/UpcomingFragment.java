package com.example.user.catalogfilm.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.app.LoaderManager;
import android.content.Loader;

import com.example.user.catalogfilm.Adapter.FilmAdapter;
import com.example.user.catalogfilm.Adapter.ListFilmAdapter;
import com.example.user.catalogfilm.FilmItems;
import com.example.user.catalogfilm.MyAsyncTaskLoader;
import com.example.user.catalogfilm.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class UpcomingFragment extends Fragment implements LoaderManager.LoaderCallbacks<ArrayList<FilmItems>>  {

    RecyclerView rvCategory;
    static final String EXTRAS_FILM = "EXTRAS_FILM";
    FilmAdapter adapter;

    public UpcomingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.film_list, container, false);

        rvCategory = (RecyclerView)rootView.findViewById(R.id.rv_category);
        rvCategory.setHasFixedSize(true);

        showRecyclerList();

        return rootView;
    }

    private void showRecyclerList(){
        rvCategory.setLayoutManager(new LinearLayoutManager(getContext()));
        ListFilmAdapter listFilmAdapter = new ListFilmAdapter(this.getActivity());
        rvCategory.setAdapter(listFilmAdapter);
    }

    @Override
    public Loader<ArrayList<FilmItems>> onCreateLoader(int id, Bundle args) {

        String kumpulanFilm = "";
        if (args != null){
            kumpulanFilm = args.getString(EXTRAS_FILM);
        }

        return new MyAsyncTaskLoader(getContext(),kumpulanFilm);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<FilmItems>> loader, ArrayList<FilmItems> data) {

        adapter.setData(data);
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<FilmItems>> loader) {
        adapter.setData(null);
    }

}
