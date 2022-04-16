package com.ruta_del_valle.app_hosteria.rest_api.io;

import com.ruta_del_valle.app_hosteria.rest_api.model.Habitacion;
import com.ruta_del_valle.app_hosteria.rest_api.model.LoginUsuario;
import com.ruta_del_valle.app_hosteria.rest_api.model.Mensaje;
import com.ruta_del_valle.app_hosteria.rest_api.model.MiReserva;
import com.ruta_del_valle.app_hosteria.rest_api.model.NuevoUsuario;
import com.ruta_del_valle.app_hosteria.rest_api.model.Reserva;
import com.ruta_del_valle.app_hosteria.rest_api.security.JwtDto;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MyApiService {

    @POST("auth/login")
    Call<JwtDto> loginPeticion(@Body LoginUsuario loginUsuario);

    @POST("auth/nuevo")
    Call<Mensaje> newUser(@Body NuevoUsuario nuevoUsuario);

    //consulta de habitaciones disponibles
    @GET("api/habitaciones")
    Call<List<Habitacion>> getHabitaciones(@Query("estado") String estado);

    @GET("api/users/usuario/{username}")
    Call<NuevoUsuario> getUsuariobyUsername(@Path("username") String username);

    //Actualizar usuario
    @PUT("api/users/{id_user}")
    Call<NuevoUsuario> updateUsuario(@Path("id_user")long id_user, @Body NuevoUsuario usuario);

    //Crear Reserva
    @POST("api/reserva")
    Call<Reserva> createReserva(@Body Reserva reserva);


    //Mis Reservas
    @GET("api/reserva/usuario/{username}")
    Call<List<MiReserva>> infoReservaByUsername(@Path("username") String username);



}
