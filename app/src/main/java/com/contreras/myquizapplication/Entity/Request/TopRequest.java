package com.contreras.myquizapplication.Entity.Request;

public class TopRequest {
    private int txt_numero_nivel;
    private int txt_codigo_usuario;
    private int txt_my_timer;
    private int txt_preguntas_resueltas;

    public int getTxt_numero_nivel() {
        return txt_numero_nivel;
    }

    public void setTxt_numero_nivel(int txt_numero_nivel) {
        this.txt_numero_nivel = txt_numero_nivel;
    }

    public int getTxt_codigo_usuario() {
        return txt_codigo_usuario;
    }

    public void setTxt_codigo_usuario(int txt_codigo_usuario) {
        this.txt_codigo_usuario = txt_codigo_usuario;
    }

    public int getTxt_my_timer() {
        return txt_my_timer;
    }

    public void setTxt_my_timer(int txt_my_timer) {
        this.txt_my_timer = txt_my_timer;
    }

    public int getTxt_preguntas_resueltas() {
        return txt_preguntas_resueltas;
    }

    public void setTxt_preguntas_resueltas(int txt_preguntas_resueltas) {
        this.txt_preguntas_resueltas = txt_preguntas_resueltas;
    }
}
