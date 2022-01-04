package com.example.lotr_trivial.Preguntas;

import com.example.lotr_trivial.Tools.Enums.DIFICULTAD;
import com.example.lotr_trivial.Tools.Enums.TIPO_PREGUNTA;

public class Pregunta_Audio_Texto extends Pregunta {

    public int audio;
    public Pregunta_Audio_Texto(String p, int c, String[] r, boolean shuffle, int idAudio, DIFICULTAD dif) {
        super(p, c, r, shuffle,dif);
        audio= idAudio;
        tipo= TIPO_PREGUNTA.AUDIO_TEXTO;
    }
}
