package paisdeyann.floway;

/**
 * Created by Aitana on 17/02/2017.
 */

public class Puntuacion implements Comparable{

    String nombre;
    String apellidos;
    int puntuacion;
    String foto;



    public Puntuacion(String nombre, String apellidos, int puntuacion, String foto){
        this.nombre = nombre;
        this.puntuacion = puntuacion;
        this.apellidos = apellidos;
        this.foto = foto;

    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public int getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(int puntuacion) {
        this.puntuacion = puntuacion;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }


    @Override
    public int compareTo(Object o) {
        return (((Puntuacion)o).getPuntuacion()-getPuntuacion());
    }
}
