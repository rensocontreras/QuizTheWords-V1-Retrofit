package com.contreras.myquizapplication.Model;

import android.util.Log;

import com.contreras.myquizapplication.Entity.Compite;
import com.contreras.myquizapplication.Entity.Nivel;
import com.contreras.myquizapplication.Entity.Request.UsuarioRequest;
import com.contreras.myquizapplication.Entity.Usuario;
import com.contreras.myquizapplication.Interfaces.IJugar;
import com.contreras.myquizapplication.Presenter.JugarPresenter;
import com.contreras.myquizapplication.Rest.HelperWs;
import com.contreras.myquizapplication.Rest.MethodWs;
import com.contreras.myquizapplication.View.JugarActivity;

import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JugarModel implements IJugar.IJugarModel {

    JugarPresenter presenter;

    public JugarModel(JugarPresenter presenter, JugarActivity view) {
        this.presenter = presenter;
    }



    @Override
    public void consultaListaNiveles(int codigoUsuario) {
        obtenerNiveles(codigoUsuario);
    }


    public void obtenerNiveles(final int codigoUsuario){

        MethodWs methodWs = HelperWs.getConfiguration().create(MethodWs.class);  //Il create me pide un interface que sarebbe MethodWs.class
        Call<ArrayList<Nivel>> response = methodWs.obtenerNiveles();

        response.enqueue(new Callback<ArrayList<Nivel>>() {
            @Override
            public void onResponse(Call<ArrayList<Nivel>> call, Response<ArrayList<Nivel>> response) {
                ArrayList<Nivel> list_niveles = response.body();
                if(list_niveles!=null)
                    obtenerCompeticiones(codigoUsuario, list_niveles);
            }

            @Override
            public void onFailure(Call<ArrayList<Nivel>> call, Throwable t) {
                Log.d("jugarNivel",t.getMessage());
            }
        });
    }

    private void obtenerCompeticiones(int codigoUsuario, final ArrayList<Nivel> list_niveles) {

        UsuarioRequest usuarioRequest = new UsuarioRequest();
        usuarioRequest.setTxt_codigo_usuario(codigoUsuario);
        int txt_codigo_usuario = codigoUsuario;

        MethodWs methodWs = HelperWs.getConfiguration().create(MethodWs.class);  //Il create me pide un interface que sarebbe MethodWs.class
        Call<ArrayList<Compite>> response = methodWs.obtenerCompeticion(usuarioRequest);

        response.enqueue(new Callback<ArrayList<Compite>>() {
            @Override
            public void onResponse(Call<ArrayList<Compite>> call, Response<ArrayList<Compite>> response) {
                ArrayList<Compite> list_competiciones = response.body();
                if(list_competiciones!=null)
                    presenter.obtenerListaNiveles(list_niveles,list_competiciones);
            }

            @Override
            public void onFailure(Call<ArrayList<Compite>> call, Throwable t) {
                Log.d("jugarCompite",t.getMessage());
            }
        });
    }


}