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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.lotr_trivial.BD.BDManager;
import com.example.lotr_trivial.Tools.Auxiliares.LectorBitmaps;
import com.example.lotr_trivial.Tools.Perfiles.PerfilManager;

//Activity que gestiona la creación de un nuevo perfil
public class CrearPerfil extends AppCompatActivity {

    private ImageButton abrirCamara;
    private Button aceptarAdd;
    private Button salirAdd;
    private EditText passwordAdd;
    private EditText nombreAdd;
    private PerfilManager perfilManager;
    private Bitmap image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_perfil);
        BDManager BD = new BDManager(this);

        perfilManager= new PerfilManager(BD,this);
        SharedPreferences preferencias= getSharedPreferences(Configuracion.PREFS_NAME,0);
        boolean noche= preferencias.getBoolean(Configuracion.PREFS_NOCHE,false);
        if(noche){
            ConstraintLayout layout= (ConstraintLayout) findViewById(R.id.layoutcrear);
            layout.setBackgroundResource(R.drawable.popup_formulario_noche);
        }
        abrirCamara= (ImageButton) findViewById(R.id.camaraBoton);
        aceptarAdd= (Button) findViewById(R.id.aceptarAdd);
        salirAdd= (Button) findViewById(R.id.cancelarButtonAdd);
        passwordAdd= (EditText) findViewById(R.id.contrasenaAdd);
        nombreAdd= (EditText) findViewById(R.id.nombreAdd);
        onClickAdd();

    }
    private void onClickAdd() {
        abrirCamara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CameraAction();

            }
        });
        if ((ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED)||(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED)) {

            abrirCamara.setClickable(false);
            abrirCamara.setImageResource(R.drawable.default_perfil);
        }
        salirAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(v.getContext(),Perfil.class);
                startActivity(intent);
            }
        });
        aceptarAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                añadirPerfil(nombreAdd.getText().toString(),passwordAdd.getText().toString());
                Intent intent= new Intent(v.getContext(),Perfil.class);
                startActivity(intent);
            }
        });

    }
    public void añadirPerfil(String nombre,String pass){

        if(!nombre.equals("")){
           String nombreFinal= nombre.trim();
            if(perfilManager.Add(nombreFinal,pass)){
                if(image!=null) {
                    LectorBitmaps lb = new LectorBitmaps();
                    lb.EscribirBitmap(image, this, nombreFinal);
                    image = null;
                }
                Toast.makeText(this,"¡Perfil creado con éxito!", Toast.LENGTH_SHORT).show();

            }
            else{
                Toast.makeText(this,"¡Error!¡Perfil ya existente!", Toast.LENGTH_SHORT).show();


            }
        }
        else{
            Toast.makeText(this,"Rellene el campo nombre", Toast.LENGTH_SHORT).show();

        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Perfil.PHOTO_RESULT && resultCode == RESULT_OK ) {

            image= (Bitmap)data.getExtras().get("data");
            abrirCamara.setImageBitmap(image);
            abrirCamara.setScaleX( abrirCamara.getScaleX()*2);
            abrirCamara.setScaleY( abrirCamara.getScaleY()*2);

        }

    }
    private void CameraAction(){

        Intent intent= new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, Perfil.PHOTO_RESULT);
        }

    }
}