package com.ruta_del_valle.app_hosteria;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ruta_del_valle.app_hosteria.rest_api.io.MyApiAdapter;
import com.ruta_del_valle.app_hosteria.rest_api.io.MyApiService;
import com.ruta_del_valle.app_hosteria.rest_api.model.Mensaje;
import com.ruta_del_valle.app_hosteria.rest_api.model.NuevoUsuario;

import javax.xml.validation.Validator;

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
                etDni.setError(null);
                etTelefono.setError(null);
                String tele= etTelefono.getText().toString();
                String nume= etDni.getText().toString();


                if (nume.length()  >= 11) {
                    etDni.setError("Cédula fuera de rango");
                    etDni.requestFocus();
                } else {
                    if (tele.length()  >= 11){
                        etTelefono.setError("Teléfono fuera de rango");
                        etTelefono.requestFocus();
                    }else{
                        callRegisterService();
                    }



                }


            }
        });

    }

    public void pantallaLogin(View view){
        finish();
    }

    protected void callRegisterService(){

        String dni = etDni.getText().toString().trim();
        String nombre = etNombre.getText().toString();
        String email = etEmail.getText().toString();
        String telefono = etTelefono.getText().toString().trim();
        String username = etUsername.getText().toString();
        String password = etpassword.getText().toString();

        if (dni.isEmpty() ){
            Toast.makeText(this.getApplicationContext(),"Campo cédula vacío",Toast.LENGTH_SHORT).show();
        }else{
                if (nombre.isEmpty()){
                    Toast.makeText(this.getApplicationContext(),"Campo nombre vacío",Toast.LENGTH_SHORT).show();
                }else{
                    if (email.isEmpty()){
                        Toast.makeText(this.getApplicationContext(),"Campo email vacío",Toast.LENGTH_SHORT).show();
                    }else{
                        if (telefono.isEmpty() ){
                            Toast.makeText(this.getApplicationContext(),"Campo teléfono vacío",Toast.LENGTH_SHORT).show();
                        }else{
                            if (username.isEmpty()){
                                Toast.makeText(this.getApplicationContext(),"Campo username vacío",Toast.LENGTH_SHORT).show();
                            }else{
                                if (password.isEmpty()){
                                    Toast.makeText(this.getApplicationContext(),"Campo password vacío",Toast.LENGTH_SHORT).show();
                                }else{
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
                                                Toast.makeText(getApplicationContext(), "Se produjo un error al guardar", Toast.LENGTH_SHORT).show();
                                                System.out.println(response.errorBody());
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<Mensaje> call, Throwable t) {
                                            System.out.println(t.getMessage());
                                        }
                                    });

                                }
                            }
                        }
                    }
                }
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