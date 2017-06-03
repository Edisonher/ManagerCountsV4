package com.example.jacobo.managercounts.Objetos;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.jacobo.managercounts.DrawerVendActivity;
import com.example.jacobo.managercounts.R;

import java.util.ArrayList;

/**
 * Creado por Hermosa Programación
 */
public class ClientesAdapter extends RecyclerView.Adapter<ClientesAdapter.ClientesViewHolder> {
    private ArrayList<Clientes> items;
    Context ctx;




    public static class ClientesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        // Campos respectivos de un item
        public ImageView imagen;
        public TextView nombre;
        public TextView visitas;
        ArrayList<Clientes> items = new ArrayList<Clientes> ();
        Context ctx;

        public ClientesViewHolder(View v,Context ctx,ArrayList<Clientes> items) {
            super(v);
            this.items = items;
            this.ctx = ctx;
            v.setOnClickListener(this);
            imagen = (ImageView) v.findViewById(R.id.imagen);
            nombre = (TextView) v.findViewById(R.id.cardnombre);
            visitas = (TextView) v.findViewById(R.id.cardcedula);

        }


        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            Clientes cliente = this.items.get(position);

            Intent intent = new Intent(this.ctx,DrawerVendActivity.class);
            intent.putExtra("nombre",cliente.getNombre());
            intent.putExtra("cedula",cliente.getCedula());
            intent.putExtra("barrio",cliente.getBarrio());
            intent.putExtra("direccion",cliente.getDireccion());
            intent.putExtra("apellido",cliente.getApellido());
            intent.putExtra("fecha",cliente.getFechapago());
            intent.putExtra("telefono",cliente.getTelefono());
            intent.putExtra("saldo",cliente.getDeuda());

            intent.putExtra("bandera","1");
            this.ctx.startActivity(intent);
        }
    }

    public ClientesAdapter(ArrayList<Clientes> items, Context ctx) {

        this.items = items;
        this.ctx = ctx;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public ClientesViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.cliente_card, viewGroup, false);
        ClientesViewHolder clientesViewHolder = new ClientesViewHolder(v,ctx,items);
        return clientesViewHolder;
    }

    @Override
    public void onBindViewHolder(ClientesViewHolder viewHolder, int i) {
        //viewHolder.imagen.setImageResource(items.get(i).getImagen());
        viewHolder.nombre.setText(items.get(i).getNombre());
        viewHolder.visitas.setText("Cedula:"+String.valueOf(items.get(i).getCedula()));
        Glide.with(ctx).load(items.get(i).getUrlimagen()).into(viewHolder.imagen);
    }
}
