package com.ruta_del_valle.app_hosteria.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ruta_del_valle.app_hosteria.R;
import com.ruta_del_valle.app_hosteria.rest_api.model.MiReserva;

import java.util.List;

public class MisReservasAdapter extends RecyclerView.Adapter<MisReservasAdapter.ViewHolder> {

    List<MiReserva> misReservas;
    LayoutInflater inflater;

    public MisReservasAdapter(Context context,List<MiReserva> misReservas){

        this.inflater = LayoutInflater.from(context);
        this.misReservas = misReservas;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.lista_misreservas,parent,false);

        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String num_habitacion = Integer.toString(misReservas.get(position).getNum_habitacion());
        String tipo_habitacion = misReservas.get(position).getTipo_habitacion();
        String adultos = Integer.toString(misReservas.get(position).getAdultos());
        String ninos = Integer.toString(misReservas.get(position).getNinos());
        String ingreso = misReservas.get(position).getFecha_ingreso();
        String salida = misReservas.get(position).getFecha_salida();
        String costo_alojamiento = Double.toString(misReservas.get(position).getCosto_alojamiento());


        holder.infoHabitacion.setText("Habitación Nº"+num_habitacion + " | " + tipo_habitacion);
        holder.personas.setText(adultos + " adultos - " + ninos + " niños");
        holder.ingreso.setText(ingreso);
        holder.salida.setText(salida);
        holder.costo.setText("$ "+costo_alojamiento);
        holder.imagen.setImageResource(R.drawable.assigments_icon);


    }

    @Override
    public int getItemCount() {
        return misReservas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView infoHabitacion, personas, ingreso, salida, costo;
        ImageView imagen;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            infoHabitacion = itemView.findViewById(R.id.tvInfoHb);
            personas = itemView.findViewById(R.id.tvPersonas);
            ingreso = itemView.findViewById(R.id.tvIngreso);
            salida = itemView.findViewById(R.id.tvSalida);
            costo = itemView.findViewById(R.id.tvCostoMiReserva);
            imagen = itemView.findViewById(R.id.ivAssigment);

        }

    }
}
