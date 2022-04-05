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
import com.ruta_del_valle.app_hosteria.rest_api.model.LoginUsuario;
import com.ruta_del_valle.app_hosteria.rest_api.security.JwtDto;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private MyApiAdapter myApiAdapter;

    EditText etUsername, etPassword;
    Button btnSignin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSignin = findViewById(R.id.buttonRegister);
        etUsername = findViewById(R.id.editTName);
        etPassword = findViewById(R.id.editTPassword);

        myApiAdapter = new MyApiAdapter();

        btnSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callLoginService();
            }
        });


    }

    public void ingresar() {

        Intent intent = new Intent(this, NavigationDrawer.class);
        startActivity(intent);
    }

    public void crearCuenta(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    protected void callLoginService() {

        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();

        if (!username.isEmpty() && !password.isEmpty()) {


            LoginUsuario loginUsuario = new LoginUsuario(username, password);

            MyApiService myApiService = myApiAdapter.getApiService();
            Call<JwtDto> call = myApiService.loginPeticion(loginUsuario);
            call.enqueue(new Callback<JwtDto>() {
                @Override
                public void onResponse(Call<JwtDto> call, Response<JwtDto> response) {
                    if (response.isSuccessful()) {

                        Toast.makeText(MainActivity.this, "Bienvenido " + response.body().getUsername(), Toast.LENGTH_SHORT).show();
                        cleanFields();
                        ingresar();
                    } else {
                        System.out.println(response.errorBody());
                        Toast.makeText(MainActivity.this,"Credenciales incorrectas", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<JwtDto> call, Throwable t) {
                    System.out.println(t.getMessage());
                }
            });

        } else {
            Toast.makeText(MainActivity.this, "Ingrese los datos solicitados", Toast.LENGTH_SHORT).show();
        }

    }

    private void cleanFields(){

        etUsername.setText("");
        etPassword.setText("");
    }
}