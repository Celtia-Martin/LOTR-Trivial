package com.example.lotr_trivial;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;


import com.example.lotr_trivial.Tools.Auxiliares.Ranking.RankingAuxiliar;

import java.util.ArrayList;

import static com.example.lotr_trivial.Configuracion.PREFS_NAME;

//Actividad que muestra las 6 mejores puntuaciones
public class Ranking extends AppCompatActivity {

    ArrayList<TextView> nombres;
    ArrayList<TextView> puntuaciones;
    ImageButton volver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);
        rellenarArraylists();
        Inicializar();

    }

    private void Inicializar(){
        SharedPreferences preferences= getSharedPreferences(PREFS_NAME,0);
        RankingAuxiliar rankingAuxiliar= new RankingAuxiliar(preferences);
        rankingAuxiliar.SetText(puntuaciones,nombres);
        volver= (ImageButton)findViewById(R.id.volverRanking);
        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(v.getContext(), Menu.class);
                startActivity(intent);
            }
        });
    }
    private void rellenarArraylists(){
        nombres= new ArrayList<>();
        puntuaciones= new ArrayList<>();
        nombres.add((TextView)findViewById(R.id.PuntNom1));
        nombres.add((TextView)findViewById(R.id.PuntNom2));
        nombres.add((TextView)findViewById(R.id.PuntNom3));
        nombres.add((TextView)findViewById(R.id.PuntNom4));
        nombres.add((TextView)findViewById(R.id.PuntNom5));
        nombres.add((TextView)findViewById(R.id.PuntNom6));

        puntuaciones.add((TextView)findViewById(R.id.Punt1));
        puntuaciones.add((TextView)findViewById(R.id.Punt2));
        puntuaciones.add((TextView)findViewById(R.id.Punt3));
        puntuaciones.add((TextView)findViewById(R.id.Punt4));
        puntuaciones.add((TextView)findViewById(R.id.Punt5));
        puntuaciones.add((TextView)findViewById(R.id.Punt6));
    }
}