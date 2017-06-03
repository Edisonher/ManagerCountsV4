package com.example.jacobo.managercounts;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jacobo.managercounts.Objetos.Clientes;
import com.example.jacobo.managercounts.Objetos.ClientesAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragmentmostrar extends Fragment {

    private RecyclerView recycler;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager lManager;
    public static ArrayList<Clientes> items;



    public Fragmentmostrar() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View inflated = inflater.inflate(R.layout.fragment_fragmentmostrar, container, false);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();

        items = new ArrayList<Clientes>();

       /* database.getReference("clientes").getRoot().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                items.removeAll(items);
                for (DataSnapshot snapshot:
                        dataSnapshot.getChildren()) {
                    Clientes cliente = snapshot.getValue(Clientes.class);
                    items.add(cliente);
                    adapter.notifyItemInserted(items.size() - 1);

                }
                //Clientes cliente = dataSnapshot.getValue(Clientes.class);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/
        database.getReference("clientes").addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if (dataSnapshot != null && dataSnapshot.getValue() != null) {

                    Clientes parqueadero = dataSnapshot.getValue(Clientes.class);
                    items.add(parqueadero);
                    adapter.notifyDataSetChanged();
                    //adapter.notifyItemInserted(items.size() - 1);

                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                String key = dataSnapshot.getKey();
                Clientes parqueadero = dataSnapshot.getValue(Clientes.class);
                for (Clientes cl:items
                     ) {if(cl.getCedula().equals(key)){
                        cl.setNombre(parqueadero.getNombre());
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

        recycler = (RecyclerView) inflated.findViewById(R.id.recicladorCliente);
        recycler.setHasFixedSize(true);

        // Usar un administrador para LinearLayout
        lManager = new LinearLayoutManager(this.getContext());
        recycler.setLayoutManager(lManager);


        // Crear un nuevo adaptador

        adapter = new ClientesAdapter(items,this.getContext());
        recycler.setAdapter(adapter);



        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_fragmentmostrar, container, false);
        return inflated;
    }



}
