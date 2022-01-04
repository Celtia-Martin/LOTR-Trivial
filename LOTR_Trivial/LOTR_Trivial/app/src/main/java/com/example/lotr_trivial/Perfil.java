package com.example.lotr_trivial;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.lotr_trivial.BD.BDManager;
import com.example.lotr_trivial.Tools.Perfiles.AdaptadorPerfil;
import com.example.lotr_trivial.Tools.Perfiles.PerfilManager;
import com.example.lotr_trivial.Tools.Perfiles.PerfilObject;

import java.util.ArrayList;

import static com.example.lotr_trivial.Configuracion.PREFS_CURRENTPERFIL;
import static com.example.lotr_trivial.Configuracion.PREFS_LOG;
import static com.example.lotr_trivial.Configuracion.PREFS_NAME;
import static com.example.lotr_trivial.Configuracion.PREFS_NOCHE;

//Actividad que gestiona los perfiles. Se muestran todos los perfiles creados, y el usuario puede eliminar un perfil,
//crearlo o hacer login
public class Perfil extends AppCompatActivity implements View.OnClickListener {


    boolean noche=false;
    //Referencias

    RecyclerView vistaPerfiles;
    ArrayList<PerfilObject> listaPerfiles;
    PerfilManager perfilManager;
    BDManager bdManager;
    ImageButton addPerfil,deletePerfil,login;
    AdaptadorPerfil adaptadorPerfil;
    SharedPreferences preferencias;
    SharedPreferences.Editor editor;
    ImageButton volver;

    //variables añadir

    AlertDialog dialog;
    Bitmap image;
    final static int PHOTO_RESULT=1;
    final static int PERMISSION_REQUEST = 100;

    //Variables eliminar
    AlertDialog.Builder dialogBuilderDelete;
    AlertDialog.Builder dialogBuilderConfirm;
    AlertDialog dialogconf;
    Button aceptarDelete, aceptarConfirm, cancelarDelete, cancelarConfirm;
    EditText passwordDelete,nombreDelete;
    String nombreEliminar;

    //Variables login
    Button aceptarLogin,cancelarLogin;
    EditText passwordLogin,nombreLogin;
    AlertDialog.Builder dialogBuilderLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        Inicializar();
        PedirPermiso();
        llenarPerfiles();
        UpdateRecycler();

    }
    private void PedirPermiso(){
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
    private  void Inicializar(){
        image=null;

        preferencias= getSharedPreferences(PREFS_NAME,0);
        noche = preferencias.getBoolean(PREFS_NOCHE,false);
        if(noche){
            ConstraintLayout layout=( (ConstraintLayout) findViewById(R.id.layoutperfil));
            layout.setBackgroundResource(R.drawable.background_trivial_noche);
        }
        editor= preferencias.edit();
        deletePerfil= (ImageButton) findViewById(R.id.deletePerfil);
        deletePerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createPopUpDelete();
            }
        });
        addPerfil= (ImageButton) findViewById(R.id.addPerfil);
        addPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(v.getContext(),CrearPerfil.class);
                startActivity(intent);
            }
        });
        login= (ImageButton) findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createPopUpLogin();
            }
        });
        vistaPerfiles = (RecyclerView) findViewById(R.id.perfilesView);
        vistaPerfiles.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        vistaPerfiles.setLayoutManager(new LinearLayoutManager(this));
        volver= (ImageButton) findViewById(R.id.volverPerfil);
        volver.setOnClickListener(this);
    }
    private void llenarPerfiles(){

        listaPerfiles = new ArrayList<>();
        bdManager = new BDManager(this);
        perfilManager = new PerfilManager(bdManager,this);
        listaPerfiles = perfilManager.getBD();
    }

    @Override
    public void onClick(View v) {
        Intent intent;

        switch (v.getId()){
            case R.id.volverPerfil:
                intent= new Intent(this, Menu.class);
                startActivity(intent);
                break;
        }
    }
    public void createPopUpLogin(){
        dialogBuilderLogin= new AlertDialog.Builder(this);
        final View loginPopUp= getLayoutInflater().inflate(R.layout.popup_login,null);
        ConstraintLayout layout= (ConstraintLayout)loginPopUp.findViewById(R.id.loginlayout);
        if(noche){
            layout.setBackgroundResource(R.drawable.cuadro_dialogo_noche);
        }
        aceptarLogin= (Button) loginPopUp.findViewById(R.id.aceptarLogin);
        cancelarLogin= (Button) loginPopUp.findViewById(R.id.cancelarLogin);
        passwordLogin= (EditText) loginPopUp.findViewById(R.id.contraLogin);
        nombreLogin= (EditText) loginPopUp.findViewById(R.id.nombreLogin);
        onClickLogin();
        dialogBuilderLogin.setView(loginPopUp);
        dialog= dialogBuilderLogin.create();
        dialog.show();

    }

    public void createPopUpDelete(){
        dialogBuilderDelete= new AlertDialog.Builder(this);
        dialogBuilderConfirm= new AlertDialog.Builder(this);
        final View deletePopUp= getLayoutInflater().inflate(R.layout.popup_delete,null);
        final View confirmPopUp= getLayoutInflater().inflate(R.layout.popup_confirmacion,null);
        ConstraintLayout layout= (ConstraintLayout)deletePopUp.findViewById(R.id.layoutdelete);
        ConstraintLayout layout2= (ConstraintLayout)confirmPopUp.findViewById(R.id.layoutconfirm);
        if(noche){
            layout.setBackgroundResource(R.drawable.cuadro_dialogo_noche);
            layout2.setBackgroundResource(R.drawable.cuadro_dialogo_noche);
        }
        aceptarDelete=(Button) deletePopUp.findViewById(R.id.deleteAceptar);
        cancelarDelete=(Button) deletePopUp.findViewById(R.id.deleteCancel);
        aceptarConfirm=(Button) confirmPopUp.findViewById(R.id.aceptarConfirmacion);
        cancelarConfirm=(Button) confirmPopUp.findViewById(R.id.cancelarConfirmacion);
        passwordDelete=( EditText) deletePopUp.findViewById(R.id.contrasenaDelete);
        nombreDelete= (EditText) deletePopUp.findViewById(R.id.nombreDelete);
        dialogBuilderDelete.setView(deletePopUp);
        dialogBuilderConfirm.setView(confirmPopUp);
        onClickDelete();
        dialogconf= dialogBuilderConfirm.create();
        dialog= dialogBuilderDelete.create();
        dialog.show();

    }

    public void UpdateRecycler(){
        adaptadorPerfil= new AdaptadorPerfil(listaPerfiles);
        vistaPerfiles.setAdapter(adaptadorPerfil);
    }

    public void deletePerfil(String nombre,String pass){
        String nombreFinal= nombre.trim();
        if(!nombreFinal.equals("")){
            if(perfilManager.ComprobarPerfil(nombreFinal)){
                if(perfilManager.getPerfil(nombreFinal).ContraseñaCorrecta(pass)){
                    nombreEliminar=nombreFinal;
                    dialogconf.show();
                }
                else{
                    Toast.makeText(this,"¡Error!¡Contraseña incorrecta!", Toast.LENGTH_SHORT).show();

                }
            }
            else{
                Toast.makeText(this,"¡Error!¡Perfil no encontrado!", Toast.LENGTH_SHORT).show();

            }
        }
        else{
            Toast.makeText(this,"Rellene el campo nombre", Toast.LENGTH_SHORT).show();

        }
    }

    public void loginPerfil(String nombre, String pass){
        String nombreFinal= nombre.trim();
        if(!nombreFinal.equals("")){
            if(perfilManager.ComprobarPerfil(nombreFinal)){
                if(perfilManager.getPerfil(nombreFinal).ContraseñaCorrecta(pass)){

                    editor.putString(PREFS_CURRENTPERFIL,nombreFinal);
                    editor.putBoolean(PREFS_LOG,true);
                    editor.commit();
                    Toast.makeText(this,"Iniciada la sesión con éxito", Toast.LENGTH_SHORT).show();

                    dialog.cancel();
                    //quizas meterle un booleano de "logeado"
                }
                else{
                    Toast.makeText(this,"Contraseña no válida", Toast.LENGTH_SHORT).show();

                }

            }else{
                Toast.makeText(this,"Perfil no existente", Toast.LENGTH_SHORT).show();

            }
        }
        else{
            Toast.makeText(this,"Nombre no válido", Toast.LENGTH_SHORT).show();

        }
    }

    private void onClickLogin(){
        cancelarLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        aceptarLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginPerfil(nombreLogin.getText().toString(),passwordLogin.getText().toString());

            }
        });
    }
    private void onClickDelete(){
        aceptarDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deletePerfil(nombreDelete.getText().toString(),passwordDelete.getText().toString());
                if(preferencias.getBoolean(PREFS_LOG,false)){
                    if(preferencias.getString(PREFS_CURRENTPERFIL,"").equals(nombreDelete.getText().toString())){
                        editor.putBoolean(PREFS_LOG,false);
                        editor.commit();
                    }
                }
            }
        });
        cancelarDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        cancelarConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogconf.cancel();
            }
        });
        aceptarConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                perfilManager.DeletePerfil(nombreEliminar);
                UpdateRecycler();
                Toast.makeText(v.getContext(),"Perfil eliminado con éxito", Toast.LENGTH_SHORT).show();

                dialogconf.cancel();
                dialog.cancel();
            }
        });
    }
}