package com.example.lotr_trivial.FragmentosPregunta;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.lotr_trivial.R;


//Fragmento que gestiona las preguntas con imagenes como respuesta
public class Texto_Imagen extends FragmentoPregunta {

    //Variables generadas
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        pregunta = ((TextView) view.findViewById(R.id.pregunta_5));
        respuestas[0] = ((ImageButton) view.findViewById(R.id.respuesta1_5));
        respuestas[1] = ((ImageButton) view.findViewById(R.id.respuesta2_5));
        respuestas[2] = ((ImageButton) view.findViewById(R.id.respuesta3_5));
        respuestas[3] = ((ImageButton) view.findViewById(R.id.respuesta4_5));
        for(int i=0; i<item.numRespuestas;i++){{
           respuestas[i].setOnClickListener(this);
        }}
        UpdateGUIImagenes();
    }







    //Código generado

    public Texto_Imagen() {
        // Required empty public constructor
    }


    public static Texto_Imagen newInstance(String param1, String param2) {
        Texto_Imagen fragment = new Texto_Imagen();
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
        return inflater.inflate(R.layout.fragment_texto__imagen, container, false);
    }

}