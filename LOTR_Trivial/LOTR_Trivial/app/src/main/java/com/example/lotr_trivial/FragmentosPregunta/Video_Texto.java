package com.example.lotr_trivial.FragmentosPregunta;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.VideoView;

import com.example.lotr_trivial.Preguntas.Pregunta_Video_Texto;
import com.example.lotr_trivial.R;


//Fragmento que se encarga de las preguntas con video como pregunta
public class Video_Texto extends FragmentoPregunta {

    int video;
    VideoView videoView;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        pregunta = ((TextView) view.findViewById(R.id.pregunta8));
        respuestas[0] = ((Button) view.findViewById(R.id.respuesta1_8));
        respuestas[1] = ((Button) view.findViewById(R.id.respuesta2_8));
        respuestas[2] = ((Button) view.findViewById(R.id.respuesta3_8));
        respuestas[3] = ((Button) view.findViewById(R.id.respuesta4_8));

        for (int i = 0; i < 4; i++) {
            respuestas[i].setOnClickListener(this);
        }
        videoView= (VideoView) view.findViewById(R.id.videoView);

        UpdateGUI();
    }

    //necesita un UpdateGUI personalizado
    public void UpdateGUI() {
        super.UpdateGUIBotones();

        LeerVideo(((Pregunta_Video_Texto)item).video,videoView);
    }

























    //código generado
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    public Video_Texto() {
        // Required empty public constructor
    }


    public static Video_Texto newInstance(String param1, String param2) {
        Video_Texto fragment = new Video_Texto();
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
        return inflater.inflate(R.layout.fragment_video__texto, container, false);
    }
}