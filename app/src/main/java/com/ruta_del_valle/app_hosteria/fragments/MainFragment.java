package com.ruta_del_valle.app_hosteria.fragments;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ruta_del_valle.app_hosteria.R;
import com.ruta_del_valle.app_hosteria.adapters.HabitacionAdapter;
import com.ruta_del_valle.app_hosteria.fragments.bridge.BridgeHabitacionToDetalle;
import com.ruta_del_valle.app_hosteria.rest_api.io.MyApiAdapter;
import com.ruta_del_valle.app_hosteria.rest_api.io.MyApiService;
import com.ruta_del_valle.app_hosteria.rest_api.model.Habitacion;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainFragment extends Fragment {

    //variables para el Recycler View
    HabitacionAdapter habitacionAdapter;
    RecyclerView recyclerView;
    //ArrayList<Habitacion> listHabitaciones;

    //referencias para comunicar fragments
    Activity activity;
    BridgeHabitacionToDetalle bridgeHabitacionToDetalle; //comunica Fragments



    //variables para Retrofit
    private MyApiAdapter myApiAdapter;

    public MainFragment(){
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_fragment,container,false);
        recyclerView = view.findViewById(R.id.recyclerViewHb);

        myApiAdapter = new MyApiAdapter();

        //listHabitaciones = new ArrayList<>();

        //cargarLista
        searchData();

        return view;
    }

    protected void searchData(){

        MyApiService myApiService = myApiAdapter.getApiService();
        Call<List<Habitacion>> call = myApiService.getHabitaciones();
        call.enqueue(new Callback<List<Habitacion>>() {
            @Override
            public void onResponse(Call<List<Habitacion>> call, Response<List<Habitacion>> response) {
                if (response.isSuccessful()){

                    List<Habitacion> habitacionesResponse = response.body();

                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    habitacionAdapter = new HabitacionAdapter(getContext(),habitacionesResponse);
                    recyclerView.setAdapter(habitacionAdapter);

                    habitacionAdapter.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            int num_habitacion =  habitacionesResponse.get(recyclerView.getChildAdapterPosition(view)).getNum_habitacion();
                            Toast.makeText(getContext(),"Habitación Nº"+num_habitacion+" seleccionada",Toast.LENGTH_SHORT).show();

                            //Recuperamos todo el objeto
                            bridgeHabitacionToDetalle.enviarHabitacion(habitacionesResponse
                            .get(recyclerView.getChildAdapterPosition(view)));

                        }
                    });
                }else{
                    System.out.println(response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<List<Habitacion>> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof Activity){
            this.activity = (Activity) context;
            bridgeHabitacionToDetalle = (BridgeHabitacionToDetalle) this.activity;
        }

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
