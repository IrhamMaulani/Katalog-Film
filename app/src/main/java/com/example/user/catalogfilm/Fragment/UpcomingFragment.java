package com.example.user.catalogfilm.Fragment;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.user.catalogfilm.Activity.DetailFilmActivity;
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
public class UpcomingFragment extends Fragment  {

    @BindView(R.id.rv_category)
    RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    public static FragmentActivity ma;
    private List<FilmItems> kontakList = new ArrayList<>();
    private ListFilmAdapter viewAdapter;
    private ApiInterface mApiInterface;
    private String statusFilm ;

    public UpcomingFragment() {
        // Required empty public constructor
    }

    @SuppressLint("ValidFragment")
    public UpcomingFragment(String statusFilm){
        this.statusFilm = statusFilm;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.film_list, container, false);

        ButterKnife.bind(this,rootView);

//        mRecyclerView = (RecyclerView)rootView.findViewById(R.id.rv_category);
        mRecyclerView.setHasFixedSize(true);
        viewAdapter = new ListFilmAdapter(getContext(), kontakList);
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(viewAdapter);



        mApiInterface = ApiClient.getClient().create(ApiInterface.class);
        ma=getActivity();

        getData();



        return rootView;
    }


    public void getData(){
        Call<GetFilmItems> call = mApiInterface.getUpcoming(statusFilm);
        call.enqueue(new Callback<GetFilmItems>() {
            @Override
            public void onResponse(Call<GetFilmItems> call, Response<GetFilmItems>
                    response) {
                final List<FilmItems> kontakList = response.body().getListDataFilm();
                Log.d("Retrofit Get", "Jumlah data : " +
                        String.valueOf(kontakList.size()));
                mAdapter = new ListFilmAdapter(kontakList);
                mRecyclerView.setAdapter(mAdapter);

                ItemClickSupport.addTo(mRecyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        showSelected(kontakList.get(position));
                    }
                });
            }

            @Override
            public void onFailure(Call<GetFilmItems> call, Throwable t) {
                Log.e("Retrofit Get", t.toString());
            }

        });


    }

    private void showSelected(FilmItems filmItems){
//        Toast.makeText(getContext(), "Kamu memilih "+filmItems.getJudul(), Toast.LENGTH_SHORT).show();
        filmItems.setJudul(filmItems.getJudul());
        filmItems.setOverview(filmItems.getOverview());
        filmItems.setTanggalRilis(filmItems.getTanggalRilis());
        filmItems.setSkorFilm(filmItems.getSkorFilm());
        filmItems.setGambar(filmItems.getGambar());
        Intent moveWithObjectIntent = new Intent(getContext(), DetailFilmActivity.class);
        moveWithObjectIntent.putExtra(DetailFilmActivity.EXTRA_FILM, filmItems);
        getContext().startActivity(moveWithObjectIntent);




    }


}
