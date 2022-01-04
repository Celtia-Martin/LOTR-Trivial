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
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import com.example.lotr_trivial.Tools.Enums.DIFICULTAD;

//Activity que permite al jugador gestionar diversos aspectos persistentes, así como cerrar sesión
public class Configuracion extends AppCompatActivity implements View.OnClickListener {


    int numPreguntas;
    DIFICULTAD dificultad;
    boolean noche;
    public static final String PREFS_NAME="MyPrefs";

    public static final String PREFS_NUM="PrefNumeroPreguntas";
    public static final String PREFS_DIF="PrefDificultad";
    public static final String PREFS_NOCHE="PrefModoNoche";
    public static final String PREFS_CURRENTPERFIL= "PrefPerfil";
    public static final String PREFS_LOG="PrefLogeado";

    SharedPreferences.Editor editor;
    ConstraintLayout layout;
    Switch switch_modo;
    SeekBar dbar,nbar;
    TextView dtext,ntext;
    SharedPreferences settings;
    //Cerrar sesion
    Button aceptar,cancelar;
    Button cerrarSesion;
    AlertDialog.Builder alertDialog;
    AlertDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracion);
         Inicializar();
         ModoBackground();
         FuncionalidadWidgets();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.nocheSwitch:
                if (switch_modo.isChecked()) {
                    noche = true;
                    layout.setBackgroundResource(R.drawable.background_trivial3);
                } else {
                    noche = false;
                    layout.setBackgroundResource(R.drawable.background_trivial2);
                }
                editor.putBoolean(PREFS_NOCHE,noche);
                editor.commit();
                break;
            case R.id.volverConfig:
                Intent intent= new Intent(this, Menu.class);
                startActivity(intent);
                break;
            case R.id.cerrarSesion:
                if(settings.getBoolean(PREFS_LOG,false)){
                    CreatePopUpConfirmacion();
                }
                else{

                    Toast.makeText(this,"No tiene ninguna sesion iniciada", Toast.LENGTH_SHORT).show();

                }

                break;

        }
    }
    private void CreatePopUpConfirmacion(){
        alertDialog= new AlertDialog.Builder(this);
        final View confirm= getLayoutInflater().inflate(R.layout.popup_confirmacion,null);
        aceptar= (Button) confirm.findViewById(R.id.aceptarConfirmacion);
        cancelar= (Button) confirm.findViewById(R.id.cancelarConfirmacion);
        TextView text= (TextView) confirm.findViewById(R.id.textConfirm);
        text.setText("¿Seguro que quieres cerrar sesión?");
        onClickConfirm();
        alertDialog.setView(confirm);
        dialog= alertDialog.create();
        dialog.show();
    }
    private void  onClickConfirm(){
        aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putBoolean(PREFS_LOG,false);
                editor.commit();
                dialog.cancel();
            }
        });
        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
    }
    public void UpdateDificultad(int progress, TextView dtext){
        if(progress<33){
            dtext.setText(String.valueOf("Dificultad: Fácil"));
            dificultad= DIFICULTAD.FACIL;
        }
        else if(progress<66){
            dtext.setText(String.valueOf("Dificultad: Medio"));
            dificultad= DIFICULTAD.MEDIO;

        }else{
            dtext.setText(String.valueOf("Dificultad: Difícil"));
            dificultad= DIFICULTAD.DIFICIL;
        }
    }
    public void UpdateNumeroPreguntas(int progress, TextView ntext){

        numPreguntas= progress/10;
        if(numPreguntas==0){
            numPreguntas=1;
        }
        ntext.setText("Numero de Preguntas: "+ String.valueOf(numPreguntas));
    }
    private void Inicializar(){
        cerrarSesion= (Button) findViewById(R.id.cerrarSesion);
        cerrarSesion.setOnClickListener(this);
        dbar= (SeekBar)findViewById(R.id.dificulBar);
        nbar= (SeekBar)findViewById(R.id.numBar);
        dtext= (TextView) findViewById(R.id.textDificultad);
        ntext= (TextView) findViewById(R.id.textNum);
        switch_modo= (Switch)findViewById(R.id.nocheSwitch);
        switch_modo.setOnClickListener(this);
        settings= getSharedPreferences(PREFS_NAME,0);
        editor= settings.edit();
        noche= settings.getBoolean(PREFS_NOCHE,false);
        layout= (ConstraintLayout) findViewById(R.id.configLayout);
        switch_modo.setChecked(noche);
        nbar.setProgress(settings.getInt(PREFS_NUM,10)*10);
        UpdateNumeroPreguntas(nbar.getProgress(),ntext);
        dbar.setProgress((DIFICULTAD.valueOf(settings.getString(PREFS_DIF,DIFICULTAD.FACIL.toString()))).ordinal()*33);
        UpdateDificultad(dbar.getProgress(),dtext);
        ImageButton volver= (ImageButton)  findViewById(R.id.volverConfig);
        volver.setOnClickListener(this);
    }
    private void ModoBackground(){
        if(noche){
            layout.setBackgroundResource(R.drawable.background_trivial3);
        }
        else{
            layout.setBackgroundResource(R.drawable.background_trivial2);
        }
    }
    private void FuncionalidadWidgets(){

        dbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                UpdateDificultad(progress,dtext);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar){
                editor.putString(PREFS_DIF,dificultad.toString());
                editor.commit();
            }

        });


        nbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                UpdateNumeroPreguntas(progress,ntext);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                editor.putInt(PREFS_NUM,numPreguntas);
                editor.commit();
            }
        });
    }
}