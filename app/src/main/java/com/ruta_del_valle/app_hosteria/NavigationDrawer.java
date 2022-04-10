package com.ruta_del_valle.app_hosteria;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;
import com.ruta_del_valle.app_hosteria.fragments.DetalleHabitacion_Fragment;
import com.ruta_del_valle.app_hosteria.fragments.MainFragment;
import com.ruta_del_valle.app_hosteria.fragments.PerfilFragment;
import com.ruta_del_valle.app_hosteria.fragments.ServiceFragment;
import com.ruta_del_valle.app_hosteria.fragments.bridge.BridgeHabitacionToDetalle;
import com.ruta_del_valle.app_hosteria.rest_api.model.Habitacion;

public class NavigationDrawer extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, BridgeHabitacionToDetalle {

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    Toolbar toolbar;
    NavigationView navigationView;

    //variables del fragment
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    //Fragments creados
    Fragment fragmentPrincipal, fragmentServicio, fragmentPerfil;


    //variables del FragmentDetalleHabitacion
    DetalleHabitacion_Fragment detalleHabitacion_fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_drawer);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer);
        navigationView = findViewById(R.id.navigationView);

        //evento Onclik en el navigationView
        navigationView.setNavigationItemSelectedListener(this);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, (R.string.open), (R.string.close));
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        actionBarDrawerToggle.syncState();

        //instanciamos los fragments con sus Clases
        fragmentPrincipal = new MainFragment();
        fragmentServicio = new ServiceFragment();
        fragmentPerfil = new PerfilFragment();

        //cargar fragment principal
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.container, fragmentPrincipal);
        fragmentTransaction.commit();

        //titulo del toolbar
        setTitle("Home");



    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        /*configuracion para esconder el content del navigation drawer
        una vez que una de sus opciones sea seleccionada*/
        drawerLayout.closeDrawer(GravityCompat.START);

        fragmentTransaction = getSupportFragmentManager().beginTransaction();

        switch (item.getItemId()) {

            case R.id.home:
                //cargar fragment principal
                //fragmentManager = getSupportFragmentManager();
                //fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container, fragmentPrincipal);
                //fragmentTransaction.commit();
                break;
            case R.id.info:
                //cargar fragment servicio
                fragmentTransaction.replace(R.id.container, fragmentServicio);
                fragmentTransaction.addToBackStack(null);//permite regresar al anterior fragment
                break;
            case R.id.profile:
                fragmentTransaction.replace(R.id.container,fragmentPerfil);
                fragmentTransaction.addToBackStack(null);//permite regresar al anterior fragment

        }

        //concretamos la transaccion de los fragments
        fragmentTransaction.commit();

        /*cambiamos el título de acuerdo  los seleccionado*/
        toolbar.setTitle(item.getTitle());

        return  false;
    }

    //Sirve como puente para enviar información entre Fragments
    @Override
    public void enviarHabitacion(Habitacion habitacion) {

        //proceso para enviar los datos
        detalleHabitacion_fragment = new DetalleHabitacion_Fragment();
        //objeto bundle para transportar la informacion
        Bundle bundleEnvio = new Bundle();
        //enviamos el objeto serializado
        bundleEnvio.putSerializable("objeto",habitacion);
        detalleHabitacion_fragment.setArguments(bundleEnvio);

        //abrimos el fragment
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container,detalleHabitacion_fragment);
        fragmentTransaction.addToBackStack(null);//permite regresar al anterior fragment
        fragmentTransaction.commit();
    }
}