package com.example.lotr_trivial.Preguntas;
import com.example.lotr_trivial.Tools.Enums.DIFICULTAD;
import com.example.lotr_trivial.Tools.Enums.TIPO_PREGUNTA;

public class Pregunta_Texto_Texto extends Pregunta {

        //No varía de su padre, más allá de especificar su tipo
        public Pregunta_Texto_Texto(String p,int c, String[] r , boolean shuffle, DIFICULTAD dif) {
                super(p, c,r,shuffle,dif);
                tipo= TIPO_PREGUNTA.TEXTO_TEXTO;
        }



}
