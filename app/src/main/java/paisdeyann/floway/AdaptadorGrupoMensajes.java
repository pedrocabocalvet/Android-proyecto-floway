package paisdeyann.floway;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Objects;

import paisdeyann.floway.Conexion.Conexion;
import paisdeyann.floway.Objetos.Conversacion;
import paisdeyann.floway.Threads.ConseguirImagenConIdYPonerEnImageView;
import paisdeyann.floway.Threads.ConseguirUsuarioPorId;

/**
 * Created by caboc on 16/02/2017.
 */

public class AdaptadorGrupoMensajes extends RecyclerView.Adapter<AdaptadorGrupoMensajes.AdaptadorGrupoMensajesViewHolder>{

    ArrayList<Conversacion> conversaciones;
    Context contexto;

    public AdaptadorGrupoMensajes(ArrayList<Conversacion> conversaciones, Context contexto){

        this.conversaciones = conversaciones;
        this.contexto = contexto;

    }

    @Override
    public AdaptadorGrupoMensajesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemgrupomensajes,parent,false);
        return new AdaptadorGrupoMensajesViewHolder(v);

    }

    @Override
    public void onBindViewHolder(AdaptadorGrupoMensajesViewHolder holder, int position) {

        holder.textViewFecha.setText(conversaciones.get(position).getFecha());
        holder.chat = conversaciones.get(position).getChat();

        int id;

        if(conversaciones.get(position).getId1() != Conexion.usuarioActivo.getId_usuario()){
            id = conversaciones.get(position).getId1();
        }else{
            id = conversaciones.get(position).getId2();
        }

       // Log.d("prueba","estoy en el adaptador " +id);

        Object[] objetos = new Object[5];
        objetos[0] = contexto;
        objetos[1] = id;
        objetos[2] = holder.textViewNombre;
        objetos[3] = holder.textViewFecha;
        objetos[4] = conversaciones.get(position).getFecha();

        ConseguirUsuarioPorId myThread = new ConseguirUsuarioPorId();
        myThread.execute(objetos);

        // 0 contexto
        // 1 id del usuario q queremos coger la foto
        // 2 imageView donde va la foto

        Object[] objetos2 = new Object[3];
        objetos2[0] = contexto;
        objetos2[1] = id;
        objetos2[2] = holder.foto;

        ConseguirImagenConIdYPonerEnImageView myOtroThread = new ConseguirImagenConIdYPonerEnImageView();
        myOtroThread.execute(objetos2);

        //holder.textViewNombre.setText(""+id);

    }

    @Override
    public int getItemCount() {
        return conversaciones.size();
    }

    public class AdaptadorGrupoMensajesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView textViewFecha;
        TextView textViewNombre;
        View view;
        ImageView foto;
        String chat;

        public AdaptadorGrupoMensajesViewHolder(View itemView) {
            super(itemView);

            textViewFecha = (TextView) itemView.findViewById(R.id.textViewFechaItemGrupoMensaje);
            textViewNombre = (TextView) itemView.findViewById(R.id.textViewPersonaItemConversacion);
            foto = (ImageView) itemView.findViewById(R.id.imageViewFotoGrupoMensajes);
            view = itemView;
            view.setOnClickListener(this);

        }


        @Override
        public void onClick(View v) {
            Intent intent = new Intent(contexto,Mensajes.class);
            Bundle bundle = new Bundle();
            bundle.putString("chat",chat);
            //bundle.putString("nombre",textViewNombre.toString());


            intent.putExtra("bundle",bundle);
            intent.putExtra("nombre",textViewNombre.getText().toString());

/*
            BitmapDrawable drawable = (BitmapDrawable) foto.getDrawable();
            Bitmap bitmap = drawable.getBitmap();

            ByteArrayOutputStream bs = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, bs);
            intent.putExtra("imagen", bs.toByteArray());
*/
            Log.d("prueba","voy ha hacer el intent");
            textViewNombre.getContext().startActivity(intent);
        }
    }
}
