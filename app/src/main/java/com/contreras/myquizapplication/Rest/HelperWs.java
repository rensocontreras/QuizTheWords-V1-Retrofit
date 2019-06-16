package com.contreras.myquizapplication.Rest;


import com.contreras.myquizapplication.Util.Constantes;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HelperWs {

    public static Retrofit getConfiguration(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constantes.URL_BASE)
                .addConverterFactory(GsonConverterFactory.create())      //Converte un'oggetto JSON a una classe Java
                .build();

        return retrofit;
    }

}
