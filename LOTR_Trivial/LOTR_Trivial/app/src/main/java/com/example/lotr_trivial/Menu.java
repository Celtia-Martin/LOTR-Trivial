package com.example.lotr_trivial;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lotr_trivial.BD.BDManager;
import com.example.lotr_trivial.Tools.Perfiles.PerfilManager;

import java.util.ArrayList;

import static com.example.lotr_trivial.Configuracion.PREFS_CURRENTPERFIL;
import static com.example.lotr_trivial.Configuracion.PREFS_LOG;
import static com.example.lotr_trivial.Configuracion.PREFS_NAME;
import static com.example.lotr_trivial.Configuracion.PREFS_NOCHE;
import static com.example.lotr_trivial.Perfil.PERMISSION_REQUEST;

//Activity del menu principal. Aquí el usuario podrá empezar una partida, cambiar la configuración, modificar su perfil, ver el ranking
//o ir a la activity de gestion de perfiles.
public class Menu extends AppCompatActivity implements View.OnClickListener {

    //Referencias

    Button start;
    ConstraintLayout layout;
    Boolean noche= false;
    TextView usuario;
    SharedPreferences sharedPreferences;

    //Modify
    AlertDialog dialog;
    AlertDialog.Builder dialogBuilder;
    Spinner spinnerRaza;
    CheckBox checkDefault;
    PerfilManager pm;
    BDManager BD;
    Bitmap image;
    ImageButton camara,settings,perfiles,ranking,modify;
    Button aceptar,cancelar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_menu);

        Inicializar();
        ModoBackground();
        insertUsuario();

    }

    private void ModoBackground(){
        if(noche){
            layout.setBackgroundResource(R.drawable.background_title_night);
        }
        else{
            layout.setBackgroundResource(R.drawable.background_title_day);
        }
    }
    private void Inicializar(){
        layout= (ConstraintLayout) findViewById(R.id.layoutMenu) ;
        start= (Button) findViewById(R.id.start);
        usuario= (TextView) findViewById(R.id.usuarioText);
        settings=(ImageButton) findViewById(R.id.settingsButton);
        perfiles= (ImageButton) findViewById(R.id.perfilButton);
        ranking= (ImageButton) findViewById(R.id.boton_ranking);
        modify= (ImageButton) findViewById(R.id.botonModify);
        modify.setOnClickListener(this);
        ranking.setOnClickListener(this);
        perfiles.setOnClickListener(this);
        settings.setOnClickListener(this);
        start.setOnClickListener(this);
        sharedPreferences= getSharedPreferences(PREFS_NAME,0);
        noche= sharedPreferences.getBoolean(PREFS_NOCHE,false);
    }
    //Paso de pantalla y cambio del fondo por el switch
    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.start:
                 if(sharedPreferences.getBoolean(PREFS_LOG,false)){
                     intent= new Intent(this, MainActivity.class);
                     startActivity(intent);
                 }
                 else{
                     Toast.makeText(this,"Inicia sesión para comenzar", Toast.LENGTH_SHORT).show();

                 }
                break;
            case R.id.settingsButton:
                intent= new Intent(this, Configuracion.class);
                startActivity(intent);

                break;
            case R.id.perfilButton:
                intent= new Intent(this, Perfil.class);
                startActivity(intent);
                break;
            case R.id.boton_ranking:
                intent= new Intent(this, Ranking.class);
                startActivity(intent);
                break;
            case R.id.botonModify:
                if(sharedPreferences.getBoolean(PREFS_LOG,false)){
                    CreatePopUpModify();
                }
                else{
                    Toast.makeText(this,"Debes iniciar sesión", Toast.LENGTH_SHORT).show();

                }

                break;
        }
    }
    private void insertUsuario(){

        if(sharedPreferences.getBoolean(PREFS_LOG,false)){
            usuario.setText("Usuario: "+sharedPreferences.getString(PREFS_CURRENTPERFIL,"Unknown"));
        }
        else{
            usuario.setText("Sesion no iniciada");
        }


    }

   void CreatePopUpModify(){
        PedirPermisos();
       Intent intent= new Intent(this,ModificarPerfil.class);
       startActivity(intent);
        //ConstruirModify();

    }
    private void PedirPermisos(){
        ArrayList<String> permisos= new ArrayList<>();
        boolean hayquepedir=false;
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
            hayquepedir=true;
            permisos.add(Manifest.permission.CAMERA);
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
        {
            hayquepedir=true;
            permisos.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        if(hayquepedir){
            String[] permitir= permisos.toArray(new String[0]);

            ActivityCompat.requestPermissions(this,permitir, PERMISSION_REQUEST );
        }
    }
    /*
    private void ConstruirModify(){
        BD= new BDManager(this);
        pm= new PerfilManager(BD,this);
        dialogBuilder= new AlertDialog.Builder(this);
        final View modifyPopUp= getLayoutInflater().inflate(R.layout.popup_modify,null);
        checkDefault= (CheckBox) modifyPopUp.findViewById(R.id.checkBoxFoto) ;
        aceptar= (Button) modifyPopUp.findViewById(R.id.aceptarModify);
        cancelar= (Button) modifyPopUp.findViewById(R.id.cancelarModify);
        spinnerRaza= (Spinner) modifyPopUp.findViewById(R.id.spinnerRaza);
        camara= (ImageButton) modifyPopUp.findViewById(R.id.botonCamaraModify);
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)== PackageManager.PERMISSION_DENIED){
            camara.setClickable(false);
        }
        String nombre=sharedPreferences.getString(PREFS_CURRENTPERFIL,"");
        PerfilObject current= pm.getPerfil(nombre);
        ArrayList<String> items= new ArrayList<>();
        for(int i=0; i<RAZA.values().length;i++){
            items.add(RAZA.values()[i].toString());
        }
        if(current.getMaxPuntuacion()<PUNTUACION_ERU){
           items.remove(RAZA.ERU.toString());
        }
       if(current.getMaxPuntuacion()<PUNTUACION_VALAR){
           items.remove(RAZA.VALAR.toString());
        }
        if(current.getMaxPuntuacion()<PUNTUACION_MAIAR){
            items.remove(RAZA.MAIAR.toString());
        }


        spinnerRaza.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item, items));

        FuncionalidadBotones();
        dialogBuilder.setView(modifyPopUp);
        dialog= dialogBuilder.create();
        dialog.show();

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

                dialog.cancel();
            }
        });


        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
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
           // camara.setImageBitmap(image);

        }
    }*/
}