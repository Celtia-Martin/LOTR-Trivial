package com.example.lotr_trivial.Preguntas;

import com.example.lotr_trivial.Tools.Enums.DIFICULTAD;
import com.example.lotr_trivial.Tools.Enums.TIPO_PREGUNTA;

public class Pregunta_Video_Texto extends Pregunta{
    public int video;
    public Pregunta_Video_Texto(String p, int c, String[] r, boolean shuffle, int idVideo, DIFICULTAD dif) {
        super(p, c, r, shuffle,dif);
        video= idVideo;
        tipo= TIPO_PREGUNTA.VIDEO_TEXTO;
    }
}
