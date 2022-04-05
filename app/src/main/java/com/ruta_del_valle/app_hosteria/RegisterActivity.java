package com.ruta_del_valle.app_hosteria;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ruta_del_valle.app_hosteria.rest_api.io.MyApiAdapter;
import com.ruta_del_valle.app_hosteria.rest_api.io.MyApiService;
import com.ruta_del_valle.app_hosteria.rest_api.model.Mensaje;
import com.ruta_del_valle.app_hosteria.rest_api.model.NuevoUsuario;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private MyApiAdapter myApiAdapter;

    private EditText etDni, etNombre, etEmail, etTelefono, etUsername, etpassword;
    private Button btRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etDni = findViewById(R.id.editTextDNI);
        etNombre = findViewById(R.id.editTName);
        etEmail = findViewById(R.id.editTextEmail);
        etTelefono = findViewById(R.id.editTelefono);
        etUsername = findViewById(R.id.editUsername);
        etpassword = findViewById(R.id.editTPassword);

        btRegister = findViewById(R.id.buttonRegister);

        myApiAdapter = new MyApiAdapter();
        btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callRegisterService();
            }
        });

    }

    public void pantallaLogin(View view){
        finish();
    }

    protected void callRegisterService(){

        String dni = etDni.getText().toString();
        String nombre = etNombre.getText().toString();
        String email = etEmail.getText().toString();
        String telefono = etTelefono.getText().toString();
        String username = etUsername.getText().toString();
        String password = etpassword.getText().toString();

        if (!dni.isEmpty() && !nombre.isEmpty() && !email.isEmpty() && !telefono.isEmpty() &&
        !username.isEmpty() && !password.isEmpty()){

            NuevoUsuario nuevoUsuario = new NuevoUsuario(dni,nombre,email,telefono,username,password,"user");
            MyApiService myApiService = myApiAdapter.getApiService();
            Call<Mensaje> call = myApiService.newUser(nuevoUsuario);
            call.enqueue(new Callback<Mensaje>() {
                @Override
                public void onResponse(Call<Mensaje> call, Response<Mensaje> response) {
                    if (response.isSuccessful()){
                        Toast.makeText(getApplicationContext(), response.body().getMensaje(), Toast.LENGTH_SHORT).show();
                        cleanFields();
                        finish();
                    }else{
                        Toast.makeText(getApplicationContext(),String.valueOf(response.errorBody()),Toast.LENGTH_SHORT).show();
                        System.out.println(response.errorBody());
                    }
                }

                @Override
                public void onFailure(Call<Mensaje> call, Throwable t) {
                    System.out.println(t.getMessage());
                }
            });

        }else{
            Toast.makeText(this.getApplicationContext(), "Ingrese los datos solicitados", Toast.LENGTH_SHORT).show();
        }
    }

    private void cleanFields(){
        etDni.setText("");
        etNombre.setText("");
        etEmail.setText("");
        etTelefono.setText("");
        etUsername.setText("");
        etpassword.setText("");
    }
}