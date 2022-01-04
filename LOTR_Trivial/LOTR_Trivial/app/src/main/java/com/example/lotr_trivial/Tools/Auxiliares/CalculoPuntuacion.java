package com.example.lotr_trivial.Tools.Auxiliares;

import com.example.lotr_trivial.Tools.Enums.DIFICULTAD;

//Clase que se encarga del cálculo de la puntuación final
public class CalculoPuntuacion {


    int puntosPregunta= 100;
    //Segundos aprox por pregunta
    int sPregunta= 7;
    //plus por difultad
    int[] plusDif={1,2,3};
    int puntos;
    int tiempoEstimado;
    int puntosBonus=20;
    public int calcularPuntuacion(int tiempo, int npreguntas, int ncorrectas, DIFICULTAD dificultad){
        int multiplicador= plusDif[dificultad.ordinal()];
        puntos= ncorrectas*multiplicador*puntosPregunta;
        tiempoEstimado= npreguntas*sPregunta;
        if((tiempoEstimado>tiempo)&&(puntos!=0)){
            puntos+= (tiempoEstimado-tiempo)*puntosBonus;
            if(puntos<0){
                puntos=0;
            }
        }
        return puntos;
    }
}
