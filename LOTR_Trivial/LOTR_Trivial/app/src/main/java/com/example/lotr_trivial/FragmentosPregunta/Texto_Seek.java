package com.example.lotr_trivial.FragmentosPregunta;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.lotr_trivial.MainActivity;
import com.example.lotr_trivial.R;


//Este tipo de fragmento necesita mucho más código específico que sus hermanos ya que su funcionamiento es
//bastante diferente
public class Texto_Seek extends FragmentoPregunta {

   //Variables generadas
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;



    //Variables específicas
    private SeekBar bar;
    private TextView answer;

    //Un nuevo UpdateGUI Especifico, donde también manejamos el seekbar y qué va a hacer cuando se seleccione un valor
    public void UpdateGUISeek(){
        answer.setText(String.valueOf(item.respuestas.get(0)));
        pregunta.setText(item.pregunta);

        bar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(progress<25){
                    answer.setText(String.valueOf(item.respuestas.get(0)));
                    cual=0;
                }
                else if(progress<50){
                    answer.setText(String.valueOf(item.respuestas.get(1)));
                   cual=1;
                }
                else if(progress<75){
                    answer.setText(String.valueOf(item.respuestas.get(2)));
                   cual=2;
                }else{
                    answer.setText(String.valueOf(item.respuestas.get(3)));
                    cual=3;
                }
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if(item.Responder(cual)){
                    answer.setBackgroundColor(Color.parseColor(Coloracertado));
                    ((MainActivity) getActivity()).puntos++;
                }
                else{
                    answer.setBackgroundColor(Color.parseColor(Colorfallado));
                }bar.setEnabled(false);

            lanzarTimer();
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        pregunta= (TextView) view.findViewById(R.id.pregunta_6);
        bar= (SeekBar) view.findViewById(R.id.bar_respuesta);
        answer= (TextView) view.findViewById(R.id.respuesta_seek);
        UpdateGUISeek();
    }





    //Código generado
    public Texto_Seek() {
        // Required empty public constructor
    }

    public static Texto_Seek newInstance(String param1, String param2) {
        Texto_Seek fragment = new Texto_Seek();
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
        return inflater.inflate(R.layout.fragment_texto__seek, container, false);
    }

}

