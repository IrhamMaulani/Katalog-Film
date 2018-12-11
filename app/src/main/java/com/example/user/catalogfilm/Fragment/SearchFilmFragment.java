package com.example.user.catalogfilm.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.catalogfilm.Activity.DetailFilmActivity;
import com.example.user.catalogfilm.Activity.MainActivity;
import com.example.user.catalogfilm.Adapter.ListFilmAdapter;
import com.example.user.catalogfilm.Model.FilmItems;
import com.example.user.catalogfilm.Model.GetFilmItems;
import com.example.user.catalogfilm.Network.ApiClient;
import com.example.user.catalogfilm.Network.ApiInterface;
import com.example.user.catalogfilm.R;
import com.example.user.catalogfilm.Utilities.ItemClickSupport;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFilmFragment extends Fragment {

    @BindView(R.id.rv_category_search)
    RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    public static FragmentActivity ma;
    private ArrayList<FilmItems> filmList = new ArrayList<>();
    private ListFilmAdapter viewAdapter;
    private ApiInterface mApiInterface;
    private String films = "FILMS";
    private String film = "TEXT_PENCARIAN";
    @BindView(R.id.edit_film)
    EditText textPencarian ;
    @BindView(R.id.btn_search)
    Button buttonPencarian;
    @BindView(R.id.progressbar)
    ProgressBar progressBar;


    public SearchFilmFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_search, container, false);

        ButterKnife.bind(this,rootView);




        buttonPencarian.setOnClickListener(myListener);

        film = textPencarian.getText().toString();

        viewAdapter = new ListFilmAdapter(getContext(), filmList);

        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(viewAdapter);

        mApiInterface = ApiClient.getClient().create(ApiInterface.class);
        ma=getActivity();

        if(savedInstanceState != null){

            filmList = savedInstanceState.getParcelableArrayList(films);

            if(filmList.size() == 0){
                getData("");
            }
            generateFilm(filmList);
        }else {
            getData("");
        }

        return rootView;
    }

    public void getData(String film){
        if (TextUtils.isEmpty(film))return;
        progressBar.setVisibility(View.VISIBLE);

        Call<GetFilmItems> call = mApiInterface.getSearchFilm(film);
        call.enqueue(new Callback<GetFilmItems>() {
            @Override
            public void onResponse(Call<GetFilmItems> call, Response<GetFilmItems>
                    response) {
                if(progressBar.getVisibility() == View.VISIBLE){
                    progressBar.setVisibility(View.GONE);
                }
                if (response.isSuccessful()) {
                    filmList = (ArrayList<FilmItems>) response.body().getListDataFilm();
                    generateFilm(filmList);
                }else {
                    Toast.makeText(getActivity(), getString(R.string.error), Toast.LENGTH_LONG).show();
                }


            }

            @Override
            public void onFailure(Call<GetFilmItems> call, Throwable t) {
                Log.e("Retrofit Get", t.toString());
            }

        });
    }

    private void generateFilm(List<FilmItems> films) {
        viewAdapter = new ListFilmAdapter(getContext(), films);
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(viewAdapter);
        progressBar.setVisibility(View.GONE);

        ItemClickSupport.addTo(mRecyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                showSelected(filmList.get(position));
            }
        });
    }


    View.OnClickListener myListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String film = textPencarian.getText().toString();

            if (TextUtils.isEmpty(film))return;

            getData(film);
        }
    };

    private void showSelected(FilmItems filmItems){
        Intent moveWithObjectIntent = new Intent(getContext(), DetailFilmActivity.class);

        moveWithObjectIntent.putExtra(DetailFilmActivity.EXTRA_FILM, filmItems);
        getContext().startActivity(moveWithObjectIntent);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelableArrayList(films, filmList);
        super.onSaveInstanceState(outState);
    }

}
