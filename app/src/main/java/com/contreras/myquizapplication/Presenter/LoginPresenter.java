package com.contreras.myquizapplication.Presenter;

import com.contreras.myquizapplication.Interfaces.ILogin;
import com.contreras.myquizapplication.Model.LoginModel;
import com.contreras.myquizapplication.View.LoginActivity;

public class LoginPresenter implements ILogin.ILoginPresenter {

    private LoginActivity view;
    private LoginModel model;

    public LoginPresenter(LoginActivity view) {
        this.view = view;
        model = new LoginModel(this,view);
    }

    @Override
    public void solicitarValidacion(String correo, String password) {
        model.validarUsuario(correo,password);
    }

    @Override
    public void obtenerValidacion(int codigoUsuario, String nombreUsuario) {
        if(codigoUsuario!=-1)
            view.mostrarResultadoCorrecto(codigoUsuario,nombreUsuario);
        else
            view.mostrarResultadoIncorrecto();
    }
}
