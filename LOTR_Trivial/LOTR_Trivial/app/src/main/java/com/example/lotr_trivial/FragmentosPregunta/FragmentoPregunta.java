package com.example.lotr_trivial.FragmentosPregunta;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import com.example.lotr_trivial.MainActivity;
import com.example.lotr_trivial.Preguntas.Pregunta;
import com.example.lotr_trivial.Preguntas.Pregunta_Texto_Imagen;
import com.example.lotr_trivial.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;



import java.io.InputStream;

//Clase genérica de la que heredan todos los fragmentos que contienen las preguntas del trivial. Cuenta con métodos auxiliares
// para poder agilizar la nueva creación de otro tipo de preguntas


//Los diferentes hijos de esta clase varían sobre todo en el metodo "onViewCreated", ya que es en este método donde
//las referencias a widgets deben establecerse, y como cada widget en cada fragmento tiene ids diferentes
//deben especificarse. Además, algunos fragmentos tienen funcionalidades específicas de su tipo
public class FragmentoPregunta extends Fragment implements View.OnClickListener {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    //Variables
    //Referencias a las View
    View[] respuestas= new View[4];
    TextView pregunta;

    //Referencia a la informacion de la pregunta
    Pregunta item;
    int cual=0;//Respuesta seleccionada por el jugador

    //Variables auxiliares
    CountDownTimer timer;
    String Coloracertado= "#6e9784";
    String Colorfallado= "#915443";


    //UpdateGUI: Métodos para personalizar los widgets del fragmento según la pregunta
    //Son dos: uno para las preguntas de texto con respuestas de texto, y otro para las preguntas de texto con
    //imagenes como respuesta

    public void UpdateGUIBotones() {
        pregunta.setText(item.pregunta);
        for(int i=0; i<item.numRespuestas; i++){
            ((Button)respuestas[i]).setText(item.respuestas.get(i));
        }
    }

    public void UpdateGUIImagenes(){

        pregunta.setText(item.pregunta);
        for(int i=0; i<item.numRespuestas; i++){
            LeerRaw(((Pregunta_Texto_Imagen)item).images.get(i),(ImageView)respuestas[i]);
        }

    }

    public void setPregunta(Pregunta item) {
        this.item =  item;
    }

    //Metodo auxiliar que averigüa qué respuesta ha sido seleccionada
    public void RespuestaSeleccionada(View[] views ,View v){
        for(int i=0; i<item.numRespuestas;i++){
            if(v.getId()==views[i].getId()){
                cual=i;
            }
        }
    }

    //Método auxiliar que lee una imagen raw y se la añade a una ImageView. Como ImageButton hereda de ImageView,
    //se puede usar tanto para respuestas como para preguntas
    public void LeerRaw(int id, ImageView v){
        try{
            InputStream recursoRaw= getResources().openRawResource(id);
            Bitmap bitmap= BitmapFactory.decodeStream(recursoRaw);
            v.setImageBitmap(bitmap);

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void LeerVideo(int id, VideoView v){
        v.setVideoURI(Uri.parse("android.resource://"+getActivity().getPackageName()+"/"+id));
        v.setMediaController(new MediaController(getActivity()));

        v.requestFocus(); v.start();
    }
    //Cuando se hace click a una respuesta, se comprueba si se ha respondido bien y se lanza el timer
    @Override
    public void onClick(View v) {
        RespuestaSeleccionada(respuestas,v);
        responderView(respuestas);
        lanzarTimer();

    }

    //Metodo que, (independientemente de si las respuestas son de texto o de imagen) desactiva
    //la interfaz de respuestas y, mediante la utilizacion de los colores rojo y verde,
    //se le comunica al jugador si ha acertado. En caso contrario, también se indica la respuesta
    //que era correcta
    public void responderView(View[] view){
        for(int i=0; i<respuestas.length; i++){
           view[i].setClickable(false);
        }
        if (item.Responder(cual)) {
            view[cual].setBackgroundColor(Color.parseColor(Coloracertado));
            ((MainActivity) getActivity()).puntos++;

        } else {
            view[cual].setBackgroundColor(Color.parseColor(Colorfallado));
            view[item.correcta].setBackgroundColor(Color.parseColor(Coloracertado));
        }
    }

    //Este metodo lanza un CountDownTimer que espera 2 segundos y luego pasa a la siguiente pregunta
    //Esto sirve para dejar unos segundos en el que el jugador verá si ha respondido bien o no
    public void lanzarTimer(){
        timer= new CountDownTimer(2000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }
            public void onFinish() {
                ((MainActivity) getActivity()).PasarPregunta();
            }
        }.start();
    }

    //Se hace Override de los siguientes dos métodos para que no haya problemas con el timer
    @Override
    public void onPause() {
        super.onPause();
        if(timer!=null){
            timer.cancel();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if(timer!=null){
            timer.start();
        }
    }

    //Codigo generado por Android Studio


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_texto__boton, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    public FragmentoPregunta() {
        // Required empty public constructor
    }

    public static FragmentoPregunta newInstance(String param1, String param2) {
        Texto_Boton fragment = new Texto_Boton();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

}

