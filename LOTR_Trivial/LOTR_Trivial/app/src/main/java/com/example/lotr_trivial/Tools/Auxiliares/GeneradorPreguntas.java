package com.example.lotr_trivial.Tools.Auxiliares;

import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;

import com.example.lotr_trivial.BD.BDManager;
import com.example.lotr_trivial.R;
import com.example.lotr_trivial.Tools.Enums.DIFICULTAD;
import com.example.lotr_trivial.Tools.Enums.TIPO_PREGUNTA;
import com.example.lotr_trivial.Preguntas.Pregunta;
import com.example.lotr_trivial.Preguntas.Pregunta_Audio_Texto;
import com.example.lotr_trivial.Preguntas.Pregunta_Imagen_Texto;
import com.example.lotr_trivial.Preguntas.Pregunta_Texto_Imagen;
import com.example.lotr_trivial.Preguntas.Pregunta_Texto_Texto;
import com.example.lotr_trivial.Preguntas.Pregunta_Video_Texto;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
//todo HACER METODO QUE LEYENDO LA BD CREE EL ARCHIVO DE BASE DE DATOS


//Método auxiliar que genera una base de datos con preguntas. En la primera entrega, las
//preguntas primero fueron generadas "a lo bruto". Ahora contamos con un fichero que leemos si la Base de Datos
// aun no se ha creado. Si está creada, lee directamente de la base de datos.


public class GeneradorPreguntas {

    ArrayList<Pregunta> BD;
    Context context;
    public GeneradorPreguntas(Context c){
        context=c;
        BD= new ArrayList<>();
    }
    //solo es llamado si la base de datos de preguntas está vacía
    private void GenerarPreguntas(){
        Resources r= context.getResources();
                InputStream i= r.openRawResource(R.raw.ficheropreguntas);
        try {
            ObjectInputStream objectInput= new ObjectInputStream(i);


               Pregunta nueva= (Pregunta)objectInput.readObject();
               while(nueva!=null){
                   BD.add(nueva);
                   nueva= (Pregunta)objectInput.readObject();
               }
                objectInput.close();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    //Método que coge x preguntas de la base de datos, las desordena, y devuelve el resultado.
    //Se invoca cuando la Base de Datos está vacía, y lee de un fichero que está en raw con toda la informacion
    //de las preguntas.
    public void transformarPreguntas(BDManager bdManager){
        bdManager.deletePreguntas();
        GenerarPreguntas();
        for(int i=0; i<BD.size();i++){
            Pregunta current= BD.get(i);
           int aux[]={0,0,0,0};
            switch(current.tipo){
                case IMAGEN_TEXTO:
                   aux= new int[] {((Pregunta_Imagen_Texto)current).image,0,0,0};

                    break;
                case TEXTO_IMAGEN:
                   ArrayList<Integer> images= ((Pregunta_Texto_Imagen)current).images;
                    aux=  new int[] {images.get(0),images.get(1),images.get(2),images.get(3)};

                    break;

                case AUDIO_TEXTO:
                    aux= new int[] {((Pregunta_Audio_Texto)current).audio,0,0,0};

                    break;
                case VIDEO_TEXTO:
                    aux= new int[] {((Pregunta_Video_Texto)current).video,0,0,0};

                    break;

                    default:
                        break;
            }
            String[] respuestas={"","","",""};
            if(!current.tipo.equals(TIPO_PREGUNTA.TEXTO_IMAGEN)){
               respuestas= new String[] {current.respuestas.get(0),current.respuestas.get(1),current.respuestas.get(2),current.respuestas.get(3)};
            }

            bdManager.insertEntry(current.pregunta,current.tipo,respuestas,current.correcta,aux,current.dificultad);

        }
    }

    //Con la base de datos ya creada, se lee y recoge su información sobre las preguntas en base a un cursor
    public ArrayList<Pregunta> LeerBD(Cursor c){
        Pregunta nueva=null;
        ArrayList<Pregunta> resultado= new ArrayList<Pregunta>();
        if(c.moveToFirst()){

            do{
                TIPO_PREGUNTA tipo= TIPO_PREGUNTA.valueOf( c.getString(c.getColumnIndex("tipo")));
                DIFICULTAD dif= DIFICULTAD.valueOf((c.getString(c.getColumnIndex("dificultad"))));
                String[] respuestas={c.getString(c.getColumnIndex("respuesta1")),
                        c.getString(c.getColumnIndex("respuesta2")),
                        c.getString(c.getColumnIndex("respuesta3")),
                        c.getString(c.getColumnIndex("respuesta4"))};
                        String pregunta=c.getString(c.getColumnIndex("pregunta"));

                int correcta=c.getInt(c.getColumnIndex("correcta"));
                int[] multimedia={c.getInt(c.getColumnIndex("idmultimedia1")),
                        c.getInt(c.getColumnIndex("idmultimedia2")),
                        c.getInt(c.getColumnIndex("idmultimedia3")),
                        c.getInt(c.getColumnIndex("idmultimedia4"))};

            switch (tipo){
                case IMAGEN_TEXTO:
                    nueva= new Pregunta_Imagen_Texto(pregunta,correcta,respuestas,multimedia[0],dif);
                    break;

                case TEXTO_IMAGEN:
                    nueva= new Pregunta_Texto_Imagen(pregunta,correcta,respuestas,multimedia,dif);
                    break;

                case AUDIO_TEXTO:
                    nueva= new Pregunta_Audio_Texto(pregunta,correcta,respuestas,true,multimedia[0],dif);
                    break;
                case VIDEO_TEXTO:
                    nueva= new Pregunta_Video_Texto(pregunta,correcta,respuestas,true,multimedia[0],dif);
                    break;
                default:
                    nueva= new Pregunta_Texto_Texto(pregunta,correcta,respuestas,!tipo.equals(TIPO_PREGUNTA.TEXTO_SEEK),dif);
                    nueva.SetPregunta(tipo);

                    break;

            }
                resultado.add(nueva);
            }while(c.moveToNext());
        }
        c.close();
        Collections.shuffle(resultado);
        return resultado;
    }

    public List<Pregunta> getPreguntas(int howmany,BDManager bdManager, DIFICULTAD dif){

        Cursor preguntas= bdManager.getEntries(howmany,dif);
        return LeerBD(preguntas);

    }

    //Código utilizado para la generación del fichero de preguntas
    /*
    public void escribirPreguntas(){
        FileOutputStream fos= null;
        DataOutputStream salida= null;

            File path= getPath("ficheropreguntas.obj",context);
        try {
            fos= new FileOutputStream(path);
            ObjectOutputStream oos= new ObjectOutputStream(fos);
            for(int i=0; i<BD.size();i++) {
                oos.writeObject(BD.get(i));
            }
            oos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    private File getPath(String nombre, Context context){
        ContextWrapper cw= new ContextWrapper(context);
        String nombreArchivo= nombre;
        File directorio= cw.getDir("CarpetaAuxiliar",context.MODE_PRIVATE);
        File path= new File(directorio,nombreArchivo);
        return path;
    }
*/


}
