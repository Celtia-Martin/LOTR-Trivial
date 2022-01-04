package com.example.lotr_trivial.Tools.Auxiliares.Ranking;

import android.content.SharedPreferences;

import java.util.Comparator;

public class ItemRanking {
    public static final String PREFS_PUNTUACION= "prefPuntuacion";
    public static final String PREFS_NOMBRERANKING= "prefNombreRanking";
    private String nombre;
    private int puntuacion;

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setPuntuacion(int puntuacion) {
        this.puntuacion = puntuacion;
    }

    public String getNombre() {
        return nombre;
    }

    public int getPuntuacion() {
        return puntuacion;
    }
    public void PutPreferences(SharedPreferences preferences, int posicion){
        String prefsPntos=PREFS_PUNTUACION+String.valueOf(posicion);
        String prefsNombre=PREFS_NOMBRERANKING+String.valueOf(posicion);
        SharedPreferences.Editor editor= preferences.edit();
        editor.putString(prefsNombre,this.nombre);
        editor.putInt(prefsPntos,this.puntuacion);
        editor.commit();

    }
    public boolean GetPreferences(SharedPreferences preferences, int posicion){
        String prefsPntos=PREFS_PUNTUACION+String.valueOf(posicion);
        String prefsNombre=PREFS_NOMBRERANKING+String.valueOf(posicion);
        if(preferences.contains(prefsNombre)){
            nombre= preferences.getString(prefsNombre,"Unknown");
            puntuacion= preferences.getInt(prefsPntos,0);
        }

        return preferences.contains(prefsNombre);
    }


}
