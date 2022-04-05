package com.ruta_del_valle.app_hosteria.rest_api.io;

import com.ruta_del_valle.app_hosteria.rest_api.model.LoginUsuario;
import com.ruta_del_valle.app_hosteria.rest_api.model.Mensaje;
import com.ruta_del_valle.app_hosteria.rest_api.model.NuevoUsuario;
import com.ruta_del_valle.app_hosteria.rest_api.security.JwtDto;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface MyApiService {

    @POST("auth/login")
    Call<JwtDto> loginPeticion(@Body LoginUsuario loginUsuario);

    @POST("auth/nuevo")
    Call<Mensaje> newUser(@Body NuevoUsuario nuevoUsuario);

}
