package com.example.lotr_trivial.Preguntas;

import com.example.lotr_trivial.Tools.Enums.DIFICULTAD;
import com.example.lotr_trivial.Tools.Enums.TIPO_PREGUNTA;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
//Clase auxiliar que almacena información de las preguntas. Cuenta con tres hijos diferentes, que se diferencian
//en la informacion almacenada: texto de pregunta y texto de respuestas; texto de pregunta e imagenes de respuestas; texto e imagen
//de pregunta y texto de respuesta
public class Pregunta implements Serializable {
        //Variables
        public TIPO_PREGUNTA tipo;
        public  String pregunta ;
        public ArrayList<String> respuestas ;
        public int correcta=0;
        public final int numRespuestas= 4;
        public DIFICULTAD dificultad= DIFICULTAD.FACIL;

        //Constructor, donde también está la posibilidad de desordenar las respuestas
    public Pregunta(  String p,int c,String[] r, boolean shuffle, DIFICULTAD dif)  {
        dificultad= dif;
        respuestas = new ArrayList<>();
        pregunta = p;
        if(r!=null){
            if ((r.length != numRespuestas) || (correcta > numRespuestas-1) || (correcta < 0)) {
                try {
                    throw  new Exception("Número de respuestas incorrecto en la creación de una pregunta");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                for (int i = 0; i < numRespuestas; i++) {
                    respuestas.add(r[i]);
                }
                if(shuffle){
                    Collections.shuffle(respuestas);
                }
                correcta = respuestas.indexOf(r[c]);
            }
        }
       else{
           correcta= c;
        }
    }

    public boolean Responder( int respuesta){
        return respuesta==correcta;
    }
    public void SetPregunta(TIPO_PREGUNTA t){
        tipo= t;
        }


}
