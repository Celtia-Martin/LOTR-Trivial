package com.example.lotr_trivial.FragmentosPregunta;

import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.lotr_trivial.Preguntas.Pregunta_Audio_Texto;
import com.example.lotr_trivial.R;

//Fragmento que se encarga de las preguntas con audio como pregunta

public class Audio_Texto extends FragmentoPregunta {

    //Variables generadas
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    //variables
    ImageButton play, stop;
    MediaPlayer mediaplayer;
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        pregunta = ((TextView) view.findViewById(R.id.pregunta_7));

        respuestas[0] = ((Button) view.findViewById(R.id.respuesta1_7));
        respuestas[1] = ((Button) view.findViewById(R.id.respuesta2_7));
        respuestas[2] = ((Button) view.findViewById(R.id.respuesta3_7));
        respuestas[3] = ((Button) view.findViewById(R.id.respuesta4_7));

        int id = ((Pregunta_Audio_Texto)item).audio;
        mediaplayer = MediaPlayer.create(getActivity(),id);
        play = ((ImageButton) view.findViewById(R.id.playmusic));
        stop = ((ImageButton) view.findViewById(R.id.pausemusic));

        for (int i = 0; i < 4; i++) {
            respuestas[i].setOnClickListener(this);
        }

        play.setOnClickListener(this);
        stop.setOnClickListener(this);

        UpdateGUIBotones();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.playmusic:
                mediaplayer.start();
                break;
            case R.id.pausemusic:
                mediaplayer.pause();
                break;

            default:
                super.onClick(v);
                mediaplayer.stop();
                break;
        }

    }

    //Codigo generado
    public Audio_Texto() {
        // Required empty public constructor
    }

    public static Audio_Texto newInstance(String param1, String param2) {
        Audio_Texto fragment = new Audio_Texto();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_audio__texto, container, false);
    }
}