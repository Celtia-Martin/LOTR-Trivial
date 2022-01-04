package com.example.lotr_trivial.BD;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.lotr_trivial.Tools.Enums.TIPO_PREGUNTA;
import com.example.lotr_trivial.Tools.Enums.DIFICULTAD;
import com.example.lotr_trivial.Tools.Enums.RAZA;

//Manejador de la BD. Se han incluido métodos especificos para la aplicacion, tales como inserciones especifícas ( para los perfiles
//o para las preguntas), actualización de perfil, eliminación de un perfil específico...etc
public class BDManager {
    private BDHelper helper;
    private SQLiteDatabase db;

    public BDManager(Context context) {
        this.helper = new BDHelper(context);
        this.db = this.helper.getWritableDatabase();
    }
    public boolean isEmpty(){
        Cursor c= db.rawQuery("SELECT * FROM "+ BDContract.DbEntry.TABLE_NAME,null);
        return !c.moveToFirst();
    }

    public void insertEntry(String pregunta, TIPO_PREGUNTA tipoPregunta, String[] respuesta, int correcta, int[]multimedia, DIFICULTAD dif){
        this.db.insert(BDContract.DbEntry.TABLE_NAME, null,
                this.generateContentValues(pregunta, tipoPregunta.toString(),respuesta,correcta,multimedia, dif.toString()));
    }

    public Cursor getEntries () {
        String[] columns = new String[]{
                BDContract.DbEntry.COLUMN_PREGUNTA,
                BDContract.DbEntry.COLUMN_TIPOPREGUNTA,
                BDContract.DbEntry.COLUMN_RESPUESTA1
                ,BDContract.DbEntry.COLUMN_RESPUESTA2
                ,BDContract.DbEntry.COLUMN_RESPUESTA3
                ,BDContract.DbEntry.COLUMN_RESPUESTA4
                ,BDContract.DbEntry.COLUMN_CORRECTA
                ,BDContract.DbEntry.COLUMN_IDMULTIMEDIA1
                ,BDContract.DbEntry.COLUMN_IDMULTIMEDIA2
                ,BDContract.DbEntry.COLUMN_IDMULTIMEDIA3
                ,BDContract.DbEntry.COLUMN_IDMULTIMEDIA4,
                BDContract.DbEntry.COLUMN_DIFICULTAD
                };
        return db.query(BDContract.DbEntry.TABLE_NAME, columns, null, null,
                null, null, null);
    }

    public Cursor getEntries (int howmany,DIFICULTAD dif) {
        String[] columns = new String[]{
                BDContract.DbEntry.COLUMN_PREGUNTA,
                BDContract.DbEntry.COLUMN_TIPOPREGUNTA,
                BDContract.DbEntry.COLUMN_RESPUESTA1
                ,BDContract.DbEntry.COLUMN_RESPUESTA2
                ,BDContract.DbEntry.COLUMN_RESPUESTA3
                ,BDContract.DbEntry.COLUMN_RESPUESTA4
                ,BDContract.DbEntry.COLUMN_CORRECTA
                ,BDContract.DbEntry.COLUMN_IDMULTIMEDIA1
                ,BDContract.DbEntry.COLUMN_IDMULTIMEDIA2
                ,BDContract.DbEntry.COLUMN_IDMULTIMEDIA3
                ,BDContract.DbEntry.COLUMN_IDMULTIMEDIA4,
                BDContract.DbEntry.COLUMN_DIFICULTAD
        };

        String aux= BDContract.DbEntry.COLUMN_DIFICULTAD+" = '"+dif.toString()+"'";
        return db.query(BDContract.DbEntry.TABLE_NAME, columns, aux, null,
                null, null, "RANDOM()",String.valueOf(howmany));
    }

    public void insertEntryPerfil(String nombre, int puntuacion, int npartidas,  String date, String contra, RAZA raza){
        this.db.insert(BDContract.DbEntryPerfil.TABLE_NAME, null,
                this.generateContentValuesPerfil(nombre, puntuacion, npartidas,  date,contra,raza.toString()));
    }
    public Cursor getEntriesPerfiles () {
        String[] columns = new String[]{
                BDContract.DbEntryPerfil.COLUMN_NOMBRE,
                BDContract.DbEntryPerfil.COLUMN_MAXPUNTUACION,
                BDContract.DbEntryPerfil.COLUMN_NPARTIDAS,
                BDContract.DbEntryPerfil.COLUMN_DATE,
                BDContract.DbEntryPerfil.COLUMN_CONTRASENA,
                BDContract.DbEntryPerfil.COLUMN_RAZA

        };

        return db.query(BDContract.DbEntryPerfil.TABLE_NAME, columns, null, null,
                null, null, null);
    }

    public void deleteAll() {
        this.db.delete(BDContract.DbEntry.TABLE_NAME, null,null);
        this.db.delete(BDContract.DbEntryPerfil.TABLE_NAME, null,null);
    }
    public void deletePreguntas() {
        this.db.delete(BDContract.DbEntry.TABLE_NAME, null,null);
    }

    //Hay que poner un delete exclusivo

    public void deletePerfil(String nombre){
       this.db.delete(BDContract.DbEntryPerfil.TABLE_NAME, BDContract.DbEntryPerfil.COLUMN_NOMBRE+" = '"+nombre+"'",null);
    }
    public void updatePerfil(int npartidas, int puntuacion, String date, String nombre){
        ContentValues contentValues= new ContentValues();
        contentValues.put(BDContract.DbEntryPerfil.COLUMN_MAXPUNTUACION,puntuacion);
        contentValues.put(BDContract.DbEntryPerfil.COLUMN_NPARTIDAS,npartidas);
        contentValues.put(BDContract.DbEntryPerfil.COLUMN_DATE,date);
        this.db.update(BDContract.DbEntryPerfil.TABLE_NAME,contentValues,BDContract.DbEntryPerfil.COLUMN_NOMBRE+" = '"+nombre+"'",null);
    }
    public void changeRaza(String nombre, String raza){
        ContentValues contentValues= new ContentValues();
        contentValues.put(BDContract.DbEntryPerfil.COLUMN_RAZA,raza);
        this.db.update(BDContract.DbEntryPerfil.TABLE_NAME,contentValues,BDContract.DbEntryPerfil.COLUMN_NOMBRE+" = '"+nombre+"'",null);
    }

    private ContentValues generateContentValues(String pregunta, String tipoPregunta, String[] respuesta, int correcta,int[]multimedia,String dificultad){
        ContentValues contentValues = new ContentValues();
        contentValues.put(BDContract.DbEntry.COLUMN_PREGUNTA, pregunta);
        contentValues.put(BDContract.DbEntry.COLUMN_TIPOPREGUNTA, tipoPregunta);
        contentValues.put(BDContract.DbEntry.COLUMN_RESPUESTA1, respuesta[0]);
        contentValues.put(BDContract.DbEntry.COLUMN_RESPUESTA2, respuesta[1]);
        contentValues.put(BDContract.DbEntry.COLUMN_RESPUESTA3, respuesta[2]);
        contentValues.put(BDContract.DbEntry.COLUMN_RESPUESTA4, respuesta[3]);
        contentValues.put(BDContract.DbEntry.COLUMN_CORRECTA, correcta);
        contentValues.put(BDContract.DbEntry.COLUMN_IDMULTIMEDIA1, multimedia[0]);
        contentValues.put(BDContract.DbEntry.COLUMN_IDMULTIMEDIA2, multimedia[1]);
        contentValues.put(BDContract.DbEntry.COLUMN_IDMULTIMEDIA3, multimedia[2]);
        contentValues.put(BDContract.DbEntry.COLUMN_IDMULTIMEDIA4, multimedia[3]);
        contentValues.put(BDContract.DbEntry.COLUMN_DIFICULTAD, dificultad);

        return contentValues;
    }


    private ContentValues generateContentValuesPerfil(String nombre,int puntuacion,int npartidas, String date,String contra, String raza){
        ContentValues contentValues = new ContentValues();
        contentValues.put(BDContract.DbEntryPerfil.COLUMN_NOMBRE,nombre);
        contentValues.put(BDContract.DbEntryPerfil.COLUMN_MAXPUNTUACION,puntuacion);
        contentValues.put(BDContract.DbEntryPerfil.COLUMN_NPARTIDAS,npartidas);
        contentValues.put(BDContract.DbEntryPerfil.COLUMN_DATE,date);
        contentValues.put(BDContract.DbEntryPerfil.COLUMN_RAZA,raza);
        contentValues.put(BDContract.DbEntryPerfil.COLUMN_CONTRASENA,contra);



        return contentValues;
    }
}
