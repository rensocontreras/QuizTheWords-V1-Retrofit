package com.contreras.myquizapplication.Entity.Request;

public class UsuarioRequest {

    private int txt_codigo_usuario;
    private String txt_nombres;
    private String txt_apellidos;
    private String txt_correo;
    private String txt_password;
    private String txt_imagen;
    private String txt_sexo;

    public int getTxt_codigo_usuario() {
        return txt_codigo_usuario;
    }

    public void setTxt_codigo_usuario(int txt_codigo_usuario) {
        this.txt_codigo_usuario = txt_codigo_usuario;
    }

    public String getTxt_nombres() {
        return txt_nombres;
    }

    public void setTxt_nombres(String txt_nombres) {
        this.txt_nombres = txt_nombres;
    }

    public String getTxt_apellidos() {
        return txt_apellidos;
    }

    public void setTxt_apellidos(String txt_apellidos) {
        this.txt_apellidos = txt_apellidos;
    }

    public String getTxt_correo() {
        return txt_correo;
    }

    public void setTxt_correo(String txt_correo) {
        this.txt_correo = txt_correo;
    }

    public String getTxt_password() {
        return txt_password;
    }

    public void setTxt_password(String txt_password) {
        this.txt_password = txt_password;
    }

    public String getTxt_imagen() {
        return txt_imagen;
    }

    public void setTxt_imagen(String txt_imagen) {
        this.txt_imagen = txt_imagen;
    }

    public String getTxt_sexo() {
        return txt_sexo;
    }

    public void setTxt_sexo(String txt_sexo) {
        this.txt_sexo = txt_sexo;
    }
}
