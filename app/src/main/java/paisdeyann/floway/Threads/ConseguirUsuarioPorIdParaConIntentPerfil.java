package paisdeyann.floway.Threads;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import paisdeyann.floway.Conexion.Conexion;
import paisdeyann.floway.Objetos.Usuario;
import paisdeyann.floway.Perfil;

/**
 * Created by caboc on 19/02/2017.
 */

public class ConseguirUsuarioPorIdParaConIntentPerfil extends AsyncTask<Object,Object,Object> {

    Context contexto;
    int usuarioComprobar;
    String jason;
    Usuario usuario;


    @Override
    protected Object doInBackground(Object... params) {

        // params[0]  contexto
        // params[1] id usuaria coger

        Log.d("prueba","entro al thread");

        contexto = (Context) params[0];
        usuarioComprobar = (int)params[1];


        if(comprobarConexion()){
            Log.d("prueba","tengo conexion");
            jason = conseguirJason();

            usuario = parsearJason(jason);

        }


        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);


        Intent intent = new Intent(contexto,Perfil.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("usuario",(Serializable)usuario);

        intent.putExtra("bundle",bundle);
        contexto.startActivity(intent);


    }

    @Override
    protected void onProgressUpdate(Object... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onCancelled(Object o) {
        super.onCancelled(o);
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

        String cadenaConexion = Conexion.SERVER+"/APIFLOWAY-PHP/apiNueva.php?whereid="+usuarioComprobar+"&"+Conexion.APIKEY;

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


    public Usuario parsearJason(String jason){

        Usuario usuario = null;

        Gson gson = new Gson(); // usaremos esto para pasear el jason
        JsonParser parseador = new JsonParser();    // necesitamos este objeto para conseguir el elemento raiz del String


        JsonElement raiz = parseador.parse(jason); // conseguimos el elemnto raiz del string
        JsonArray lista = raiz.getAsJsonArray();    // el elemento raiz es un array de objetos jason por eso nos creamos un objeto JsonArray y lo cogemos con este metodo

        for (JsonElement elemento : lista) {    // recorremos el JsonArray lista con este for each abreviado y nos creamos un nuevo usuario
            usuario = gson.fromJson(elemento, Usuario.class);
            Log.d("prueba",usuario.getNombre());
        }

        //Log.d("prueba","usuario conseguido por id");
        //usuario.imprimir();

        return usuario;
    }

}
