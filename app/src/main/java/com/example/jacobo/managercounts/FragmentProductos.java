package com.example.jacobo.managercounts;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.jacobo.managercounts.Dialogos.Addprod_dialog;
import com.example.jacobo.managercounts.Objetos.Clientes;
import com.example.jacobo.managercounts.Objetos.ClientesAdapter;
import com.example.jacobo.managercounts.Objetos.Productos;
import com.example.jacobo.managercounts.Objetos.ProductosAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentProductos extends Fragment {

    Intent intent;

    Button bAgregarpro;

    private RecyclerView recycler;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager lManager;
    public static ArrayList<Productos> items;


    public FragmentProductos() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_productos, container, false);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();

        items = new ArrayList<Productos>();


        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                android.app.FragmentManager fm = getActivity().getFragmentManager();


                new Addprod_dialog().show(fm, "Addprod_dialog");


                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
            }
        });

        database.getReference("productos").addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if (dataSnapshot != null && dataSnapshot.getValue() != null) {

                    Productos parqueadero = dataSnapshot.getValue(Productos.class);
                    items.add(parqueadero);
                    //adapter.notifyDataSetChanged();
                    adapter.notifyItemInserted(items.size() - 1);

                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                String key = dataSnapshot.getKey();
                Productos parqueadero = dataSnapshot.getValue(Productos.class);
                for (Productos cl : items
                        ) {
                    if (cl.getNombre().equals(key)) {
                        cl.setNombre(parqueadero.getNombre());
                        cl.setPrecio(parqueadero.getPrecio());
                        //cl.setCedula(parqueadero.getCedula());
                        break;

                    }

                }

                adapter.notifyDataSetChanged();
                /*
                items.add(parqueadero);
                adapter.notifyItemInserted(items.size() - 1);
                //adapter.notifyItemChanged(Integer.parseInt(parqueadero.getCedula()));
                adapter.notifyDataSetChanged();*/

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {


            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        recycler = (RecyclerView) view.findViewById(R.id.recicladorProductos);
        recycler.setHasFixedSize(true);

        // Usar un administrador para LinearLayout
        lManager = new LinearLayoutManager(this.getContext());
        recycler.setLayoutManager(lManager);


        // Crear un nuevo adaptador

        adapter = new ProductosAdapter(items, this.getContext());
        recycler.setAdapter(adapter);


        return view;
    }


}