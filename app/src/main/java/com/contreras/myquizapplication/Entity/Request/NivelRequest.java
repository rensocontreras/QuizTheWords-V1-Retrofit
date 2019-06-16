package com.contreras.myquizapplication.Entity.Request;

public class NivelRequest {

    private int txt_numero_nivel;
    private int txt_tot_preguntas;
    private int txt_limite_preguntas;

    public int getTxt_numero_nivel() {
        return txt_numero_nivel;
    }

    public void setTxt_numero_nivel(int txt_numero_nivel) {
        this.txt_numero_nivel = txt_numero_nivel;
    }

    public int getTxt_tot_preguntas() {
        return txt_tot_preguntas;
    }

    public void setTxt_tot_preguntas(int txt_tot_preguntas) {
        this.txt_tot_preguntas = txt_tot_preguntas;
    }

    public int getTxt_limite_preguntas() {
        return txt_limite_preguntas;
    }

    public void setTxt_limite_preguntas(int txt_limite_preguntas) {
        this.txt_limite_preguntas = txt_limite_preguntas;
    }
}
