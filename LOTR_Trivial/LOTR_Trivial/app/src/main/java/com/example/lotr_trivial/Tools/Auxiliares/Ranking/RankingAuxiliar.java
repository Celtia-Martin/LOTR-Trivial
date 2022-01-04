package com.example.lotr_trivial.Tools.Auxiliares.Ranking;

import android.content.SharedPreferences;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

public class RankingAuxiliar {

    ArrayList<ItemRanking> ranking= new ArrayList<>();
    SharedPreferences preferences;
    public static final int posiciones= 6;
    public static final int PUNTUACION_ERU=3200;
    public static final int PUNTUACION_VALAR=2500;
    public static final int PUNTUACION_MAIAR=2000;
    public static final int PUNTUACION_ELFO=1500;
    public static final int PUNTUACION_ENANO=1000;
    public static final int PUNTUACION_ENT=600;
    private boolean HuecoVacio=false;
    // Con que haya un hueco vacio, el manejador lo rellenara con puntuaciones preestablecidas
    public RankingAuxiliar(SharedPreferences preferences){
        this.preferences= preferences;
        for(int i=0; i<posiciones;i++){
            ItemRanking item= new ItemRanking();
            boolean existe=item.GetPreferences(preferences,i);
            if(existe){
                ranking.add(item);
            }
            else{
                HuecoVacio=true;
                break; //Si hay huecos libre ssignifica que es la primera vez que se ejecuta el ranking y debe rellenarse
            }

        }
        if(HuecoVacio){
            GenerarRankingDefault();
        }
    }
    public void nuevaPuntuacion(int puntuacion, String nombre){
        int i=posiciones-1;
        while(i>=0){
            int comparacion= ranking.get(i).getPuntuacion();
            if(comparacion>puntuacion){
                if(i==posiciones-1){
                    return;
                }
                break;
            }
            i--;
        }
        //si ha salido, significa que entra en el ranking;
        ItemRanking item= new ItemRanking();
        item.setNombre(nombre);
        item.setPuntuacion(puntuacion);
        ranking.add(item);
        Collections.sort(ranking,new RankingComparator());
        ranking.remove(posiciones);
        UpdatePreferences();

    }
    private void UpdatePreferences(){
        for(int i=0; i<posiciones;i++){
            ranking.get(i).PutPreferences(preferences,i);
        }
    }
    public void SetText(ArrayList<TextView> punt, ArrayList<TextView> nom){
        for(int i=0; i<posiciones;i++){
            punt.get(i).setText(String.valueOf(ranking.get(i).getPuntuacion()));
            nom.get(i).setText(ranking.get(i).getNombre());
        }
    }
     //Genera un ranking si no se ha creado aún
    public void GenerarRankingDefault(){

        ItemRanking item[]= new ItemRanking[posiciones];
        for(int i=0; i<posiciones;i++){
            item[i]= new ItemRanking();
        }
        item[0].setNombre("Eru,el único");
        item[0].setPuntuacion(PUNTUACION_ERU);

        item[1].setPuntuacion(PUNTUACION_VALAR);
        item[1].setNombre("Varda");

        item[2].setPuntuacion(PUNTUACION_MAIAR);
        item[2].setNombre("Melian");

        item[3].setNombre("Galadriel");
        item[3].setPuntuacion(PUNTUACION_ELFO);

        item[4].setPuntuacion(PUNTUACION_ENANO);
        item[4].setNombre("Durin");

        item[5].setPuntuacion(PUNTUACION_ENT);
        item[5].setNombre("RamaViva");



        for(int i=0;i<posiciones;i++){
            ranking.add(item[i]);
        }
        UpdatePreferences();

    }
}
