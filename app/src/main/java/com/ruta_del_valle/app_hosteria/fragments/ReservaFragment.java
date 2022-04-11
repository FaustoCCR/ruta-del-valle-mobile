package com.ruta_del_valle.app_hosteria.fragments;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentResultListener;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.ruta_del_valle.app_hosteria.R;
import com.ruta_del_valle.app_hosteria.rest_api.model.Habitacion;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;


public class ReservaFragment extends Fragment implements View.OnClickListener {

    TextView tvNroHabitacion,tvTipoHabitacion,tvPrecioN,tvPagoTotal;
    EditText editTextFechaEntrada,editTextFechaSalida;
    ImageButton btnFechaEntrada, btnFechaSalida;
    DatePicker dpFechaEntrada, dpFechaSalida;

    Calendar calendar;

    Habitacion habitacion;

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
        tvNroHabitacion = view.findViewById(R.id.tvNroHabitacion);
        tvTipoHabitacion = view.findViewById(R.id.tvTipoHabitacion);
        tvPrecioN = view.findViewById(R.id.tvCostoN);
        editTextFechaEntrada = view.findViewById(R.id.etFechaEntrada);
        editTextFechaSalida = view.findViewById(R.id.etFechaSalida);
        btnFechaEntrada = view.findViewById(R.id.iBFechaEntrada);
        btnFechaSalida = view.findViewById(R.id.iBFechaSalida);
        dpFechaEntrada = view.findViewById(R.id.datePicker1);
        dpFechaSalida = view.findViewById(R.id.datePicker2);
        tvPagoTotal = view.findViewById(R.id.tvTotalPago);

        //Escucha del bundle recibido

        Bundle objetoHabitacion = getArguments();
        habitacion = null;
        if (objetoHabitacion!=null){
            habitacion = (Habitacion) objetoHabitacion.getSerializable("detailhb");
            tvNroHabitacion.setText("Habitación Nº"+String.valueOf(habitacion.getNum_habitacion()));
            tvTipoHabitacion.setText(habitacion.getTipo_habitacion());
            tvPrecioN.setText("$" + String.valueOf(habitacion.getCosto_noche())+ "/n");
        }
        //----------------------------//
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
                tvPagoTotal.setText(String.valueOf(calcularPrecioFinal()));

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
                tvPagoTotal.setText(String.valueOf(calcularPrecioFinal()));
            }
        });

        //restricciones datePickers
        dpFechaEntrada.setMinDate(calendar.getTimeInMillis());
        dpFechaSalida.setMinDate(setMinDateSalida().getTimeInMillis());
        //---------------
        editTextFechaEntrada.setText(getFechaDatePickerEntrada());
        editTextFechaSalida.setText(getFechaDatePickerSalida());

        tvPagoTotal.setText(String.valueOf(calcularPrecioFinal()));

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

    private double calcularPrecioFinal(){

        String fechaEntrada = editTextFechaEntrada.getText().toString();
        String fechaSalida = editTextFechaSalida.getText().toString();
        Date dateEntrada = convertStringToDate(fechaEntrada);
        Date dateSalida = convertStringToDate(fechaSalida);

        long difference = diferenceBetweenDates(dateEntrada,dateSalida);

        double costoN = habitacion.getCosto_noche();

        double precioFinal = difference * costoN;

        return precioFinal;
    }

    private long diferenceBetweenDates(Date entrada, Date salida){

        long diff = salida.getTime() - entrada.getTime();
        TimeUnit time = TimeUnit.DAYS;
        long difference = time.convert(diff,TimeUnit.MILLISECONDS);
        return difference;
    }

    private Date convertStringToDate(String date){

        SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy");
        Date fecha = new Date();
        try {
            fecha = sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return fecha;

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