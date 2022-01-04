package com.example.lotr_trivial.FragmentosPregunta;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lotr_trivial.Preguntas.Pregunta_Imagen_Texto;
import com.example.lotr_trivial.R;


//Clase del fragmento encargado de gestionar las pantallas donde la pregunta contiene una imagen
public class Imagen_Boton extends FragmentoPregunta {

    //Variables generadas
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    //Referencias
    ImageView imagen;

    public Imagen_Boton() {
        // Required empty public constructor
    }


    //Se gestiona el diseño del fragmento en cuando la vista ya está creada para no tener problemas y poder referenciar
    //los widgets creados
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        pregunta = ((TextView) view.findViewById(R.id.pregunta_2));
        respuestas[0] = ((Button) view.findViewById(R.id.respuesta1_2));
        respuestas[1] = ((Button) view.findViewById(R.id.respuesta2_2));
        respuestas[2] = ((Button) view.findViewById(R.id.respuesta3_2));
        respuestas[3] = ((Button) view.findViewById(R.id.respuesta4_2));

        for (int i = 0; i < 4; i++) {
            respuestas[i].setOnClickListener(this);
        }
        imagen= (ImageView) view.findViewById(R.id.imagenPregunta);

        UpdateGUI();
    }

    //necesita un UpdateGUI personalizado
    public void UpdateGUI() {
        super.UpdateGUIBotones();
        LeerRaw(((Pregunta_Imagen_Texto)item).image,imagen);

    }



    //Métodos generados

    public static Imagen_Boton newInstance(String param1, String param2) {
        Imagen_Boton fragment = new Imagen_Boton();
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
        return inflater.inflate(R.layout.fragment_imagen__boton, container, false);
    }
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}