package com.example.lotr_trivial.FragmentosPregunta;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.lotr_trivial.R;


//Fragmento que gestiona las preguntas normales : pregunta y respuestas de texto
public class Texto_Boton extends FragmentoPregunta  {


    //Variables generadas
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        pregunta = ((TextView) view.findViewById(R.id.PreguntaTxtBoton));

        respuestas[0] = ((Button) view.findViewById(R.id.respuesta1_1));
        respuestas[1] = ((Button) view.findViewById(R.id.respuesta2_1));
        respuestas[2] = ((Button) view.findViewById(R.id.respuesta3_1));
        respuestas[3] = ((Button) view.findViewById(R.id.respuesta4_1));


        for (int i = 0; i < 4; i++) {
            respuestas[i].setOnClickListener(this);
        }

        UpdateGUIBotones();
    }



    //Código generado

    public Texto_Boton() {
        // Required empty public constructor

    }

    public static Texto_Boton newInstance(String param1, String param2) {
        Texto_Boton fragment = new Texto_Boton();
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
        return inflater.inflate(R.layout.fragment_texto__boton, container, false);
    }

    public interface OnFragmentInteractionListener {

        void onFragmentInteraction(Uri uri);
    }

}