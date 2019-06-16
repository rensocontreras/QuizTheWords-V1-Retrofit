package com.contreras.myquizapplication.Model;

import android.util.Log;

import com.contreras.myquizapplication.Entity.Request.UsuarioRequest;
import com.contreras.myquizapplication.Entity.Usuario;
import com.contreras.myquizapplication.Interfaces.IRegistrar;
import com.contreras.myquizapplication.Presenter.RegistrarPresenter;
import com.contreras.myquizapplication.View.RegistrarActivity;

import com.contreras.myquizapplication.Rest.HelperWs;
import com.contreras.myquizapplication.Rest.MethodWs;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrarModel implements IRegistrar.IRegistrarModel {

    RegistrarPresenter presenter;
    RegistrarActivity view;

    public RegistrarModel(RegistrarPresenter presenter, RegistrarActivity view) {
        this.presenter = presenter;
        this.view = view;
    }

    @Override
    public void insertarUsuario(Usuario usuario) {
        UsuarioRequest usuarioRequest = new UsuarioRequest();
        usuarioRequest.setTxt_nombres(usuario.getNombres());
        usuarioRequest.setTxt_apellidos(usuario.getApellidos());
        usuarioRequest.setTxt_correo(usuario.getCorreo());
        usuarioRequest.setTxt_password(usuario.getPassword());
        usuarioRequest.setTxt_imagen(usuario.getImagen());
        usuarioRequest.setTxt_sexo(usuario.getSexo());


        MethodWs methodWs = HelperWs.getConfiguration().create(MethodWs.class);  //Il create me pide un interface que sarebbe MethodWs.class
        Call<Boolean> response = methodWs.registrarUsuario(usuarioRequest);

        response.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                Boolean existeUsuario = response.body();
                if(existeUsuario)
                    presenter.obtenerResultado("Usuario creado",1);
                else
                    presenter.obtenerResultado("Error, vuelva a intentarlo",2);
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Log.i("contreras, errore ",t.getMessage());
                presenter.obtenerResultado("Error, vuelva a intentarlo",2);
            }
        });
    }

}
