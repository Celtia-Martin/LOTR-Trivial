package com.example.lotr_trivial;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;

import com.example.lotr_trivial.BD.BDManager;
import com.example.lotr_trivial.FragmentosPregunta.Audio_Texto;
import com.example.lotr_trivial.FragmentosPregunta.FragmentoPregunta;
import com.example.lotr_trivial.FragmentosPregunta.Imagen_Boton;
import com.example.lotr_trivial.FragmentosPregunta.Texto_Boton;
import com.example.lotr_trivial.FragmentosPregunta.Texto_Imagen;
import com.example.lotr_trivial.FragmentosPregunta.Texto_RadioBoton;
import com.example.lotr_trivial.FragmentosPregunta.Texto_Seek;
import com.example.lotr_trivial.FragmentosPregunta.Texto_Spinner;
import com.example.lotr_trivial.FragmentosPregunta.Video_Texto;
import com.example.lotr_trivial.Preguntas.Pregunta;
import com.example.lotr_trivial.Tools.Auxiliares.CalculoPuntuacion;
import com.example.lotr_trivial.Tools.Enums.DIFICULTAD;
import com.example.lotr_trivial.Tools.Auxiliares.GeneradorPreguntas;

import java.util.ArrayList;

import static com.example.lotr_trivial.Configuracion.PREFS_DIF;
import static com.example.lotr_trivial.Configuracion.PREFS_NAME;
import static com.example.lotr_trivial.Configuracion.PREFS_NOCHE;
import static com.example.lotr_trivial.Configuracion.PREFS_NUM;

//Aquí es donde se implementara el juego en sí
public class MainActivity extends AppCompatActivity implements View.OnClickListener, Texto_Boton.OnFragmentInteractionListener, Imagen_Boton.OnFragmentInteractionListener{

    //Referencias a Widgets
    Button volver;
    TextView currentScore;
    TextView currentPregunta;
    Chronometer cronometro;
    ConstraintLayout layout;
    //Preguntas
    ArrayList<Pregunta> bdpreguntas= new ArrayList<>();

    //Otras variables
    int numPreguntas = 10;
    int contPreguntas = 0;
    public int puntos = 0;
    boolean noche;
    long timepause = 0;
    DIFICULTAD dificultad;
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Inicializar();
        ModoBackground();
        CreacionPreguntas(dificultad);
        PasarPregunta();

    }
    private void Inicializar(){
        cronometro = (Chronometer) findViewById(R.id.crono);
        layout = (ConstraintLayout) findViewById(R.id.layoutTrivial);
        preferences= getSharedPreferences(PREFS_NAME,0);
        noche = preferences.getBoolean(PREFS_NOCHE,false);
        numPreguntas = preferences.getInt(PREFS_NUM,10);
        currentScore = (TextView) findViewById(R.id.currentScore);
        currentPregunta = (TextView) findViewById(R.id.numero_pregunta) ;
        volver = (Button)findViewById(R.id.salir);
        volver.setOnClickListener(this);
        cronometro.setBase(SystemClock.elapsedRealtime());
        cronometro.start();
        dificultad= DIFICULTAD.valueOf(preferences.getString(PREFS_DIF,DIFICULTAD.FACIL.toString()));
    }
    private void ModoBackground(){
        if(noche){
            layout.setBackgroundResource(R.drawable.background_trivial3);
        }
        else{
            layout.setBackgroundResource(R.drawable.background_trivial2);
        }
    }

    //Método auxiliar que crea un generador y genera n preguntas
    public void CreacionPreguntas(DIFICULTAD dif){

        //CREACION BASE DE DATOS
        BDManager bd= new BDManager(this);
        GeneradorPreguntas gp= new GeneradorPreguntas(this);

        if(bd.isEmpty()){
            gp.transformarPreguntas(bd);
        }


        bdpreguntas.addAll(gp.getPreguntas(numPreguntas,bd,dif));
    }

    private String MillistoString(int s){

        int min= (int) s / 60;
        int finals= s % 60;

        if(finals < 10){
            return "" + min + ":0" + finals;
        }
        else{
            return "" + min + ":" + finals;
        }
    }
    //Método auxiliar que pasa de la pantalla del trivial a la de resultados
    private void PasarPantalla(){
        cronometro.stop();
        Intent intent= new Intent( this, PantallaResultados.class);

        long elapsedmillis = SystemClock.elapsedRealtime() - cronometro.getBase();
        int tiempoS = (int) (elapsedmillis/1000);
        intent.putExtra("tiempo",MillistoString(tiempoS));

        //Calculo de puntos: se resta
        CalculoPuntuacion calculoPuntuacion= new CalculoPuntuacion();
        int puntosFinales= calculoPuntuacion.calcularPuntuacion(tiempoS, contPreguntas, puntos, dificultad);
        intent.putExtra("puntos",puntosFinales);
        intent.putExtra("maxpuntos",numPreguntas);
        intent.putExtra("noche",noche);
        startActivity(intent);
    }

    //Método que cambia el fragment que se visualiza, es decir, pasa de pregunta
    public void PasarPregunta(){

            if(contPreguntas>=numPreguntas){// Si se han acabado las preguntas
                PasarPantalla();
            }
            else{
                Pregunta siguiente= bdpreguntas.get(contPreguntas);
                FragmentoPregunta siguientePantalla= new FragmentoPregunta();

                //Dependiendo del tipo, que tipo de fragmento ha de generarse?
                switch (siguiente.tipo){
                    case IMAGEN_TEXTO:
                        siguientePantalla = new Imagen_Boton();
                        break;
                    case TEXTO_TEXTO:
                        siguientePantalla = new Texto_Boton();
                        break;
                    case TEXTO_RADIO:
                        siguientePantalla= new Texto_RadioBoton();
                        break;
                    case TEXTO_SPINNER:
                        siguientePantalla= new Texto_Spinner();
                        break;
                    case TEXTO_IMAGEN:
                        siguientePantalla= new Texto_Imagen();
                        break;
                    case TEXTO_SEEK:
                        siguientePantalla= new Texto_Seek();
                        break;
                    case AUDIO_TEXTO:
                        siguientePantalla= new Audio_Texto();
                        break;
                    case VIDEO_TEXTO:
                        siguientePantalla= new Video_Texto();
                        break;

                }
                //Transición del fragment
                FragmentTransaction transition =  getSupportFragmentManager().beginTransaction();
                transition.replace(R.id.QuestionContainer, siguientePantalla);
                transition.commit();
                siguientePantalla.setPregunta(siguiente);
                contPreguntas++;
                currentScore.setText(String.valueOf(puntos) + "/" + String.valueOf(contPreguntas - 1));
                currentPregunta.setText("q"+String.valueOf(contPreguntas) + "/" + String.valueOf(numPreguntas));
            }
    }
    @Override
    public void onClick(View v) {
        switch(v.getId()){

            case R.id.salir:
                PasarPantalla();
                break;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        cronometro.stop();
        timepause = SystemClock.elapsedRealtime() - cronometro.getBase();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(timepause != 0){
            cronometro.setBase(SystemClock.elapsedRealtime() - timepause);
        }

        cronometro.start();
        Log.i("hola",String.valueOf(cronometro.getBase()));
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}