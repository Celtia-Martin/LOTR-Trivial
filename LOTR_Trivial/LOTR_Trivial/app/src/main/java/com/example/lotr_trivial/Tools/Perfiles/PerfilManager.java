package com.example.lotr_trivial.Tools.Perfiles;

import android.content.Context;
import android.database.Cursor;

import com.example.lotr_trivial.BD.BDManager;
import com.example.lotr_trivial.Tools.Enums.RAZA;
import com.example.lotr_trivial.Tools.Auxiliares.LectorBitmaps;

import java.util.ArrayList;

import static com.example.lotr_trivial.Tools.Auxiliares.Ranking.RankingAuxiliar.PUNTUACION_ERU;
import static com.example.lotr_trivial.Tools.Auxiliares.Ranking.RankingAuxiliar.PUNTUACION_MAIAR;
import static com.example.lotr_trivial.Tools.Auxiliares.Ranking.RankingAuxiliar.PUNTUACION_VALAR;

//Clase que gestiona la creación, visionado y actualizado de la base de datos de perfiles
public class PerfilManager {

    ArrayList<PerfilObject> lista;
    BDManager BD;
    Context context;

    public PerfilManager(BDManager bd, Context context){
        this.context= context;
        BD= bd;
        lista= new ArrayList<>();
        Cursor c= bd.getEntriesPerfiles();
        if(c.moveToFirst()){
            do{
                String nombre= c.getString(c.getColumnIndex("nombre"));
                        String data=c.getString(c.getColumnIndex("date"));
                        int puntuacion=c.getInt(c.getColumnIndex("maxpuntuacion"));
                        int npartidas=c.getInt(c.getColumnIndex("npartidas"));
                        RAZA raza=RAZA.valueOf( c.getString(c.getColumnIndex("raza")));
                        String contrasena= c.getString(c.getColumnIndex("contrasena"));
                        lista.add(new PerfilObject(nombre,data,puntuacion,npartidas,contrasena,raza));

            }while(c.moveToNext());


        }

    }
    public ArrayList<PerfilObject> getBD(){
        return lista;
    }
    public boolean ComprobarPerfil(String nombre){
        PerfilObject aux= new PerfilObject(nombre);
        return lista.contains(aux);
    }
    public boolean DeletePerfil(String nombre){
        if(ComprobarPerfil(nombre)){
            BD.deletePerfil(nombre);
            lista.remove(new PerfilObject(nombre));
            LectorBitmaps lb = new LectorBitmaps();
            lb.EliminarBitmap(nombre,context);
            return true;
        }
        else{
            return false;
        }

    }
    public boolean Add(String nombre, String contrasena){

        if(ComprobarPerfil(nombre)){

            return false;
        }
        else{
            PerfilObject nuevo= new PerfilObject(nombre,contrasena);
            BD.insertEntryPerfil(nuevo.getNombre(),nuevo.getMaxPuntuacion(),nuevo.getnPartidas(),nuevo.getUltimaPartida(),contrasena,RAZA.HOBBIT);
            lista.add(nuevo);
            return true;
        }

    }
    public PerfilObject getPerfil(String nombre){
      int id=  lista.indexOf(new PerfilObject(nombre));
      return lista.get(id);
    }
    public void UpdateRaza(String nombre,RAZA nueva){
        PerfilObject current= getPerfil(nombre);
        current.setRaza(nueva);
        BD.changeRaza(nombre,nueva.toString());
    }
    public boolean UpdateInfoPartidas(String nombre, int puntuacion, String data){
        boolean nuevasRazas=false;
        PerfilObject current= getPerfil(nombre);
        current.addPartida();
        if(current.getMaxPuntuacion()<puntuacion){
            if((current.getMaxPuntuacion()<PUNTUACION_ERU)&&(puntuacion>PUNTUACION_ERU)){
                nuevasRazas=true;
            }
            else if((current.getMaxPuntuacion()<PUNTUACION_VALAR)&&(puntuacion>PUNTUACION_VALAR)){
                nuevasRazas=true;
            }
            else if((current.getMaxPuntuacion()<PUNTUACION_MAIAR)&&(puntuacion>PUNTUACION_MAIAR)){
                nuevasRazas=true;
            }
            current.setMaxPuntuacion(puntuacion);

        }
        current.setUltimaPartida(data);
        BD.updatePerfil(current.getnPartidas(),current.getMaxPuntuacion(),data,nombre);
        return  nuevasRazas;
    }

}
