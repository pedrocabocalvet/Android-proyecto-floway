package paisdeyann.floway.Threads;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

import paisdeyann.floway.Adaptador_Puntuaciones;
import paisdeyann.floway.Conexion.Conexion;
import paisdeyann.floway.Objetos.Publicacion;
import paisdeyann.floway.Objetos.Usuario;
import paisdeyann.floway.Puntuacion;

/**
 * Created by caboc on 19/02/2017.
 */

public class CogerPuntuacionesDeUsuarios extends AsyncTask<Object,Object,Object>{

    Context contexto;
    String jason;

    ArrayList<Usuario> usuarios = new ArrayList<Usuario>();



    @Override
    protected Object doInBackground(Object... params) {

        //0 contexto
        //1 recyclerview

        contexto = (Context)params[0];




        if(comprobarConexion()){

            jason = conseguirJason();
            parsearJason(jason);

        }

        publishProgress(params);


        return params;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
    }

    @Override
    protected void onProgressUpdate(Object... values) {
        super.onProgressUpdate(values);

        //0 contexto
        //1 recyclerview

        ArrayList<Puntuacion> puntuaciones = new ArrayList<Puntuacion>();

        for (Usuario u:usuarios) {
            // String nombre, String apellidos, String puntuacion, String foto
            //((ArrayList<Puntuacion>)values[1]).add(new Puntuacion(u.getNombre(),u.getApellidos(),""+u.getPuntuacion(),u.getFoto()));
            puntuaciones.add(new Puntuacion(u.getNombre(),u.getApellidos(),u.getPuntuacion(),u.getFoto()));

        }

        Collections.sort(puntuaciones);

        Adaptador_Puntuaciones adaptador;
        adaptador = new Adaptador_Puntuaciones(puntuaciones);

        ((RecyclerView)values[1]).setAdapter(adaptador);



    }

    @Override
    protected void onCancelled(Object o) {
        super.onCancelled(o);
    }


    public void parsearJason(String jason){

        Usuario usuario = null;

        Gson gson = new Gson(); // usaremos esto para pasear el jason
        JsonParser parseador = new JsonParser();    // necesitamos este objeto para conseguir el elemento raiz del String


        JsonElement raiz = parseador.parse(jason); // conseguimos el elemnto raiz del string
        JsonArray lista = raiz.getAsJsonArray();    // el elemento raiz es un array de objetos jason por eso nos creamos un objeto JsonArray y lo cogemos con este metodo

        for (JsonElement elemento : lista) {    // recorremos el JsonArray lista con este for each abreviado y nos creamos un nuevo usuario
            usuario = gson.fromJson(elemento, Usuario.class);
            usuarios.add(usuario);
        }

    }



    public boolean comprobarConexion(){

        boolean respuesta = false;

        ConnectivityManager connMgr = (ConnectivityManager) contexto.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {

            respuesta = true;

        }

        return respuesta;
    }


    public String conseguirJason(){

        String respuesta = null;

        String cadenaConexion = Conexion.SERVER+"/APIFLOWAY-PHP/apiNueva.php?"+Conexion.APIKEY;

        URL url = null;
        HttpURLConnection conn;

        try {

            url = new URL(cadenaConexion);
            conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);

            conn.connect();
            int response = conn.getResponseCode();		// código devuelto por web Service
            InputStream is = conn.getInputStream();

            respuesta = transformarInputStremString(is);
            Log.d("prueba","String id por usuario "+respuesta);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return respuesta;
    }


    private String transformarInputStremString(InputStream is) {

        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));

        String linea = "";

        try {
            while((linea = reader.readLine()) != null){
                stringBuilder.append(linea);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            reader.close();
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return stringBuilder.toString();
    }
}
