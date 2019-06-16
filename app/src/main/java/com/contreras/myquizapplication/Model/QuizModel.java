package com.contreras.myquizapplication.Model;

import android.os.Handler;
import android.util.Log;

import com.contreras.myquizapplication.Entity.Compite;
import com.contreras.myquizapplication.Entity.Nivel;
import com.contreras.myquizapplication.Entity.Pregunta;
import com.contreras.myquizapplication.Entity.Request.CompiteRequest;
import com.contreras.myquizapplication.Entity.Request.NivelRequest;
import com.contreras.myquizapplication.Entity.Request.TopRequest;
import com.contreras.myquizapplication.Entity.Top;
import com.contreras.myquizapplication.Interfaces.IQuiz;
import com.contreras.myquizapplication.Presenter.QuizPresenter;

import com.contreras.myquizapplication.Rest.HelperWs;
import com.contreras.myquizapplication.Rest.MethodWs;
import com.contreras.myquizapplication.View.QuizActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuizModel implements IQuiz.IQuizModel {

    private QuizPresenter presenter;

    Nivel nivelActual;
    Compite competicionActual;
    int codigo;


    ArrayList<Pregunta> list_preguntas;
    int posicionCorriente;
    int preguntasResueltas;


    int numSecond=60;
    TimerTask task;

    public QuizModel(QuizPresenter presenter, QuizActivity view){
        this.presenter = presenter;
        posicionCorriente = 0;
        preguntasResueltas = 0;

        list_preguntas = new ArrayList<>();
    }


    @Override
    public void actualizarConfiguracionQuiz(Nivel nivelActual, Compite competicionActual, int codigo) {
        this.nivelActual = nivelActual;
        this.competicionActual = competicionActual;
        this.codigo = codigo;

        //Start timer
        myTimerStart();

    }

    @Override
    public void elaborarQuiz() {
        obtenerPreguntas(nivelActual.getNumero_nivel(), new MyCallbackPregunta() {
            @Override
            public void onCallback(ArrayList<Pregunta> list_preguntas) {
                Pregunta myPregunta = (Pregunta) list_preguntas.get(posicionCorriente);
                myPregunta.setNumero_pregunta(posicionCorriente+1); //TODO, nuevo update
                presenter.obtenerResultadoQuiz(mesclarOpciones(myPregunta));
            }
        });

    }

    public Pregunta mesclarOpciones(Pregunta myPregunta){
        ArrayList<String> list_opciones = new ArrayList<>();
        list_opciones.add(myPregunta.getOpcion1());
        list_opciones.add(myPregunta.getOpcion2());
        list_opciones.add(myPregunta.getOpcion3());
        list_opciones.add(myPregunta.getOpcion4());

        Collections.shuffle(list_opciones);
        myPregunta.setOpcion1(list_opciones.get(0));
        myPregunta.setOpcion2(list_opciones.get(1));
        myPregunta.setOpcion3(list_opciones.get(2));
        myPregunta.setOpcion4(list_opciones.get(3));

        return myPregunta;
    }

    @Override
    public void analisaRespuesta(String opcionSeleccionada) {
        if(opcionSeleccionada.equals(list_preguntas.get(posicionCorriente).getRespuesta())){
            //Actualizar el numero de Preguntas del objeto nivel actual
            preguntasResueltas++;
            presenter.obtenerRespuesta(1);
        }else{
            presenter.obtenerRespuesta(2);
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if((posicionCorriente+1)<list_preguntas.size()){
                    //Actualizo position dell'arrayPreguntas y seteo il Quiz
                    posicionCorriente++;
                    Pregunta myPregunta = (Pregunta) list_preguntas.get(posicionCorriente);
                    myPregunta.setNumero_pregunta(posicionCorriente+1);//TODO, nuevo update
                    presenter.obtenerResultadoQuiz(mesclarOpciones(myPregunta));
                }else {
                    controlFinalRespuesta();
                }
            }
        },600);
    }




    public void controlFinalRespuesta(){
        if (preguntasResueltas >= nivelActual.getLimite_preguntas()) {
            //Toast.makeText(this, "Nivel completado", Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    presenter.obtenerCalificacion(1);
                }
            }, 700);


            existenciaNivelSiguiente(nivelActual.getNumero_nivel() + 1,new MyCallbackExiste() {
                @Override
                public void onCallback(boolean existe) {
                    if(existe) {
                        //TODO, ARREGLAR NIVEL SIGUIENTE
                        actualizarCompeticionSiguiente(competicionActual.getCodigo_usuario(), nivelActual.getNumero_nivel() + 1, 1);//Nivel siguiente unlock
                    } else {
                        presenter.obtenerRespuesta(3);
                    }
                }
            });

        } else {
            //Toast.makeText(this, "Intentalo otra vez!", Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    presenter.obtenerCalificacion(2);
                }
            }, 700);
        }

        Log.i("AQUIRC","aqui");
        if (preguntasResueltas >= competicionActual.getPreguntas_resueltas()) {
            competicionActual.setPreguntas_resueltas(preguntasResueltas);
            if (competicionActual.getMy_timer() >= (60 - numSecond)) {
                competicionActual.setMy_timer(60 - numSecond);
                actualizarTimer(competicionActual.getCodigo_usuario(), nivelActual.getNumero_nivel(), competicionActual.getMy_timer());

            }
        }

        actualizarTop();

        actualizarCompeticionActual(competicionActual.getCodigo_usuario(), nivelActual.getNumero_nivel(), competicionActual.getPreguntas_resueltas());

        anularTimer();
    }



    public void controlFinalRespuestaTimeOut(){
        if (preguntasResueltas >= nivelActual.getLimite_preguntas()) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    presenter.obtenerCalificacion(3);

                }
            }, 700);




            existenciaNivelSiguiente(nivelActual.getNumero_nivel() + 1,new MyCallbackExiste() {
                @Override
                public void onCallback(boolean existe) {
                    if(existe) {
                        //TODO, ARREGLAR NIVEL SIGUIENTE
                        actualizarCompeticionSiguiente(competicionActual.getCodigo_usuario(), nivelActual.getNumero_nivel() + 1, 1);//Nivel siguiente unlock
                    }
                }
            });

        } else {
            try {
                Thread.sleep(700);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
                    presenter.obtenerCalificacion(4);

        }

        if (preguntasResueltas >= competicionActual.getPreguntas_resueltas()) {
            competicionActual.setPreguntas_resueltas(preguntasResueltas);
            if (competicionActual.getMy_timer() >= (60 - numSecond)) {
                competicionActual.setMy_timer(60 - numSecond);
                Log.i("COMPITETIME1",competicionActual.getMy_timer() +"");
                actualizarTimer(competicionActual.getCodigo_usuario(), nivelActual.getNumero_nivel(), competicionActual.getMy_timer());
            }
        }

        actualizarTop();

        actualizarCompeticionActual(competicionActual.getCodigo_usuario(), nivelActual.getNumero_nivel(), competicionActual.getPreguntas_resueltas());

        anularTimer();
    }


    private void myTimerStart() {
        final Timer timer = new Timer();
        task = new TimerTask() {
            public void run() {
                        presenter.obtenerNumeroSegundos(numSecond);
                        numSecond--;
                        if (numSecond == -1) {

                            try {
                                Thread.sleep(700);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                            numSecond=0;
                            controlFinalRespuestaTimeOut();


                            timer.cancel();
                        }
            }

        };
        timer.scheduleAtFixedRate(task, 0, 1000);
    }


    @Override
    public void anularTimer() {
        task.cancel();
    }



    private void actualizarTop() {

        final Top nuevoCompetidorTop = new Top();
        nuevoCompetidorTop.setNumero_nivel(nivelActual.getNumero_nivel());
        nuevoCompetidorTop.setCodigo_usuario(codigo);
        nuevoCompetidorTop.setMy_timer(competicionActual.getMy_timer());
        nuevoCompetidorTop.setPreguntas_resueltas(competicionActual.getPreguntas_resueltas());

        obtenerCompetidorTop(nivelActual.getNumero_nivel(), new MyCallbackExisteTop() {
            @Override
            public void onCallback(Top mycompetidorTop) {

                if(mycompetidorTop!=null){
                    if(mycompetidorTop.getPreguntas_resueltas()<competicionActual.getPreguntas_resueltas()) {
                            actualizarCompetidorTop(nuevoCompetidorTop);
                    }else if(mycompetidorTop.getPreguntas_resueltas() == competicionActual.getPreguntas_resueltas()){
                                if (mycompetidorTop.getMy_timer() >= competicionActual.getMy_timer()) {
                                    actualizarCompetidorTop(nuevoCompetidorTop);
                                }
                    }
                }else{
                    insertarCompetidor(nuevoCompetidorTop);
                }
            }
        });
    }



    /**************************************RETROFIT********************************************************/

    public interface MyCallbackPregunta {
        void onCallback(ArrayList<Pregunta> preguntas);
    }

    public void obtenerPreguntas(int nivelActual, final MyCallbackPregunta myCallbackPregunta){

        NivelRequest nivelRequest = new NivelRequest();
        nivelRequest.setTxt_numero_nivel(nivelActual);

        MethodWs methodWs = HelperWs.getConfiguration().create(MethodWs.class);  //Il create me pide un interface que sarebbe MethodWs.class
        Call<ArrayList<Pregunta>> response = methodWs.obtenerPreguntas(nivelRequest);

        response.enqueue(new Callback<ArrayList<Pregunta>>() {
            @Override
            public void onResponse(Call<ArrayList<Pregunta>> call, Response<ArrayList<Pregunta>> response) {
                list_preguntas = response.body();
                Collections.shuffle(list_preguntas);
                myCallbackPregunta.onCallback(list_preguntas);
            }

            @Override
            public void onFailure(Call<ArrayList<Pregunta>> call, Throwable t) {

            }
        });
    }



    public interface MyCallbackExiste {
        void onCallback(boolean existe);
    }

    public void existenciaNivelSiguiente(final int nivelActual, final MyCallbackExiste myCallbackExiste) {

        NivelRequest nivelRequest = new NivelRequest();
        nivelRequest.setTxt_numero_nivel(nivelActual);

        MethodWs methodWs = HelperWs.getConfiguration().create(MethodWs.class);  //Il create me pide un interface que sarebbe MethodWs.class
        Call<ArrayList<Nivel>> response = methodWs.existenciaNivelSiguiente(nivelRequest);

        response.enqueue(new Callback<ArrayList<Nivel>>() {
            @Override
            public void onResponse(Call<ArrayList<Nivel>> call, Response<ArrayList<Nivel>> response) {
                ArrayList<Nivel> list_niveles = response.body();

                if(list_niveles!=null)
                    myCallbackExiste.onCallback(true);
                else
                    myCallbackExiste.onCallback(false);
            }

            @Override
            public void onFailure(Call<ArrayList<Nivel>> call, Throwable t) {

            }
        });
    }




    public void actualizarCompeticionSiguiente(int codigoUsuario, int numeroNivel, int stadoCandado){

        CompiteRequest compiteRequest = new CompiteRequest();
        compiteRequest.setTxt_codigo_usuario(codigoUsuario);
        compiteRequest.setTxt_numero_nivel(numeroNivel);
        compiteRequest.setTxt_estado_candado(stadoCandado);

        MethodWs methodWs = HelperWs.getConfiguration().create(MethodWs.class);  //Il create me pide un interface que sarebbe MethodWs.class
        Call<Void> response = methodWs.actualizarCompeticionSiguiente(compiteRequest);
        response.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }


    public void actualizarTimer(int codigoUsuario,int numeroNivel,int numTimer){

        CompiteRequest compiteRequest = new CompiteRequest();
        compiteRequest.setTxt_codigo_usuario(codigoUsuario);
        compiteRequest.setTxt_numero_nivel(numeroNivel);
        compiteRequest.setTxt_my_timer(numTimer);

        MethodWs methodWs = HelperWs.getConfiguration().create(MethodWs.class);  //Il create me pide un interface que sarebbe MethodWs.class
        Call<Void> response = methodWs.actualizarTimer(compiteRequest);

        response.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });

    }


    public void actualizarCompeticionActual(int codigoUsuario,int numeroNivel, int misPreguntasResueltas){

        CompiteRequest compiteRequest = new CompiteRequest();
        compiteRequest.setTxt_codigo_usuario(codigoUsuario);
        compiteRequest.setTxt_numero_nivel(numeroNivel);
        compiteRequest.setTxt_preguntas_resueltas(misPreguntasResueltas);

        MethodWs methodWs = HelperWs.getConfiguration().create(MethodWs.class);  //Il create me pide un interface que sarebbe MethodWs.class
        Call<Void> response = methodWs.actualizarCompeticionActual(compiteRequest);

        response.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });

    }



    public void insertarCompetidor(Top competidorTop){

        TopRequest topRequest = new TopRequest();
        topRequest.setTxt_codigo_usuario(competidorTop.getCodigo_usuario());
        topRequest.setTxt_numero_nivel(competidorTop.getNumero_nivel());
        topRequest.setTxt_my_timer(competidorTop.getMy_timer());
        topRequest.setTxt_preguntas_resueltas(competidorTop.getPreguntas_resueltas());

        MethodWs methodWs = HelperWs.getConfiguration().create(MethodWs.class);  //Il create me pide un interface que sarebbe MethodWs.class
        Call<Void> response = methodWs.insertarCompetidor(topRequest);

        response.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }


    public interface MyCallbackExisteTop {
        void onCallback(Top mycompetidorTop);
    }


    public void obtenerCompetidorTop(int myNumeroNivel,final MyCallbackExisteTop myCallbackExisteTop ){


        NivelRequest nivelRequest = new NivelRequest();
        nivelRequest.setTxt_numero_nivel(myNumeroNivel);

        MethodWs methodWs = HelperWs.getConfiguration().create(MethodWs.class);  //Il create me pide un interface que sarebbe MethodWs.class
        Call<Top> response = methodWs.obtenerCompetidorTop(nivelRequest);

        response.enqueue(new Callback<Top>() {
            @Override
            public void onResponse(Call<Top> call, Response<Top> response) {

                Top competidorTop = response.body();

                if(competidorTop!=null)
                    myCallbackExisteTop.onCallback(competidorTop);
                else
                    myCallbackExisteTop.onCallback(null);

            }

            @Override
            public void onFailure(Call<Top> call, Throwable t) {

            }
        });
    }


    public void actualizarCompetidorTop(Top competidorTop){

        TopRequest topRequest = new TopRequest();
        topRequest.setTxt_codigo_usuario(competidorTop.getCodigo_usuario());
        topRequest.setTxt_numero_nivel(competidorTop.getNumero_nivel());
        topRequest.setTxt_my_timer(competidorTop.getMy_timer());
        topRequest.setTxt_preguntas_resueltas(competidorTop.getPreguntas_resueltas());

        MethodWs methodWs = HelperWs.getConfiguration().create(MethodWs.class);  //Il create me pide un interface que sarebbe MethodWs.class
        Call<Void> response = methodWs.actualizarCompetidorTop(topRequest);

        response.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }

}
