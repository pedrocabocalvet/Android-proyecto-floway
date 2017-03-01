package paisdeyann.floway.Objetos;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by caboc on 28/02/2017.
 */

public class Privado {

    int idConversando;
    String nombreConversando;
    int miId;
    String miNombre;
    String fecha;

    String nombreChat;


    public Privado() {
    }





    public Privado(int idConversando, String nombreConversando, int miId, String miNombre, String fecha, String nombreChat) {
        this.idConversando = idConversando;
        this.nombreConversando = nombreConversando;
        this.miId = miId;
        this.miNombre = miNombre;
        this.fecha = fecha;
        this.nombreChat = nombreChat;


    }

    public String getNombreChat() {
        return nombreChat;
    }

    public void setNombreChat(String nombreChat) {
        this.nombreChat = nombreChat;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getIdConversando() {
        return idConversando;
    }

    public void setIdConversando(int idConversando) {
        this.idConversando = idConversando;
    }

    public String getNombreConversando() {
        return nombreConversando;
    }

    public void setNombreConversando(String nombreConversando) {
        this.nombreConversando = nombreConversando;
    }

    public int getMiId() {
        return miId;
    }

    public void setMiId(int miId) {
        this.miId = miId;
    }

    public String getMiNombre() {
        return miNombre;
    }

    public void setMiNombre(String miNombre) {
        this.miNombre = miNombre;
    }


    public void imprimir(){
        Log.d("prueba10","idConversando "+idConversando + " nombreConversando: "+nombreConversando+" miId: "+miId+" miNombre "+miNombre+" fecha "+fecha+ " nombreChat: "+nombreChat);
    }

}
