package com.example.lotr_trivial.Tools.Perfiles;

import com.example.lotr_trivial.Tools.Enums.RAZA;

//Clase con la información de la que dispone un perfil
public class PerfilObject {
    private  String nombre;
    private  int MaxPuntuacion;
    private  int nPartidas;
    private  String ultimaPartida;
    private String contraseña;
    private RAZA raza;

    public PerfilObject(String n){
        nombre= n;
        contraseña="";
        nPartidas=0;
        MaxPuntuacion=0;
        ultimaPartida= "Nunca";
        raza= RAZA.HOBBIT;
    }
    public PerfilObject(String n,String contra){

        contraseña=contra;
        nombre=n;
        nPartidas=0;
        MaxPuntuacion=0;
        ultimaPartida= "Nunca";
        raza= RAZA.HOBBIT;


    }
    public PerfilObject(String nom, String data, int maxPuntuacion,int npartidas,String contra,RAZA ra){

        nombre=nom;
        nPartidas=npartidas;
        MaxPuntuacion=maxPuntuacion;
        ultimaPartida= data;
        contraseña= contra;
        raza= ra;

    }

    public RAZA getRaza() {
        return raza;
    }

    public void setRaza(RAZA raza) {
        this.raza = raza;
    }

    public boolean ContraseñaCorrecta(String input){
        return contraseña.equals(input);
    }

    public void nuevaPuntuacion( int puntos){
        if(puntos>MaxPuntuacion){
            MaxPuntuacion= puntos;
        }
    }

    public void addPartida(){
        nPartidas++;
    }


    public String getNombre() {
        return nombre;
    }

    public int getMaxPuntuacion() {
        return MaxPuntuacion;
    }

    public int getnPartidas() {
        return nPartidas;
    }

    public String getUltimaPartida() {
        return ultimaPartida;
    }



    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setMaxPuntuacion(int maxPuntuacion) {
        MaxPuntuacion = maxPuntuacion;
    }

    public void setnPartidas(int nPartidas) {
        this.nPartidas = nPartidas;
    }

    public void setUltimaPartida(String ultimaPartida) {
        this.ultimaPartida = ultimaPartida;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PerfilObject that = (PerfilObject) o;
        return nombre.equals(that.nombre);
    }


}
