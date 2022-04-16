package com.ruta_del_valle.app_hosteria.fragments;

import static com.ruta_del_valle.app_hosteria.MainActivity.usernameToSend;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ruta_del_valle.app_hosteria.R;
import com.ruta_del_valle.app_hosteria.adapters.MisReservasAdapter;
import com.ruta_del_valle.app_hosteria.rest_api.io.MyApiAdapter;
import com.ruta_del_valle.app_hosteria.rest_api.io.MyApiService;
import com.ruta_del_valle.app_hosteria.rest_api.model.MiReserva;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MisReservas_Fragment extends Fragment {

    MisReservasAdapter misReservasAdapter;
    RecyclerView recyclerView;
    String usernameReceived;


    //variables para Retrofit
    private MyApiAdapter myApiAdapter;

    public MisReservas_Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mis_reservas_, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewMisReservas);
        myApiAdapter = new MyApiAdapter();
        usernameReceived = usernameToSend;

        //cargarLista
        loadInfo();

        return view;
    }

    protected void loadInfo(){

        MyApiService myApiService = myApiAdapter.getApiService();
        Call<List<MiReserva>> call = myApiService.infoReservaByUsername(usernameReceived);
        call.enqueue(new Callback<List<MiReserva>>() {
            @Override
            public void onResponse(Call<List<MiReserva>> call, Response<List<MiReserva>> response) {
                if (response.isSuccessful()){
                    List<MiReserva> misreservas = response.body();

                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    misReservasAdapter = new MisReservasAdapter(getContext(), misreservas);
                    recyclerView.setAdapter(misReservasAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<MiReserva>> call, Throwable t) {

            }
        });

    }
}