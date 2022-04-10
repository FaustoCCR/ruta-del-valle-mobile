package com.ruta_del_valle.app_hosteria.fragments;

import static com.ruta_del_valle.app_hosteria.MainActivity.usernameToSend;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ruta_del_valle.app_hosteria.R;
import com.ruta_del_valle.app_hosteria.rest_api.io.MyApiAdapter;
import com.ruta_del_valle.app_hosteria.rest_api.io.MyApiService;
import com.ruta_del_valle.app_hosteria.rest_api.model.NuevoUsuario;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PerfilFragment extends Fragment implements View.OnClickListener {

    //variables para Retrofit
    private MyApiAdapter myApiAdapter;
    private String usernameReceived;

    //componentes de la vista
    EditText etNombre,etCedula,etUsername,etEmail,etTelefono,etPassword;
    Button btEditar;

    //variable id_user
    long id_user;

    //Objeto Usuario
    NuevoUsuario usuarioResponse;
    public PerfilFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_perfil, container, false);
        myApiAdapter = new MyApiAdapter();

        usuarioResponse = new NuevoUsuario();

        //Instanciamos variables
        etNombre = view.findViewById(R.id.eteNombre);
        etCedula = view.findViewById(R.id.eteDni);
        etUsername = view.findViewById(R.id.eteUsername);
        etEmail = view.findViewById(R.id.eteEmail);
        etTelefono = view.findViewById(R.id.eteTelefono);
        etPassword = view.findViewById(R.id.etePassword);
        btEditar = view.findViewById(R.id.btnEditar);

        etNombre.clearFocus();
        btEditar.setOnClickListener(this);

        usernameReceived = usernameToSend;
        searchData();

        return view;
    }


    protected void searchData(){

        MyApiService myApiService = myApiAdapter.getApiService();
        Call<NuevoUsuario> call = myApiService.getUsuariobyUsername(usernameReceived);
        call.enqueue(new Callback<NuevoUsuario>() {
            @Override
            public void onResponse(Call<NuevoUsuario> call, Response<NuevoUsuario> response) {
                if (response.isSuccessful()){
                    usuarioResponse = response.body();

                    fillData(usuarioResponse);

                }else{
                    System.out.println(response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<NuevoUsuario> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });
    }


    protected void fillData(NuevoUsuario usuario){

        etNombre.setText(usuario.getNombre());
        etCedula.setText(usuario.getDni());
        etUsername.setText(usuario.getUsername());
        etEmail.setText(usuario.getEmail());
        etTelefono.setText(usuario.getTelefono());

    }

    protected void updateData(){

        String dni = etCedula.getText().toString();
        String nombre = etNombre.getText().toString();
        String email = etEmail.getText().toString();
        String telefono = etTelefono.getText().toString();
        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();
        boolean estado = true;
        String rol = "user";

        if (!dni.isEmpty() && !nombre.isEmpty() && !email.isEmpty() && !telefono.isEmpty() &&
                !username.isEmpty()){
            if (!password.isEmpty()) {
                NuevoUsuario usuarioUpdated = new NuevoUsuario(dni, nombre, email, telefono, username, password, estado, rol);

                MyApiService myApiService = myApiAdapter.getApiService();
                Call<NuevoUsuario> call = myApiService.updateUsuario(usuarioResponse.getId_user(), usuarioUpdated);
                call.enqueue(new Callback<NuevoUsuario>() {
                    @Override
                    public void onResponse(Call<NuevoUsuario> call, Response<NuevoUsuario> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(getContext(), "Usurio Actualizado", Toast.LENGTH_SHORT).show();
                        } else {
                            System.out.println(response.errorBody());
                        }

                    }

                    @Override
                    public void onFailure(Call<NuevoUsuario> call, Throwable t) {
                        System.out.println(t.getMessage());
                    }
                });
            }else{
                Toast.makeText(getContext(),"Confirma tu constraseña o ingresa una nueva",Toast.LENGTH_LONG).show();
            }
        }else{
            Toast.makeText(this.getContext(), "Ingrese los datos solicitados", Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    public void onClick(View view) {
        updateData();
    }
}