package com.example.lotr_trivial.Preguntas;

import com.example.lotr_trivial.Tools.Enums.DIFICULTAD;
import com.example.lotr_trivial.Tools.Enums.TIPO_PREGUNTA;

public class Pregunta_Imagen_Texto extends Pregunta {

    //Añade una variable, referencia al id de la imagen pregunta
    public int image;
    public Pregunta_Imagen_Texto(String p, int c, String[] r, int i, DIFICULTAD dif) {
        super(p, c, r,true,dif);
        image=i;
        tipo= TIPO_PREGUNTA.IMAGEN_TEXTO;
    }

}
