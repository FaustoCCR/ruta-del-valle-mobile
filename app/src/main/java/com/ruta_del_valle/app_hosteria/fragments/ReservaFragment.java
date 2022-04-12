package com.ruta_del_valle.app_hosteria.fragments;

import static com.ruta_del_valle.app_hosteria.MainActivity.usernameToSend;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.ruta_del_valle.app_hosteria.R;
import com.ruta_del_valle.app_hosteria.rest_api.io.MyApiAdapter;
import com.ruta_del_valle.app_hosteria.rest_api.io.MyApiService;
import com.ruta_del_valle.app_hosteria.rest_api.model.Habitacion;
import com.ruta_del_valle.app_hosteria.rest_api.model.Reserva;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ReservaFragment extends Fragment implements View.OnClickListener {

    TextView tvNroHabitacion,tvTipoHabitacion,tvPrecioN,tvPagoTotal;
    EditText editTextFechaEntrada,editTextFechaSalida,etAdultos,etNinos;
    ImageButton btnFechaEntrada, btnFechaSalida;
    DatePicker dpFechaEntrada, dpFechaSalida;
    Button btReservar;

    Calendar calendar;

    //Variables para Retrofit
    private MyApiAdapter myApiAdapter;

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


        //Instancia del adapterAPI
        myApiAdapter = new MyApiAdapter();
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
        etAdultos = view.findViewById(R.id.etNumAdultos);
        etNinos = view.findViewById(R.id.etNumNinos);
        tvPagoTotal = view.findViewById(R.id.tvTotalPago);
        btReservar = view.findViewById(R.id.btnConfirmReserva);

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
        btReservar.setOnClickListener(this);

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
                if (!editTextFechaSalida.getText().toString().isEmpty()){
                    tvPagoTotal.setText(String.valueOf(calcularPrecioFinal()));
                }

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

        editTextFechaEntrada.setEnabled(false);
        editTextFechaSalida.setEnabled(false);
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

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date fecha = new Date();
        try {
            fecha = sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return fecha;

    }

    private String dateToAPI(String date){

        String[] fechaArray = date.split("/");

        String dia = fechaArray[0];
        String mes = fechaArray[1];
        String anio = fechaArray[2];
        String dateToCast = anio + "-"+ mes +"-"+dia;

        return dateToCast;

    }

    protected void createReserva(){

        String username = usernameToSend;
        long id_habitacion = habitacion.getId_habitacion();
        String fecha_ingreso = dateToAPI(editTextFechaEntrada.getText().toString());
        String fecha_salida = dateToAPI(editTextFechaSalida.getText().toString());


        System.out.println(fecha_ingreso);
        System.out.println(fecha_salida);
        System.out.println(convertStringToDate(editTextFechaEntrada.getText().toString()));


        String observaciones = "";
        String estado = "Reservada";
        double costo_alojamiento = Double.parseDouble(tvPagoTotal.getText().toString());

        if (!etAdultos.getText().toString().isEmpty()){

            int ninos;
            if (etNinos.getText().toString().isEmpty()){

                ninos = 0;
            }else{
                ninos = Integer.parseInt(etNinos.getText().toString());
            }
            int adultos = Integer.parseInt(etAdultos.getText().toString());

            if (adultos<= habitacion.getMax_adultos() && ninos<=habitacion.getMax_ninos()){

                Reserva reserva = new Reserva(username,id_habitacion,fecha_ingreso,fecha_salida,adultos,ninos,costo_alojamiento,observaciones,estado);

                MyApiService myApiService = myApiAdapter.getApiService();
                Call<Reserva> call = myApiService.createReserva(reserva);
                call.enqueue(new Callback<Reserva>() {
                    @Override
                    public void onResponse(Call<Reserva> call, Response<Reserva> response) {
                        if (response.isSuccessful()){

                            Toast.makeText(getContext(),"Reserva Realizada con éxito", Toast.LENGTH_LONG).show();

                        }else {
                            System.out.println(response.errorBody());
                        }
                    }

                    @Override
                    public void onFailure(Call<Reserva> call, Throwable t) {

                    }
                });

            }else{
                Toast.makeText(this.getContext(), "Número de personas mayor a lo debido", Toast.LENGTH_SHORT).show();
            }

        }else{
            Toast.makeText(this.getContext(), "Requerido el número de adultos", Toast.LENGTH_SHORT).show();
        }


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

            case R.id.btnConfirmReserva:
                createReserva();
            break;
        }
    }
}