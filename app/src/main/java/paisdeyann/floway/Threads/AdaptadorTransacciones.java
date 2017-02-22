package paisdeyann.floway.Threads;

import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import paisdeyann.floway.Objetos.Transaccion;
import paisdeyann.floway.R;

/**
 * Created by Dario on 19/02/2017.
 */

public class AdaptadorTransacciones  extends RecyclerView.Adapter<AdaptadorTransacciones.AdaptadorTransaccionesViewHolder>{

    ArrayList<Transaccion> transacciones;

    public AdaptadorTransacciones( ArrayList<Transaccion> transacciones){
        this.transacciones = transacciones;
        Log.d("prueba5","constructor bien");
    }

    @Override
    public AdaptadorTransacciones.AdaptadorTransaccionesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_transaccion,parent,false);
        return new AdaptadorTransacciones.AdaptadorTransaccionesViewHolder(v);

    }

    @Override
    public void onBindViewHolder(AdaptadorTransacciones.AdaptadorTransaccionesViewHolder holder, int position) {

        //holder.fecha.setText(transacciones.get(position).getFecha());
       // holder.nombre.setText(transacciones.get(position).getNombre());
       // holder.precio.setText(transacciones.get(position).getPrecio());

        holder.nombre.setText(transacciones.get(position).getNombre());
        holder.precio.setText(""+transacciones.get(position).getPrecio()+"€");

        if(transacciones.get(position).getConductor().equals("pasajero")){
            holder.foto.setImageResource(R.drawable.pasajero);
            holder.card.setBackgroundColor(Color.parseColor("#009688"));
        }


    }

    @Override
    public int getItemCount() {
        return transacciones.size();
    }

    public class AdaptadorTransaccionesViewHolder extends RecyclerView.ViewHolder{

        TextView nombre;
        TextView precio;
        ImageView foto;
        CardView card;

        public AdaptadorTransaccionesViewHolder(View itemView) {
            super(itemView);
            nombre = (TextView) itemView.findViewById(R.id.textViewTransaccionNombre);
            precio = (TextView) itemView.findViewById(R.id.textViewTransaccionDinero);
            foto = (ImageView) itemView.findViewById(R.id.imageViewTransaccion);
            card = (CardView) itemView.findViewById(R.id.card_view);
        }


    }

}