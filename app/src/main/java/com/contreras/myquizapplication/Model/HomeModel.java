package com.contreras.myquizapplication.Model;

import android.content.SharedPreferences;

import com.contreras.myquizapplication.Interfaces.IHome;
import com.contreras.myquizapplication.Presenter.HomePresenter;
import com.contreras.myquizapplication.Util.Constantes;
import com.contreras.myquizapplication.View.HomeActivity;


public class HomeModel implements IHome.IHomeModel {

    HomePresenter presenter;
    HomeActivity view;

    public HomeModel(HomePresenter presenter, HomeActivity view) {
        this.presenter = presenter;
        this.view = view;
        //SharedPreferences pref = view.getSharedPreferences(Constantes.PREFERENCIA_USUARIO,0);
        //String nombre = pref.getString("nombre_usuario","");
    }

    @Override
    public void executeLogout() {
        presenter.obtenerRespuesta();
    }

    @Override
    public void buscaNombreUsuario(String nombre) {

        //TODO Agregar en el sharepreference anche il sesso, cosi faccio un if per passare tutta la stringa "Bienvenido/a Renso"
        presenter.recibeNameUser(nombre);
    }

}
