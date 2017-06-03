package com.example.jacobo.managercounts;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

//import com.example.jacobo.managercounts.Dialogos.DateDialog;



/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentoInfoclientes extends Fragment {

    TextView tNombre,tCedula,tDireccion,tTelefono,tsaldo,tBarrio,tFechapago;
    String PREFS_NAME = "MyPrefsFile";
    Button bFecha, bAbonar;

    int year, month , day;
    String flagdate;


    public FragmentoInfoclientes() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View inflated = inflater.inflate(R.layout.fragment_infoclientes, container, false);
        tNombre = (TextView) inflated.findViewById(R.id.Infonombre);
        tCedula = (TextView) inflated.findViewById(R.id.Infocedula);
        tBarrio = (TextView) inflated.findViewById(R.id.Infobarrio);
        tDireccion = (TextView) inflated.findViewById(R.id.Infodireccion);
        tTelefono = (TextView) inflated.findViewById(R.id.Infotelefono);
        tsaldo = (TextView) inflated.findViewById(R.id.Infosaldo);
        tFechapago = (TextView) inflated.findViewById(R.id.Infofechapago);



        bFecha = (Button) inflated.findViewById(R.id.bFecha);


        SharedPreferences datos = this.getActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String Usuario = " "+datos.getString("nombre","");
        String Cedula = " "+datos.getString("cedula","");
        String Barrio = " "+datos.getString("barrio","");
        String Direccion = " "+datos.getString("direccion","");
        String Apellido = " "+datos.getString("apellido","");
        String Fecha = " "+datos.getString("fecha","");
        String Telefono = " "+datos.getString("telefono","");
        String Saldo = " "+datos.getString("saldo","");
        flagdate = datos.getString("flagdate","");

        year = datos.getInt("yeardate",0);
        month = datos.getInt("monthdate",0);
        day = datos.getInt("daydate",0);





        Log.d("Usuario", Usuario);
        Log.d("Password", Cedula);
        Log.d("Correo", Barrio);

        tNombre.setText(getString(R.string.nombre)+":"+Usuario+""+Apellido);
        tCedula.setText(getString(R.string.cedula)+":"+Cedula);
        tBarrio.setText(getString(R.string.barrio)+":"+Barrio);
        tDireccion.setText(getString(R.string.direccion)+":"+Direccion);
        tTelefono.setText(getString(R.string.telefono)+":"+Telefono);
        tsaldo.setText(getString(R.string.saldo)+":"+Saldo);
        tFechapago.setText("FECHA PAGO: "+Fecha);



        bFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentManager fragmentManager = getFragmentManager();



                //new DateDialog().show(fragmentManager, "DatePickerFragment");


                Snackbar.make(v, flagdate, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_infoclientes, container, false);
        return inflated;
    }

}
