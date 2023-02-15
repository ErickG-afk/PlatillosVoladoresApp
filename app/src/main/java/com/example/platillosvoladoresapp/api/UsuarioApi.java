package com.example.platillosvoladoresapp.api;


import com.example.platillosvoladoresapp.entity.GenericResponse;
import com.example.platillosvoladoresapp.entity.service.Usuario;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface UsuarioApi {

    //Ruta del Controlador usuario
    String base = "api/usuario";

    //Ruta del controlador usuario + Ruta del metodo
    @FormUrlEncoded
    @POST(base + "/login")
    Call<GenericResponse<Usuario>> login(@Field("email") String email, @Field("password")String password);

    @POST(base)
    Call<GenericResponse<Usuario>> save (@Body Usuario u);
}
