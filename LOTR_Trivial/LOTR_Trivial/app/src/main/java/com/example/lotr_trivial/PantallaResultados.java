package com.example.lotr_trivial;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.lotr_trivial.BD.BDManager;
import com.example.lotr_trivial.Tools.Auxiliares.Ranking.RankingAuxiliar;
import com.example.lotr_trivial.Tools.Perfiles.PerfilManager;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static com.example.lotr_trivial.Configuracion.PREFS_CURRENTPERFIL;
import static com.example.lotr_trivial.Configuracion.PREFS_NAME;
import static com.example.lotr_trivial.Configuracion.PREFS_NOCHE;

//Activity de la pantalla de resultados
public class PantallaResultados extends AppCompatActivity implements View.OnClickListener {

    //Coge el score que le ha mandado MainActivity y lo visualiza. También asigna
    //la funcionalidad de los dos botones

    TextView puntuacion,tiempo;
    ImageButton reintentar,volver;

    int puntos;
    AlertDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_resultados);
        inicializar();
        actualizarPerfil(puntos);
    }

    private void inicializar(){

        puntuacion= findViewById(R.id.score);
        tiempo= findViewById(R.id.tiempo);
        puntos= getIntent().getIntExtra("puntos",0);
        String tp= getIntent().getStringExtra("tiempo");
        tiempo.setText(tp);
        puntuacion.setText(String.valueOf(puntos));
        reintentar= (ImageButton)findViewById(R.id.boton_reintentar);
        volver= (ImageButton)findViewById(R.id.boton_volver);
        reintentar.setOnClickListener(this);
        volver.setOnClickListener(this);
    }
    //Los botones sacan al jugador de la pantalla, y lo envían o al menú, u otra vez al trivial
    @Override
    public void onClick(View v) {
        Intent intent= null;
        switch (v.getId()){
            case R.id.boton_reintentar:

                intent= new Intent(this, MainActivity.class);
                break;
            case R.id.boton_volver:
                intent= new Intent(this, Menu.class);

                break;
            default:
                try {
                    throw new Exception();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
        startActivity(intent);
    }
    public void actualizarPerfil(int puntos){
        boolean PopupRaza=false;
        SharedPreferences preferencias= getSharedPreferences(PREFS_NAME,0);
        boolean noche= preferencias.getBoolean(PREFS_NOCHE,false);
        BDManager bd= new BDManager(this);
        PerfilManager pm= new PerfilManager(bd,this);
        String nombre= preferencias.getString(PREFS_CURRENTPERFIL,"");
        if(pm.ComprobarPerfil(nombre)){
            Date time= (Date)Calendar.getInstance().getTime();

            SimpleDateFormat sdf= new SimpleDateFormat("dd/MM/yyyy");
            String date= sdf.format(time);
            PopupRaza= pm.UpdateInfoPartidas(nombre,puntos,date);
        }
        RankingAuxiliar rankingAuxiliar= new RankingAuxiliar(preferencias);
        rankingAuxiliar.nuevaPuntuacion(puntos,nombre);
        if(PopupRaza){
            CreatePopUpRaza(noche);
        }

    }
    private void CreatePopUpRaza(boolean noche){
        AlertDialog.Builder db= new AlertDialog.Builder(this);
        final View razaPopup= getLayoutInflater().inflate(R.layout.popup_razadesbloqueada,null);
        if(noche){
          ConstraintLayout layout= razaPopup.findViewById(R.id.layoutraza);
          layout.setBackgroundResource(R.drawable.cuadro_dialogo_noche);
        }
        Button aceptar= (Button) razaPopup.findViewById(R.id.aceptarPopupDesbloqueado);
       aceptar.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               dialog.cancel();
           }
       });
        db.setView(razaPopup);
        dialog= db.create();
        dialog.show();
    }
}