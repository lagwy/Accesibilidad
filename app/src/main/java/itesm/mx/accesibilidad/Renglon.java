package itesm.mx.accesibilidad;

import android.graphics.Bitmap;

/**
 * Created by Luis Ángel on 11/11/2015.
 */
public class Renglon {
    private String nombre;
    private Bitmap imagen;

    public Renglon(){

    }

    public Renglon(String nombre, Bitmap imagen){
        this.nombre = nombre;
        this.imagen = imagen;
    }

    public String getNombre(){
        return nombre;
    }

    public void setNombre(String nombre){
        this.nombre = nombre;
    }

    public Bitmap getImagen(){
        return imagen;
    }

    public void setImagen(Bitmap imagen){
        this.imagen = imagen;
    }
}
