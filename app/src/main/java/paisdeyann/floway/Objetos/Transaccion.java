package paisdeyann.floway.Objetos;

import android.widget.ImageView;

/**
 * Created by Dario on 19/02/2017.
 */

public class Transaccion {
    String nombre;
    int precio;
    String conductor;

    public Transaccion(){}

    public Transaccion(String nombre, int precio, String conductor) {
        this.nombre = nombre;
        this.precio = precio;
        this.conductor = conductor;
    }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public String getConductor() {
        return conductor;
    }

    public void setConductor(String conductor) {
        this.conductor = conductor;
    }
}
