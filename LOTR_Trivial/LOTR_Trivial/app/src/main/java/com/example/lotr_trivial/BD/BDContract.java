package com.example.lotr_trivial.BD;

import android.provider.BaseColumns;


public class BDContract {

    static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + BDContract.DbEntry.TABLE_NAME + " (" +
                    BDContract.DbEntry._ID + " INTEGER PRIMARY KEY," +
                    DbEntry.COLUMN_PREGUNTA + " TEXT," +
                    DbEntry.COLUMN_TIPOPREGUNTA + " TEXT,"+
                    DbEntry.COLUMN_RESPUESTA1 + " TEXT," +
                    DbEntry.COLUMN_RESPUESTA2 + " TEXT,"+
                    DbEntry.COLUMN_RESPUESTA3 + " TEXT," +
                    DbEntry.COLUMN_RESPUESTA4 + " TEXT,"+
                    DbEntry.COLUMN_CORRECTA + " INTEGER," +
                    DbEntry.COLUMN_IDMULTIMEDIA1 + " INTEGER,"+
                    DbEntry.COLUMN_IDMULTIMEDIA2 + " INTEGER," +
                    DbEntry.COLUMN_IDMULTIMEDIA3 + " INTEGER,"+
                    DbEntry.COLUMN_IDMULTIMEDIA4 + " INTEGER,"+
                    DbEntry.COLUMN_DIFICULTAD+ " TEXT)";

    static final String SQL_CREATE_ENTRIES_PERFIL=
            "CREATE TABLE " + DbEntryPerfil.TABLE_NAME+" ("+
                    BDContract.DbEntryPerfil._ID+" INTEGER PRIMARY KEY,"+
                    DbEntryPerfil.COLUMN_NOMBRE+" TEXT,"+
                    DbEntryPerfil.COLUMN_MAXPUNTUACION+" INTEGER,"+
                    DbEntryPerfil.COLUMN_NPARTIDAS+" INTEGER,"+
                    DbEntryPerfil.COLUMN_CONTRASENA+" TEXT,"+
                    DbEntryPerfil.COLUMN_RAZA+" TEXT,"+
                    DbEntryPerfil.COLUMN_DATE+" TEXT)";

    static final String SQL_DELETE_ENTRIES_PERFIL =
            "DROP TABLE IF EXISTS " + BDContract.DbEntryPerfil.TABLE_NAME;

    static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + BDContract.DbEntry.TABLE_NAME;

    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private BDContract() {}

    /* Inner class that defines the table contents */
    static class DbEntry implements BaseColumns {
        static final String TABLE_NAME = "Preguntas";
        static final String COLUMN_PREGUNTA = "pregunta";
        static final String COLUMN_TIPOPREGUNTA = "tipo";
        static final String COLUMN_RESPUESTA1 = "respuesta1";
        static final String COLUMN_RESPUESTA2 = "respuesta2";
        static final String COLUMN_RESPUESTA3 = "respuesta3";
        static final String COLUMN_RESPUESTA4 = "respuesta4";
        static final String COLUMN_CORRECTA = "correcta";
        static final String COLUMN_IDMULTIMEDIA1 = "idmultimedia1";
        static final String COLUMN_IDMULTIMEDIA2 = "idmultimedia2";
        static final String COLUMN_IDMULTIMEDIA3 = "idmultimedia3";
        static final String COLUMN_IDMULTIMEDIA4 = "idmultimedia4";
        static final String COLUMN_DIFICULTAD= "dificultad";

    }
    static class DbEntryPerfil implements BaseColumns {
        static final String TABLE_NAME = "Perfiles";
        static final String COLUMN_NOMBRE = "nombre";
        static final String COLUMN_MAXPUNTUACION="maxpuntuacion";
        static final String COLUMN_NPARTIDAS="npartidas";
        static final String COLUMN_DATE= "date" ;
        static final String COLUMN_CONTRASENA= "contrasena";
        static final String COLUMN_RAZA ="raza";


    }
}