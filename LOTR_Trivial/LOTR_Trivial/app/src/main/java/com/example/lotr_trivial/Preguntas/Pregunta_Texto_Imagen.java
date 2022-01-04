package com.example.lotr_trivial.Preguntas;

import com.example.lotr_trivial.Tools.Enums.DIFICULTAD;
import com.example.lotr_trivial.Tools.Enums.TIPO_PREGUNTA;

import java.util.ArrayList;
import java.util.Collections;

public class Pregunta_Texto_Imagen extends Pregunta {

    //Añade una lista de imagenes con las ids de las respuestas
    public ArrayList<Integer> images;
    public Pregunta_Texto_Imagen(String p, int c, String[] r, int[] i, DIFICULTAD dif) {
        super(p, c, r,false,dif);
        images= new ArrayList<Integer>();
        tipo= TIPO_PREGUNTA.TEXTO_IMAGEN;
        if ((i.length != numRespuestas) ) {
            try {
                throw new Exception("Número de respuestas incorrecto en la creación de una pregunta");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else{
            for(int j=0; j<numRespuestas; j++){
                images.add(i[j]);
            }
            Collections.shuffle(images);
            correcta = images.indexOf(i[c]);
        }

    }
}
