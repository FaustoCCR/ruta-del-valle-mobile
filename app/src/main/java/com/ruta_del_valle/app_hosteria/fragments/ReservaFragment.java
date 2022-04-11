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

    EditText editTextFechaEntrada,editTextFechaSalida;
    ImageButton btnFechaEntrada, btnFechaSalida;
    DatePicker dpFechaEntrada, dpFechaSalida;

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
        editTextFechaEntrada = view.findViewById(R.id.etFechaEntrada);
        editTextFechaSalida = view.findViewById(R.id.etFechaSalida);
        btnFechaEntrada = view.findViewById(R.id.iBFechaEntrada);
        btnFechaSalida = view.findViewById(R.id.iBFechaSalida);
        dpFechaEntrada = view.findViewById(R.id.datePicker1);
        dpFechaSalida = view.findViewById(R.id.datePicker2);

        btnFechaEntrada.setOnClickListener(this);
        btnFechaSalida.setOnClickListener(this);

        calendar = Calendar.getInstance();

        /*
        * Date Picker - Entrada
        * */
        dpFechaEntrada.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker datePicker, int i, int i1, int i2) {
                editTextFechaEntrada.setText(getFechaDatePickerEntrada());
                dpFechaEntrada.setVisibility(View.GONE);
                checkPickerEntradaToChangePickerSalida();
                //setMinDateSalida();

            }
        });
        /*
         * Date Picker - Salida
         * */
        dpFechaSalida.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)+1, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker datePicker, int i, int i1, int i2) {
                editTextFechaSalida.setText(getFechaDatePickerSalida());
                dpFechaSalida.setVisibility(View.GONE);
            }
        });

        //restricciones datePickers
        dpFechaEntrada.setMinDate(calendar.getTimeInMillis());
        dpFechaSalida.setMinDate(setMinDateSalida().getTimeInMillis());
        //---------------
        editTextFechaEntrada.setText(getFechaDatePickerEntrada());
        editTextFechaSalida.setText(getFechaDatePickerSalida());

        return view;
    }

    protected Calendar setMinDateSalida(){

        Calendar fechaSalida = Calendar.getInstance();
        fechaSalida.set(dpFechaEntrada.getYear(),dpFechaEntrada.getMonth(),dpFechaEntrada.getDayOfMonth()+1);
        return fechaSalida;
    }

    protected String getFechaDatePickerEntrada(){

        String dia = String.valueOf(dpFechaEntrada.getDayOfMonth());
        String mes = String.valueOf(dpFechaEntrada.getMonth()+1);
        String anio = String.valueOf(dpFechaEntrada.getYear());

        return dia+"/"+mes+"/"+anio;

    }

    protected String getFechaDatePickerSalida(){

        String dia = String.valueOf(dpFechaSalida.getDayOfMonth());
        String mes = String.valueOf(dpFechaSalida.getMonth()+1);
        String anio = String.valueOf(dpFechaSalida.getYear());

        return dia+"/"+mes+"/"+anio;
    }

    protected void checkPickerEntradaToChangePickerSalida(){
        Calendar fechaEntrada = Calendar.getInstance();
        fechaEntrada.set(dpFechaEntrada.getYear(),dpFechaEntrada.getMonth(),dpFechaEntrada.getDayOfMonth());

        Calendar fechaSalida = Calendar.getInstance();
        fechaSalida.set(dpFechaSalida.getYear(),dpFechaSalida.getMonth(),dpFechaSalida.getDayOfMonth());

        if (fechaEntrada.getTime().equals(fechaSalida.getTime()) || fechaSalida.getTime().before(fechaEntrada.getTime())){

            int dayMonth = dpFechaEntrada.getDayOfMonth() + 1;
            int month = dpFechaEntrada.getMonth();
            int year = dpFechaEntrada.getYear();

            String dia = String.valueOf(dayMonth);
            String mes = String.valueOf(month+1);
            String anio = String.valueOf(year);

            String date = dia+"/"+mes+"/"+anio;

            editTextFechaSalida.setText(date);
            dpFechaSalida.updateDate(year,month,dayMonth);

        }
    }

    /*Mostrar Calendario*/
    protected void showCalendar(DatePicker datePicker){

        datePicker.setVisibility(View.VISIBLE);


    }

    @Override
    public void onClick(View view)
    {

        switch (view.getId()){
            case R.id.iBFechaEntrada:
                showCalendar(dpFechaEntrada);
            break;

            case R.id.iBFechaSalida:
                showCalendar(dpFechaSalida);
            break;

        }
    }
}