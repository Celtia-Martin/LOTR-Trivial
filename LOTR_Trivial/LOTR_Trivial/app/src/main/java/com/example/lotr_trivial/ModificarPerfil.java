package com.example.lotr_trivial;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.lotr_trivial.BD.BDManager;
import com.example.lotr_trivial.Tools.Auxiliares.LectorBitmaps;
import com.example.lotr_trivial.Tools.Enums.RAZA;
import com.example.lotr_trivial.Tools.Perfiles.PerfilManager;
import com.example.lotr_trivial.Tools.Perfiles.PerfilObject;

import java.util.ArrayList;

import static com.example.lotr_trivial.Configuracion.PREFS_CURRENTPERFIL;
import static com.example.lotr_trivial.Configuracion.PREFS_NAME;
import static com.example.lotr_trivial.Configuracion.PREFS_NOCHE;
import static com.example.lotr_trivial.Perfil.PHOTO_RESULT;
import static com.example.lotr_trivial.Tools.Auxiliares.Ranking.RankingAuxiliar.PUNTUACION_ERU;
import static com.example.lotr_trivial.Tools.Auxiliares.Ranking.RankingAuxiliar.PUNTUACION_MAIAR;
import static com.example.lotr_trivial.Tools.Auxiliares.Ranking.RankingAuxiliar.PUNTUACION_VALAR;

//Actividad que gestiona la modificación del perfil con el que se ha iniciado la sesion
public class ModificarPerfil extends AppCompatActivity {
    BDManager BD;
    Button aceptar, cancelar;
    PerfilManager pm;
    Spinner spinnerRaza;
    ImageButton camara;
    CheckBox checkDefault;
    SharedPreferences sharedPreferences;
    Bitmap image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_perfil);
        sharedPreferences= getSharedPreferences(PREFS_NAME,0);
        BD = new BDManager(this);
        pm = new PerfilManager(BD, this);
        boolean noche= sharedPreferences.getBoolean(PREFS_NOCHE,false);
        if(noche){
            ConstraintLayout layout= (ConstraintLayout) findViewById(R.id.layoutraza);
            layout.setBackgroundResource(R.drawable.popup_formulario_noche);
        }

        checkDefault = (CheckBox) findViewById(R.id.checkBoxFoto);
        aceptar = (Button) findViewById(R.id.aceptarModify);
        cancelar = (Button) findViewById(R.id.cancelarModify);
        spinnerRaza = (Spinner)findViewById(R.id.spinnerRaza);
        camara = (ImageButton) findViewById(R.id.botonCamaraModify);
        if ((ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED)||(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED)) {
            camara.setClickable(false);
        }
        String nombre = sharedPreferences.getString(PREFS_CURRENTPERFIL, "");
        PerfilObject current = pm.getPerfil(nombre);
        ArrayList<String> items = new ArrayList<>();
        for (int i = 0; i < RAZA.values().length; i++) {
            items.add(RAZA.values()[i].toString());
        }
        if (current.getMaxPuntuacion() < PUNTUACION_ERU) {
            items.remove(RAZA.ERU.toString());
        }
        if (current.getMaxPuntuacion() < PUNTUACION_VALAR) {
            items.remove(RAZA.VALAR.toString());
        }
        if (current.getMaxPuntuacion() < PUNTUACION_MAIAR) {
            items.remove(RAZA.MAIAR.toString());
        }


        spinnerRaza.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items));

        FuncionalidadBotones();

    }
    private void FuncionalidadBotones(){
        aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LectorBitmaps lb= new LectorBitmaps();
                String nombre=sharedPreferences.getString(PREFS_CURRENTPERFIL,"");
                pm.UpdateRaza(nombre,RAZA.valueOf(String.valueOf(spinnerRaza.getSelectedItem())));
                if(checkDefault.isChecked()){
                    lb.EliminarBitmap(nombre,v.getContext());
                }
                else{
                    if(image!=null) {
                        lb = new LectorBitmaps();
                        lb.EscribirBitmap(image, v.getContext(), nombre);
                        image = null;
                    }

                }
                Toast.makeText(v.getContext(),"Cambios realizados con exito", Toast.LENGTH_SHORT).show();

                Intent intent= new Intent(v.getContext(),Menu.class);
                startActivity(intent);
            }
        });


        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(v.getContext(),Menu.class);
               startActivity(intent);
            }
        });


        camara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(intent, PHOTO_RESULT);
                }

            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && requestCode == PHOTO_RESULT){
            image= (Bitmap)data.getExtras().get("data");
            camara.setImageBitmap(image);
            camara.setScaleX(camara.getScaleX()*2);
            camara.setScaleY(camara.getScaleY()*2);

        }
    }
}