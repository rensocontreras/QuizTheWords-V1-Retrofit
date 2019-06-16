package com.contreras.myquizapplication.Model;

import android.util.Log;

import com.contreras.myquizapplication.Entity.Request.UsuarioRequest;
import com.contreras.myquizapplication.Entity.Usuario;
import com.contreras.myquizapplication.Interfaces.ILogin;
import com.contreras.myquizapplication.Presenter.LoginPresenter;
import com.contreras.myquizapplication.View.LoginActivity;
import com.contreras.myquizapplication.Rest.HelperWs;
import com.contreras.myquizapplication.Rest.MethodWs;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginModel implements ILogin.ILoginModel {

    LoginPresenter presenter;
    LoginActivity view;

    public LoginModel(LoginPresenter presenter, LoginActivity view) {
        this.presenter = presenter;
        this.view = view;
    }

    @Override
    public void validarUsuario(String correo, String password) {

        UsuarioRequest usuarioRequest = new UsuarioRequest();
        usuarioRequest.setTxt_correo(correo);
        usuarioRequest.setTxt_password(password);

        MethodWs methodWs = HelperWs.getConfiguration().create(MethodWs.class);  //Il create me pide un interface que sarebbe MethodWs.class
        Call<Usuario> response = methodWs.validarUsuario(usuarioRequest);


        response.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                Usuario usuario = response.body();
                if(usuario!=null)
                    presenter.obtenerValidacion(usuario.getCodigoUsuario(), usuario.getNombres());
                else
                    presenter.obtenerValidacion(-1,"");
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                Log.i("contreras, errore ",t.getMessage());
                presenter.obtenerValidacion(-1,"");
            }
        });

    }
}
