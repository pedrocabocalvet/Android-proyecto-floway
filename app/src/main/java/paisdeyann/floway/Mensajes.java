package paisdeyann.floway;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.Settings;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.firebase.auth.FirebaseAuth;

import java.sql.Timestamp;
import java.util.ArrayList;

import paisdeyann.floway.Conexion.Conexion;
import paisdeyann.floway.Objetos.Mensaje;
import paisdeyann.floway.Objetos.Publicacion;
import paisdeyann.floway.Objetos.Transaccion;


public class Mensajes extends AppCompatActivity {
    ArrayList<Mensaje> mensajes = new ArrayList<Mensaje>();
    AdaptadorMensajes adaptador;

    RecyclerView recyclerView;
    RecyclerView.LayoutManager rvLM;
    private FirebaseAuth firebaseAuth;
    private Firebase mFirebase;


    Button añadirMensaje;
    EditText editTextMensajes;

    private String mId;

    String nombreConversando;
    Context contexto;






    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_chat,menu);
        return true;

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.pagar:

                LayoutInflater linf = LayoutInflater.from(this);
                final View inflator = linf.inflate(R.layout.dialog_transaccion, null);
                AlertDialog.Builder alert = new AlertDialog.Builder(this);
                Log.d("prueba3","entro en el dialogo");
                alert.setTitle("Registra Transaccion");
                //alert.setMessage("Elige nuevo usuario y password");
                alert.setView(inflator);

                final EditText precio = (EditText) inflator.findViewById(R.id.editTextPrecio);
                //final RadioGroup radio = (RadioGroup) inflator.findViewById(R.id.radioGroup6);
                final RadioButton pasajero = (RadioButton)inflator.findViewById(R.id.radioButtonPasajero);
                final RadioButton conductor = (RadioButton) inflator.findViewById(R.id.radioButtonConductor);

                Log.d("prueba3","declaro todo");

                alert.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton)
                    {

                        Log.d("prueba3","entro en ok");
                        String user = precio.getText().toString();
                        // find the radiobutton by returned id
                        Log.d("prueba3","cojo user "+user);

                        String rol = "";

                        if(pasajero.isChecked()){
                            rol = "pasajero";
                        }else{
                            rol = "conductor";
                        }

                        Log.d("prueba3","cojo radiobutton");

                        if(!user.equals("")) {
                           // int selectedId = radio.getCheckedRadioButtonId();
                            //RadioButton radioM = (RadioButton) findViewById(selectedId);
                           // String rad=""+radioM.getText();

                             FirebaseAuth firebaseAuth;
                             Firebase mFirebase;
                            String mId;

                            mId = Settings.Secure.getString(contexto.getContentResolver(),Settings.Secure.ANDROID_ID);
                            firebaseAuth = FirebaseAuth.getInstance();
                            Firebase.setAndroidContext(contexto);
                            mFirebase = new Firebase("https://flowaychatviajes.firebaseio.com").child("Transacciones/"+Conexion.usuarioActivo.getId_usuario());

                            //String nombre, int precio, String conductor
                            Transaccion transaccion = new Transaccion(nombreConversando,Integer.parseInt(user),rol);
                            mFirebase.push().setValue(transaccion);


                        }else{
                            Toast.makeText(Mensajes.this, "Faltan campos por rellenar", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.cancel();
                    }
                });

                alert.show();





        }

        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mensajes);

        contexto = getApplicationContext();

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setDisplayShowHomeEnabled(true);
        Log.d("prueba2","llego a mensajes");

        //final ImageView imagen = (ImageView) findViewById(R.id.icono_chat);
        TextView nombre = (TextView) findViewById(R.id.header);
        nombre.setText(getIntent().getStringExtra("nombre"));

        nombreConversando = nombre.getText().toString();

/*
        imagen.post(new Runnable() {
            @Override
            public void run() {
                byte[] a = getIntent().getByteArrayExtra("imagen");
                Bitmap bmp = BitmapFactory.decodeByteArray( a, 0, a.length);
                RoundedBitmapDrawable roundDrawable = RoundedBitmapDrawableFactory.create(getResources(), bmp);
                roundDrawable.setCircular(true);
                imagen.setImageDrawable(roundDrawable);

            }
        });

*/

        Log.d("prueba","llego a mensajes");

        mId = Settings.Secure.getString(this.getContentResolver(),Settings.Secure.ANDROID_ID);
      //  Log.d("prueba","el mid es :"+mId);

        firebaseAuth = FirebaseAuth.getInstance();

        String chat = getIntent().getBundleExtra("bundle").getString("chat");
        //Log.d("prueba","el chat es: ");



        añadirMensaje = (Button) findViewById(R.id.buttonMensajes);
        editTextMensajes = (EditText) findViewById(R.id.editTextMensajes);

        recyclerView = (RecyclerView) findViewById(R.id.elMeuRecyclerView);
        rvLM = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(rvLM);

        adaptador = new AdaptadorMensajes(mensajes);
        recyclerView.setAdapter(adaptador);
        Firebase.setAndroidContext(this);

        mFirebase = new Firebase("https://flowaychatviajes.firebaseio.com/chats").child(chat+"/mensajes");

        añadirMensaje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!editTextMensajes.getText().toString().equals("")) {
                    // creara una instacia hacia firebase insertando el mensaje sobre la clave del autor (email)
                    Mensaje mensaje = new Mensaje(editTextMensajes.getText().toString(), "" + new Timestamp(System.currentTimeMillis()), Conexion.usuarioActivo.getId_usuario());
                    mFirebase.push().setValue(mensaje);
                    // vaciar texto
                    editTextMensajes.setText("");
                }
            }
        });

        mFirebase.addChildEventListener(new ChildEventListener() {

            @Override

            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.d("prueba","llego al listener de firebase");
                if (dataSnapshot != null && dataSnapshot.getValue() != null){
                    try {
                        Mensaje model = dataSnapshot.getValue(Mensaje.class);
                        Log.d("prueba","he recuperado el mensaje"+model.getContenido()+" "+model.getFecha()+" "+model.getEmisor());
                        mensajes.add(model);
                        recyclerView.scrollToPosition(mensajes.size()-1);
                        adaptador.notifyItemInserted(mensajes.size()-1);

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




    }
}
