package com.example.jacobo.managercounts;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

//import com.example.jacobo.managercounts.Dialogos.Compraprod_dialog;

public class DrawerVendActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, DatePickerDialog.OnDateSetListener {

    SharedPreferences prefs;
    SharedPreferences.Editor editor;

    String PREFS_NAME = "MyPrefsFile";
    SharedPreferences datos;
    String nom, ced, bar ,tel, dir ,apell,saldo,fecha;
    String flag="1";
    String flag2="3";

    int yeardate=0,monthdate=0,daydate=0;
    String flagdate="1";
    String fechadate="";
    String nomprod,descripcionprod;

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

       yeardate = year;
       monthdate = monthOfYear;
       daydate = dayOfMonth;
       flagdate ="2";

        datos = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        SharedPreferences.Editor edit = datos.edit();

        edit.putString("flagdate",flagdate);
        edit.putString("fecha", String.valueOf(yeardate)+"/"+String.valueOf(monthdate+1)+"/"+String.valueOf(daydate));
        edit.commit();

        Fragment fragmentoGenerico = null;
        FragmentManager fragmentManager = getSupportFragmentManager();

        fragmentoGenerico = new FragmentoInfoclientes();

        editor.commit();
        fragmentManager.beginTransaction().replace(R.id.Contenedor, fragmentoGenerico).commit();

        monthOfYear= monthOfYear+1;
        Toast.makeText(
                this,
                "Fecha: " + year + "-" + monthOfYear + "-" + dayOfMonth,
                Toast.LENGTH_LONG)
                .show();


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer_vend);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        Fragment fragmentoGenerico = null;
        FragmentManager fragmentManager = getSupportFragmentManager();

        prefs = getSharedPreferences("MisPreferencias",MODE_PRIVATE);
        editor = prefs.edit();

        nom = getIntent().getStringExtra("nombre");
        ced = getIntent().getStringExtra("cedula");
        bar = getIntent().getStringExtra("barrio");
        dir = getIntent().getStringExtra("direccion");
        apell = getIntent().getStringExtra("apellido");
        fecha = getIntent().getStringExtra("fecha");
        tel = getIntent().getStringExtra("telefono");
        saldo = getIntent().getStringExtra("saldo");


        nomprod = getIntent().getStringExtra("nombreprod");
        descripcionprod = getIntent().getStringExtra("descripcionprod");


        datos = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        SharedPreferences.Editor edit = datos.edit();
        edit.putString("nombre", nom);
        edit.putString("cedula", ced);
        edit.putString("barrio",bar);
        edit.putString("direccion", dir);
        edit.putString("apellido", apell);
        edit.putString("fecha",fecha);
        edit.putString("telefono", tel);
        edit.putString("saldo", saldo);

        edit.putInt("yeardate",yeardate);
        edit.putInt("monthdate",monthdate);
        edit.putInt("daydate",daydate);
        edit.putString("flagdate",flagdate);


        //para comprar producto
        edit.putString("nombreprod",nomprod);
        edit.putString("descripcionprod",descripcionprod);


        edit.commit();


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
/*
        prefs = getSharedPreferences("MisPreferencias",MODE_PRIVATE);
        editor = prefs.edit();


        Fragment fragmentoGenerico = null;
        FragmentManager fragmentManager = getSupportFragmentManager();*/
        if(flag.equals(getIntent().getStringExtra("bandera"))){


            fragmentoGenerico = new FragmentoInfoclientes();

            editor.commit();
            fragmentManager.beginTransaction().replace(R.id.Contenedor, fragmentoGenerico).commit();

        }
        else if (flag2.equals(getIntent().getStringExtra("bandera"))){
            android.app.FragmentManager fm = getFragmentManager();

            //new Compraprod_dialog().show(fm, "Compraprod_dialog");

            fragmentoGenerico = new FragmentProductos();

            editor.commit();
            fragmentManager.beginTransaction().replace(R.id.Contenedor, fragmentoGenerico).commit();


        }
        else {
            fragmentoGenerico = new Fragmentmostrar();

            editor.commit();
            fragmentManager.beginTransaction().replace(R.id.Contenedor, fragmentoGenerico).commit();

        }
        /*
        fragmentoGenerico = new Fragmentmostrar();

        editor.commit();
        fragmentManager.beginTransaction().replace(R.id.Contenedor, fragmentoGenerico).commit();*/


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.drawer_vend, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        Fragment fragmentoGenerico = null;
        FragmentManager fragmentManager = getSupportFragmentManager();

        fragmentoGenerico = new ContenedorFragment();
        int id = item.getItemId();

        if (id == R.id.nav_InicioVendedor) {
            fragmentoGenerico = new Fragmentmostrar();
        }
        else if (id == R.id.nav_Clientes) {
            fragmentoGenerico = new Fragmentmostrar();
        }
        else if (id == R.id.nav_Inventario) {
            fragmentoGenerico = new FragmentProductos();

        }
        else if (id == R.id.nav_RevisarPedidos) {
            fragmentoGenerico = new FragmentPedidos();
        }
        else if (id == R.id.nav_CerrarSesion) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }

        editor.commit();
        if (fragmentoGenerico != null) {
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.Contenedor, fragmentoGenerico)
                    .commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
