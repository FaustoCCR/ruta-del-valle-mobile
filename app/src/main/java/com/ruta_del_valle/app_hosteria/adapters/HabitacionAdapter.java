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
import com.ruta_del_valle.app_hosteria.rest_api.model.Habitacion;
import java.util.List;

public class HabitacionAdapter extends RecyclerView.Adapter<HabitacionAdapter.ViewHolder> implements View.OnClickListener {

    List<Habitacion> habitaciones;
    LayoutInflater inflater;

    //listener
    private View.OnClickListener listener;

    public HabitacionAdapter(Context context, List<Habitacion> habitaciones){

        this.inflater = LayoutInflater.from((context));
        this.habitaciones = habitaciones;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){

        View view = inflater.inflate(R.layout.lista_habitaciones,parent,false);
        view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int num_habitacion = habitaciones.get(position).getNum_habitacion();
        String tipo_habitacion = habitaciones.get(position).getTipo_habitacion();
        double costo_noche = habitaciones.get(position).getCosto_noche();

        //establecemos los valores obtenidos
        holder.num_habitacion.setText("Habitación "+String.valueOf(num_habitacion));
        holder.tipo_habitacion.setText(tipo_habitacion);
        holder.costo_noche.setText("$ "+String.valueOf(costo_noche));
        holder.imagen.setImageResource(R.drawable.door_icon);

    }

    @Override
    public int getItemCount() {
        return habitaciones.size();
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {
        if (listener!=null){
            listener.onClick(view);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView num_habitacion,tipo_habitacion,costo_noche;
        ImageView imagen;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            num_habitacion = itemView.findViewById(R.id.tVNumHabitacion);
            tipo_habitacion = itemView.findViewById(R.id.tVTipoHabitacion);
            costo_noche = itemView.findViewById(R.id.tVCosto);
            imagen = itemView.findViewById(R.id.iVHabitacion);

        }
    }
}
