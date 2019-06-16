package com.contreras.myquizapplication.Interfaces;

public class ILogin {

    public interface ILoginView{
         void enviarInformacion(String correo, String password);
         void mostrarResultadoCorrecto(int codigoUsuario, String nombreUsuario);
         void mostrarResultadoIncorrecto();
    }

    public interface ILoginPresenter{
         void solicitarValidacion(String correo, String password);
         void obtenerValidacion(int codigoUsuario,String nombreUsuario);
    }

    public interface ILoginModel{
         void validarUsuario(String correo, String password);
    }

}
