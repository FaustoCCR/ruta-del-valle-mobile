package com.ruta_del_valle.app_hosteria.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.ruta_del_valle.app_hosteria.R;
import com.ruta_del_valle.app_hosteria.rest_api.model.Habitacion;

public class DetalleHabitacion_Fragment extends Fragment implements View.OnClickListener {

    TextView tipo_habitacion, detalles, planta, max_adultos, max_ninos, costo;
    Button btnReservarAhora;

    //varibles para la navegación entre fragments
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    //Detalles de la habitacion
    Habitacion habitacion;

    Fragment fragmentReserva;
    public DetalleHabitacion_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detalle_habitacion_, container, false);

        tipo_habitacion = view.findViewById(R.id.textVTipoHb);
        detalles = view.findViewById(R.id.textVDetalle);
        planta = view.findViewById(R.id.textVPlanta);
        max_adultos = view.findViewById(R.id.textVMax_a);
        max_ninos = view.findViewById(R.id.textVMax_n);
        costo = view.findViewById(R.id.textVCosto);
        btnReservarAhora = view.findViewById(R.id.btnReservarAhora);
        btnReservarAhora.setOnClickListener(this);


        //instancia del Fragment
        fragmentReserva = new ReservaFragment();

        //fragmentManager = getActivity().getSupportFragmentManager();


        //Objeto bundle para recibir el objeto enviado por argumentos
        Bundle objetoHabitacion = getArguments();
        habitacion = null;
        //validamos si existen argumentos enviados
        if (objetoHabitacion != null) {

            habitacion = (Habitacion) objetoHabitacion.getSerializable("objeto");

            //Establecer los datos en las vistas
            tipo_habitacion.setText("Habitación " + habitacion.getTipo_habitacion());
            detalles.setText(detallesHabitacion(habitacion.getDescripcion()));
            planta.setText(habitacion.getPlanta());
            max_adultos.setText(String.valueOf(habitacion.getMax_adultos()));
            max_ninos.setText(String.valueOf(habitacion.getMax_ninos()));
            costo.setText("$ " + habitacion.getCosto_noche());

        }


        return view;
    }


    protected String detallesHabitacion(String detalle) {

        String[] items = detalle.split("-");
        String presentation = "";
        for (String item : items) {

            presentation += "- " + item + "\n";

        }

        return presentation;

    }

    @Override
    public void onClick(View view) {

        fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();


        switch (view.getId()){

            case R.id.btnReservarAhora:
                transportDetailsHb();
                fragmentTransaction.replace(R.id.container,fragmentReserva);
                fragmentTransaction.addToBackStack(null);//permite regresar al anterior fragment
                fragmentTransaction.commit();
                break;
        }

    }

    public void transportDetailsHb(){
        Bundle bundle = new Bundle();
        bundle.putSerializable("detailhb",habitacion);
        getParentFragmentManager().setFragmentResult("key",bundle);
    }
}