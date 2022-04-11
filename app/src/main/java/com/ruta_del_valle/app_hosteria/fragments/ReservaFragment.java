package com.ruta_del_valle.app_hosteria.fragments;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;

import com.ruta_del_valle.app_hosteria.R;

import java.util.Calendar;
import java.util.Date;


public class ReservaFragment extends Fragment implements View.OnClickListener {

    EditText editTextFecha;
    ImageButton btnFecha;
    DatePicker dpFecha;

    Calendar calendar;


    public ReservaFragment() {
        // Required empty public constructor
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_reserva, container, false);

        /*Conectamos con los componentes gráficos*/
        editTextFecha = view.findViewById(R.id.editTextDate);
        btnFecha = view.findViewById(R.id.imageButtonDate1);
        dpFecha = view.findViewById(R.id.datePicker1);

        btnFecha.setOnClickListener(this);

        calendar = Calendar.getInstance();

        dpFecha.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker datePicker, int i, int i1, int i2) {
                editTextFecha.setText(getFechaDatePicker());
                dpFecha.setVisibility(View.GONE);
            }
        });

        editTextFecha.setText(getFechaDatePicker());

        return view;
    }


    protected String getFechaDatePicker(){

        String dia = String.valueOf(dpFecha.getDayOfMonth());
        String mes = String.valueOf(dpFecha.getMonth()+1);
        String anio = String.valueOf(dpFecha.getYear());

        return dia+"/"+mes+"/"+anio;

    }

    /*Mostrar Calendario*/
    protected void showCalendar(){

        dpFecha.setVisibility(View.VISIBLE);


    }

    @Override
    public void onClick(View view) {
        showCalendar();
    }
}