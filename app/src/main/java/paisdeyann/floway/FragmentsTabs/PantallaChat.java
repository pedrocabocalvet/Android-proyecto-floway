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
import android.view.View;
import android.view.ViewGroup;

import com.firebase.client.ChildEventListener;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;

import paisdeyann.floway.AdaptadorGrupoMensajes;
import paisdeyann.floway.Conexion.Conexion;
import paisdeyann.floway.Menu_Principal;
import paisdeyann.floway.Objetos.Conversacion;
import paisdeyann.floway.Objetos.Mensaje;
import paisdeyann.floway.Objetos.Privado;
import paisdeyann.floway.R;



public class PantallaChat extends Fragment {

    Menu_Principal activity;
    Context context;

    RecyclerView rv;
    RecyclerView.LayoutManager rvLM;
    AdaptadorGrupoMensajes adaptador;

    private Firebase mFirebase;
    private FirebaseAuth firebaseAuth;

    //ArrayList<Conversacion> conversaciones = new ArrayList<Conversacion>();
    ArrayList<Privado> conversaciones = new ArrayList<Privado>();

    public PantallaChat() {
        // Required empty public constructor
    }
    public void setContext(Context cont,Menu_Principal act){
        activity=act;
        context=cont;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_pantalla_chat, container, false);

        Log.d("prueba","llego al fragment chat");

        firebaseAuth = FirebaseAuth.getInstance();
        Firebase.setAndroidContext(context);



        rv = (RecyclerView) rootView.findViewById(R.id.recyclerViewMensajes);
        rvLM = new LinearLayoutManager(context);
        rv.setLayoutManager(rvLM);



        adaptador = new AdaptadorGrupoMensajes(conversaciones, context);
        rv.setAdapter(adaptador);


        mFirebase = new Firebase("https://flowaychatviajes.firebaseio.com").child("Privados/"+Conexion.usuarioActivo.getId_usuario());
        //mFirebase = new Firebase("https://flowaychatviajes.firebaseio.com").child("Conversaciones");



        mFirebase.addChildEventListener(new ChildEventListener() {

            @Override

            public void onChildAdded(com.firebase.client.DataSnapshot dataSnapshot, String s) {

                if (dataSnapshot != null && dataSnapshot.getValue() != null){

                    try {

                        Privado model = dataSnapshot.getValue(Privado.class);
                        Log.d("prueba10","voy a añadir una nueva conversacion "+model.getFecha()+" "+model.getNombreChat()+" "+model.getIdConversando());
                        conversaciones.add(model);
                       rv.scrollToPosition(conversaciones.size()-1);
                        adaptador.notifyItemInserted(conversaciones.size()-1);



                    }catch(Exception e){
                        Log.d("prueba10","entro yo aki alguna vez?");
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void onChildChanged(com.firebase.client.DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(com.firebase.client.DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(com.firebase.client.DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });





        DatabaseReference conversacionesBBDD = FirebaseDatabase.getInstance().getReference().child("Privados/"+Conexion.usuarioActivo.getId_usuario());

        conversacionesBBDD.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                Iterator i = dataSnapshot.getChildren().iterator();

                while (i.hasNext()){

                    Privado c = ((DataSnapshot) i.next()).getValue(Privado.class);
                    Log.d("prueba10","añado lo de abajo ");
                    c.imprimir();
                    conversaciones.add(c);
                    rv.scrollToPosition(conversaciones.size()-1);
                    adaptador.notifyItemInserted(conversaciones.size()-1);

                    if(conversaciones.isEmpty()){
                        Log.d("prueba10","el array esta vacio");
                    }


                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        return rootView;
    }

    public interface OnFragmentInteractionListener {
        public void onNavFragmentInteraction(String string);
    }
    public RecyclerView getRv() {
        return rv;
    }

    public void setRv(RecyclerView rv) {
        this.rv = rv;
    }


}
