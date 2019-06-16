package com.contreras.myquizapplication.Interfaces;

import com.contreras.myquizapplication.Entity.Usuario;

public class IRegistrar {

    public interface IRegistrarView{
        void enviarUsuario(Usuario usuario);
        void mostrarResultadoCorrecto(String mensaje);
        void mostrarResultadoIncorrecto(String mensaje);
        void mostrarDialogo();
        void ocultarDialog();
    }

    public interface IRegistrarPresenter{
        void solicitarUsuario(Usuario usuario);
        void obtenerResultado(String mensaje,int tipo);
    }

    public interface IRegistrarModel{
        void insertarUsuario(Usuario usuario);
    }

}
