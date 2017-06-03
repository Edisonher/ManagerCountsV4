package com.example.jacobo.managercounts;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Fragmento para la sección de "Inicio"
 */
public class FragmentoInicioVend extends Fragment {

    //String[] parques = new String[] {"San Antonio", "Parque2", "Parque3", "Parque4"};
    Intent intent;

    private Lista_Entrada[] datos = new Lista_Entrada[] {
            new Lista_Entrada(R.drawable.usuario, "Cliente 1", "     10/05/2017","  10000",""),
            new Lista_Entrada(R.drawable.usuario, "Cliente 2", "     15/05/2017","  7000",""),
            new Lista_Entrada(R.drawable.usuario, "Cliente 3", "     20/05/2017","  15000",""),
            new Lista_Entrada(R.drawable.usuario, "Cliente 4", "     30/05/2017","  25000","")
    };

    public FragmentoInicioVend() {

    }

    ListView list;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragmento_inicio_vend, container, false);

        list = (ListView) view.findViewById(R.id.lista);
        //list = (ListView) view.findViewById(R.id.lista);
        Adapter adapter = new Adapter(getContext(),datos);
        //ArrayAdapter adapter = new ArrayAdapter(FragmentoInicioVend,android.R.layout.simple_list_item_1, datos);
        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, parques);
        list.setAdapter(adapter);


        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String data = ((Lista_Entrada)parent.getItemAtPosition(position)).getNombre();
                //Toast.makeText(getApplicationContext(), data,Toast.LENGTH_SHORT).show();

                int sel = (int) parent.getItemIdAtPosition(position);

                switch (sel) {
                    case 0:
                        Fragment fragment0 = new FragmentoInfoclientes();
                        FragmentActivity myContext0 = (FragmentActivity) getActivity();
                        myContext0.getSupportFragmentManager().beginTransaction().replace(R.id.Contenedor,fragment0).commit();
                        break;
                    case 1:
                        Fragment fragment1 = new FragmentoInfoclientes();
                        FragmentActivity myContext1 = (FragmentActivity) getActivity();
                        myContext1.getSupportFragmentManager().beginTransaction().replace(R.id.Contenedor,fragment1).commit();
                        break;
                    case 2:
                        Fragment fragment2 = new FragmentoInfoclientes();
                        FragmentActivity myContext2 = (FragmentActivity) getActivity();
                        myContext2.getSupportFragmentManager().beginTransaction().replace(R.id.Contenedor,fragment2).commit();
                        break;
                    case 3:
                        Fragment fragment3 = new FragmentoInfoclientes();
                        FragmentActivity myContext3 = (FragmentActivity) getActivity();
                        myContext3.getSupportFragmentManager().beginTransaction().replace(R.id.Contenedor,fragment3).commit();
                        break;

                        /*Fragment fragment = new HistoriaFragment();
                        FragmentActivity myContext = (FragmentActivity) getActivity();
                        myContext.getSupportFragmentManager().beginTransaction().replace(R.id.ContenedorCliente,fragment).commit();
                        /*break;
                    case 1:
                        Fragment fragment1 = new HistoriaDosFragment();
                        FragmentActivity myContext1 = (FragmentActivity) getActivity();
                        myContext1.getSupportFragmentManager().beginTransaction().replace(R.id.ContenedorCliente,fragment1).commit();
                        break;
                    case 2:
                        Fragment fragment2 = new HistoriaTresFragment();
                        FragmentActivity myContext2 = (FragmentActivity) getActivity();
                        myContext2.getSupportFragmentManager().beginTransaction().replace(R.id.ContenedorCliente,fragment2).commit();
                        break;
                    case 3:
                        Fragment fragment3 = new HistoriaCuatroFragment();
                        FragmentActivity myContext3 = (FragmentActivity) getActivity();
                        myContext3.getSupportFragmentManager().beginTransaction().replace(R.id.ContenedorCliente,fragment3).commit();
                        break;  */

                }
            }
        });

        return view;
    }


    public class Adapter extends ArrayAdapter<Lista_Entrada> {  //Le pasamos q tpo de datos mandamos

        public Adapter(Context context, Lista_Entrada[] datos) {
            super(context, R.layout.listview_item, datos);
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = LayoutInflater.from(getContext());
            View item = inflater.inflate(R.layout.listview_item, null,true);

            TextView nombre = (TextView) item.findViewById(R.id.tNombre);
            nombre.setText(datos[position].getNombre());

            TextView descrip = (TextView) item.findViewById(R.id.tDescrip);
            descrip.setText(datos[position].getDescripcion());

            TextView valor = (TextView) item.findViewById(R.id.tvalor);
            valor.setText(datos[position].getValor());

            ImageView imagen = (ImageView) item.findViewById(R.id.iFoto);
            imagen.setImageResource(datos[position].getIdImagen());

            return item;
        }

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {


        super.onActivityCreated(savedInstanceState);

    }
}
