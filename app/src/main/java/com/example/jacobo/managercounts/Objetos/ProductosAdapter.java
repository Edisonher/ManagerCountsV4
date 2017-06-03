package com.example.jacobo.managercounts.Objetos;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.jacobo.managercounts.DrawerVendActivity;
import com.example.jacobo.managercounts.R;

import java.util.ArrayList;

/**
 * Creado por Hermosa Programación
 */
public class ProductosAdapter extends RecyclerView.Adapter<ProductosAdapter.ProductosViewHolder> {
    private ArrayList<Productos> items;
    Context ctx;




    public static class ProductosViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        // Campos respectivos de un item
        //public ImageView imagen;
        public TextView nombre;
        public TextView visitas;
        ArrayList<Productos> items = new ArrayList<Productos> ();
        Context ctx;

        public ProductosViewHolder(View v, Context ctx, ArrayList<Productos> items) {
            super(v);
            this.items = items;
            this.ctx = ctx;
            v.setOnClickListener(this);
            //imagen = (ImageView) v.findViewById(R.id.imagen);
            nombre = (TextView) v.findViewById(R.id.cardnombre);
            visitas = (TextView) v.findViewById(R.id.cardcedula);

        }


        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            Productos producto = this.items.get(position);

            Intent intent = new Intent(this.ctx,DrawerVendActivity.class);
            intent.putExtra("nombreprod",producto.getNombre());
            intent.putExtra("precioprod",producto.getPrecio());
            intent.putExtra("descripcionprod",producto.getDescripcion());
            intent.putExtra("bandera","3");
           // this.ctx.startActivity(intent);
            this.ctx.startActivity(intent);
        }
    }

    public ProductosAdapter(ArrayList<Productos> items, Context ctx) {

        this.items = items;
        this.ctx = ctx;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public ProductosViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.cliente_card, viewGroup, false);
        ProductosViewHolder productosViewHolder = new ProductosViewHolder(v,ctx,items);
        return productosViewHolder;
    }

    @Override
    public void onBindViewHolder(ProductosViewHolder viewHolder, int i) {
        //viewHolder.imagen.setImageResource(items.get(i).getImagen());
        viewHolder.nombre.setText(items.get(i).getNombre());
        viewHolder.visitas.setText("Precio:"+String.valueOf(items.get(i).getPrecio()));
    }
}
