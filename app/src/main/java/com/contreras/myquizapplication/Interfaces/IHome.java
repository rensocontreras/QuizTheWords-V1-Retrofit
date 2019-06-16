package com.contreras.myquizapplication.Interfaces;

public class IHome {

    public interface IHomeView {
        void salir();
        void mostrarLogout();
        void obtenerNombreUsuario(String nombre);
        void mostrarNameUser(String name);
    }

    public interface IHomePresenter{
        void solicitarLogout();
        void obtenerRespuesta();
        void solicitarNameUser(String nombre);
        void recibeNameUser(String name);
    }

    public interface IHomeModel{
        void executeLogout();
        void buscaNombreUsuario(String nombre);
    }

}
