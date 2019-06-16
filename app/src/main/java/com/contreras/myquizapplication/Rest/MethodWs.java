package com.contreras.myquizapplication.Rest;

import com.contreras.myquizapplication.Entity.Compite;
import com.contreras.myquizapplication.Entity.ItemEntity.ItemCompetidorTop;
import com.contreras.myquizapplication.Entity.Nivel;
import com.contreras.myquizapplication.Entity.Pregunta;
import com.contreras.myquizapplication.Entity.Request.CompiteRequest;
import com.contreras.myquizapplication.Entity.Request.NivelRequest;
import com.contreras.myquizapplication.Entity.Request.TopRequest;
import com.contreras.myquizapplication.Entity.Request.UsuarioRequest;
import com.contreras.myquizapplication.Entity.Top;
import com.contreras.myquizapplication.Entity.Usuario;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface MethodWs {

    @POST("registrarUsuario.php")
    @Headers("Content-Type:application/json")
    Call<Boolean> registrarUsuario(@Body UsuarioRequest usuarioRequest);

    @POST("validarUsuario.php")
    @Headers("Content-Type:application/json")
    Call<Usuario> validarUsuario(@Body UsuarioRequest usuarioRequest);

    @POST("obtenerNiveles.php")
    @Headers("Content-Type:application/json")
    Call<ArrayList<Nivel>> obtenerNiveles();

    @POST("obtenerCompeticion.php")
    @Headers("Content-Type:application/json")
    Call<ArrayList<Compite>> obtenerCompeticion(@Body UsuarioRequest usuarioRequest);


    @POST("obtenerPreguntas.php")
    @Headers("Content-Type:application/json")
    Call<ArrayList<Pregunta>> obtenerPreguntas(@Body NivelRequest nivelRequest);

    @POST("existenciaNivelSiguiente.php")
    @Headers("Content-Type:application/json")
    Call<ArrayList<Nivel>> existenciaNivelSiguiente(@Body NivelRequest nivelRequest);

    @POST("actualizarCompeticionSiguiente.php")
    @Headers("Content-Type:application/json")
    Call<Void> actualizarCompeticionSiguiente(@Body CompiteRequest compiteRequest);



    @POST("actualizarTimer.php")
    @Headers("Content-Type:application/json")
    Call<Void> actualizarTimer(@Body CompiteRequest compiteRequest);

    @POST("actualizarCompeticionActual.php")
    @Headers("Content-Type:application/json")
    Call<Void> actualizarCompeticionActual(@Body CompiteRequest compiteRequest);

    @POST("insertarCompetidor.php")
    @Headers("Content-Type:application/json")
    Call<Void> insertarCompetidor(@Body TopRequest topRequest);

    @POST("obtenerCompetidorTop.php")
    @Headers("Content-Type:application/json")
    Call<Top> obtenerCompetidorTop(@Body NivelRequest nivelRequest);

    @POST("actualizarCompetidorTop.php")
    @Headers("Content-Type:application/json")
    Call<Void> actualizarCompetidorTop(@Body TopRequest topRequest);

    @POST("obtenerItemCompetidoresTop.php")
    @Headers("Content-Type:application/json")
    Call<ArrayList<ItemCompetidorTop>> obtenerItemCompetidoresTop();
}
