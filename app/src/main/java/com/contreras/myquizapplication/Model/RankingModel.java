package com.contreras.myquizapplication.Model;

import com.contreras.myquizapplication.Entity.ItemEntity.ItemCompetidorTop;
import com.contreras.myquizapplication.Interfaces.IRanking;
import com.contreras.myquizapplication.Presenter.RankingPresenter;
import com.contreras.myquizapplication.Rest.HelperWs;
import com.contreras.myquizapplication.Rest.MethodWs;
import com.contreras.myquizapplication.View.RankingActivity;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RankingModel implements IRanking.IRankingmodel {

    RankingPresenter presenter;
    RankingActivity view;

    public RankingModel(RankingPresenter presenter, RankingActivity view){
        this.presenter = presenter;
        this.view = view;
    }


    @Override
    public void consultaListaCompetidoresTop() {
        obtenerItemCompetidoresTop();
    }


    public void obtenerItemCompetidoresTop(){
        MethodWs methodWs = HelperWs.getConfiguration().create(MethodWs.class);  //Il create me pide un interface que sarebbe MethodWs.class
        Call<ArrayList<ItemCompetidorTop>> response = methodWs.obtenerItemCompetidoresTop();

        response.enqueue(new Callback<ArrayList<ItemCompetidorTop>>() {
            @Override
            public void onResponse(Call<ArrayList<ItemCompetidorTop>> call, Response<ArrayList<ItemCompetidorTop>> response) {
                ArrayList<ItemCompetidorTop> list_competidores = response.body();
                presenter.obtenerListaCompetidoresTop(list_competidores);
            }

            @Override
            public void onFailure(Call<ArrayList<ItemCompetidorTop>> call, Throwable t) {

            }
        });
    }


}
