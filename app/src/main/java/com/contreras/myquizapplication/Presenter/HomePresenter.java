package com.contreras.myquizapplication.Presenter;

import android.content.SharedPreferences;

import com.contreras.myquizapplication.Interfaces.IHome;
import com.contreras.myquizapplication.Model.HomeModel;
import com.contreras.myquizapplication.View.HomeActivity;

public class HomePresenter implements IHome.IHomePresenter {

    HomeActivity view;
    HomeModel model;

    public HomePresenter(HomeActivity view) {
        this.view = view;
        model = new HomeModel(this, view);
    }

    @Override
    public void solicitarLogout() {
        model.executeLogout();
    }

    @Override
    public void obtenerRespuesta() {
        view.mostrarLogout();
    }

    @Override
    public void solicitarNameUser(String nombre) {
        model.buscaNombreUsuario(nombre);
    }

    @Override
    public void recibeNameUser(String name) {
        view.mostrarNameUser(name);
    }
}
