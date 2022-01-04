package com.example.lotr_trivial.FragmentosPregunta;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.lotr_trivial.MainActivity;
import com.example.lotr_trivial.R;


//Fragmento encargado de gestionar las preguntas con spinner
public class Texto_Spinner extends FragmentoPregunta {

    //Variables generadas
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    //Variables específicas
    Spinner spinner;
    Button elegir;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        spinner= view.findViewById(R.id.spinner_respuestas);
        pregunta= view.findViewById(R.id.pregunta_4);
        spinner.setAdapter(new ArrayAdapter<String>(getActivity().getApplicationContext(),android.R.layout.simple_spinner_dropdown_item,item.respuestas));
        elegir= view.findViewById(R.id.elegir);
        elegir.setOnClickListener(this);
        pregunta.setText(item.pregunta);
    }

    //No se usa el onClick del padre, se sobreescribe para llevarlo al caso específico
    @Override
    public void onClick(View v) {
        elegir.setClickable(false);
      if(item.Responder(spinner.getSelectedItemPosition())){

          spinner.setBackgroundColor(Color.parseColor(Coloracertado));
          ((MainActivity) getActivity()).puntos++;
        }else{
          spinner.setBackgroundColor(Color.parseColor(Colorfallado));
      }
      lanzarTimer();


    }


    //Código generado

    public Texto_Spinner() {
        // Required empty public constructor
    }


    public static Texto_Spinner newInstance(String param1, String param2) {
        Texto_Spinner fragment = new Texto_Spinner();
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
        return inflater.inflate(R.layout.fragment_texto__spinner, container, false);
    }

}