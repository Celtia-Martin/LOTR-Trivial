package com.example.lotr_trivial.Tools.Perfiles;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lotr_trivial.R;
import com.example.lotr_trivial.Tools.Auxiliares.LectorBitmaps;

import java.util.ArrayList;

//Adaptador de RecyclerView que gestiona el visionado de los perfiles en la activity perfil
public class AdaptadorPerfil extends RecyclerView.Adapter<AdaptadorPerfil.ViewHolderPerfiles>
{

    ArrayList<PerfilObject> listaPerfiles;
    Context context;

    public AdaptadorPerfil(ArrayList<PerfilObject> lista){

        listaPerfiles=lista;
    }
    public void addPerfil(PerfilObject nuevo){
        listaPerfiles.add(nuevo);
    }
    @NonNull
    @Override
    public ViewHolderPerfiles onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_perfiles,null,false);
       context= parent.getContext();
        return new ViewHolderPerfiles(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderPerfiles holder, int position) {

        holder.nombre.setText(listaPerfiles.get(position).getNombre());
        holder.numeroPartidas.setText("Nº partidas: "+String.valueOf(listaPerfiles.get(position).getnPartidas()));
        holder.puntos.setText("Maxima puntuacion: "+String.valueOf(listaPerfiles.get(position).getMaxPuntuacion()));
        holder.data.setText("Ultima partida: "+ listaPerfiles.get(position).getUltimaPartida().toString());
        holder.raza.setText("Raza: "+ listaPerfiles.get(position).getRaza().toString());
        LectorBitmaps lb= new LectorBitmaps();
        Bitmap foto= lb.LeerBitmap(listaPerfiles.get(position).getNombre(),context);
        if(foto==null){
            holder.foto.setImageResource(R.drawable.default_perfil);//esto es drawable ya vereos como lo ponemos al final
        }else{
            holder.foto.setImageBitmap(foto);
        }

    }

    @Override
    public int getItemCount() {
        return listaPerfiles.size();
    }

    public class ViewHolderPerfiles extends RecyclerView.ViewHolder {
        TextView nombre, numeroPartidas,puntos,data,raza;
        ImageView foto;
        public ViewHolderPerfiles(@NonNull View itemView) {
            super(itemView);
            nombre= (TextView) itemView.findViewById(R.id.nombre_perfil);
            puntos= (TextView) itemView.findViewById(R.id.maxpuntuacion);
            data= (TextView) itemView.findViewById(R.id.dataPartida);
            numeroPartidas= (TextView) itemView.findViewById(R.id.npartidas);
            foto= (ImageView) itemView.findViewById(R.id.foto_perfil);
            raza= (TextView) itemView.findViewById(R.id.razaText);
        }
    }
}
