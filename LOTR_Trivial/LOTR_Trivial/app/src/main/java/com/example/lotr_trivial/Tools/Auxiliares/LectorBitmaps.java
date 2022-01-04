package com.example.lotr_trivial.Tools.Auxiliares;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

//clase auxiliar que gestiona la lectura, eliminación y escritura de las fotos de perfil
public class LectorBitmaps {//hacer close

    private final String path="ImagenesPerfil";

    public boolean EliminarBitmap(String nombre, Context context){
        File path= getPath(nombre,context);
        return path.delete();

    }

    public Bitmap LeerBitmap(String nombre,Context context)  {
        File path= getPath(nombre,context);
        FileInputStream file=null;
        Bitmap bm= null;
        try {
            file= new FileInputStream(path);
            bm=BitmapFactory.decodeStream(file);
            try {
                file.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        finally {
            return bm;
        }
    }
    public void EscribirBitmap(Bitmap bm, Context context, String nombre){

      File path= getPath(nombre,context);
        FileOutputStream fos= null;
        try{
            fos= new FileOutputStream(path);
            bm.compress(Bitmap.CompressFormat.PNG,100,fos);
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            try{
                fos.close();
            }catch (IOException e){
                e.printStackTrace();
            }

        }
    }
    private File getPath(String nombre, Context context){
        ContextWrapper cw= new ContextWrapper(context);
        String nombreArchivo= nombre+".jpg";
        File directorio= cw.getDir(path,context.MODE_PRIVATE);
        File path= new File(directorio,nombreArchivo);
        return path;
    }
}
