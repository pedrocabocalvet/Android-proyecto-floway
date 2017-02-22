package paisdeyann.floway.FragmentsTabs;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

import paisdeyann.floway.AdaptadorGrupoMensajes;
import paisdeyann.floway.Conexion.Conexion;
import paisdeyann.floway.Menu_Principal;
import paisdeyann.floway.Objetos.Conversacion;
import paisdeyann.floway.Objetos.Publicacion;
import paisdeyann.floway.Objetos.Transaccion;
import paisdeyann.floway.R;
import paisdeyann.floway.Threads.AdaptadorTransacciones;


public class PantallaTransacciones extends Fragment {

    Menu_Principal activity;
    Context context;

    RecyclerView rv;
    RecyclerView.LayoutManager rvLM;
    AdaptadorTransacciones adaptador;

    private FirebaseAuth firebaseAuth2;
    private Firebase mFirebase2;
    private String mId2;


    ArrayList<Transaccion> transacciones = new ArrayList<Transaccion>();


    public PantallaTransacciones() {
        // Required empty public constructor
    }
    public void setContext(Context cont,Menu_Principal act){
        activity=act;
        context=cont;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_pantalla_transacciones, container, false);

        Log.d("prueba5","llego a transacciones");
        mId2 = Settings.Secure.getString(context.getContentResolver(),Settings.Secure.ANDROID_ID);
        firebaseAuth2 = FirebaseAuth.getInstance();
        Firebase.setAndroidContext(context);
        mFirebase2 = new Firebase("https://flowaychatviajes.firebaseio.com").child("Transacciones/"+Conexion.usuarioActivo.getId_usuario());

       // transacciones.add(new Transaccion("pasajero",3,"lupe"));


        rv = (RecyclerView) rootView.findViewById(R.id.recyclerViewTransacciones);
        rvLM = new LinearLayoutManager(context);
        rv.setLayoutManager(rvLM);

        adaptador = new AdaptadorTransacciones(transacciones);
        rv.setAdapter(adaptador);

        mFirebase2.addChildEventListener(new ChildEventListener() {

            @Override

            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.d("prueba5","llego al listener de publicaciones para bajarla");
                if (dataSnapshot != null && dataSnapshot.getValue() != null){
                    Log.d("prueba5","entro aki por lo menos");
                    try {
                        Log.d("prueba5","entro en el meollo de la cuestion");
                        Transaccion transaccion = dataSnapshot.getValue(Transaccion.class);
                        Log.d("prueba5","entro");
                        transacciones.add(transaccion);
                        rv.scrollToPosition(transacciones.size()-1);
                       adaptador.notifyItemInserted(transacciones.size()-1);

                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });




        return rootView;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }
}
