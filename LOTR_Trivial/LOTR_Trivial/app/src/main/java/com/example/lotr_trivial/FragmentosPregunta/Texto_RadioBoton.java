package com.example.lotr_trivial.FragmentosPregunta;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.lotr_trivial.R;


//Este fragmento gestiona las preguntas con radiobuttons como respuesta
public class Texto_RadioBoton extends FragmentoPregunta {

    //Variables generadas
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        pregunta = ((TextView) view.findViewById(R.id.pregunta_3));
        //RadioButton hereda de Button, por tanto hay poca diferencia con el script "TextoBoton",
        //simplemente cambiamos el id de las respuestas
        respuestas[0] = ((Button) view.findViewById(R.id.respuesta1_3));
        respuestas[1] = ((Button) view.findViewById(R.id.respuesta2_3));
        respuestas[2] = ((Button) view.findViewById(R.id.respuesta3_3));
        respuestas[3] = ((Button) view.findViewById(R.id.respuesta4_3));


        for (int i = 0; i < 4; i++) {
            respuestas[i].setOnClickListener(this);
        }

        UpdateGUIBotones();
    }



    //Codigo generado
    public Texto_RadioBoton() {
        // Required empty public constructor
    }


    public static Texto_RadioBoton newInstance(String param1, String param2) {
        Texto_RadioBoton fragment = new Texto_RadioBoton();
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
        return inflater.inflate(R.layout.fragment_texto__radio_boton, container, false);
    }

}