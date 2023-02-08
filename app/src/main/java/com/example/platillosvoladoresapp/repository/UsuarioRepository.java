package com.example.platillosvoladoresapp.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.platillosvoladoresapp.api.ConfigApi;
import com.example.platillosvoladoresapp.api.UsuarioApi;
import com.example.platillosvoladoresapp.entity.GenericResponse;
import com.example.platillosvoladoresapp.entity.service.Usuario;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UsuarioRepository {

    private static UsuarioRepository usuarioRepository;
    private final UsuarioApi usuarioApi;

    public UsuarioRepository() {
        this.usuarioApi = ConfigApi.getUsuarioApi();
    }

    public static UsuarioRepository getInstance()
    {
        if(usuarioRepository == null)
            usuarioRepository = new UsuarioRepository();
        return usuarioRepository;
    }


    //METODO LOG IN
    public LiveData<GenericResponse<Usuario>> login(String email, String password)
    {
        final MutableLiveData<GenericResponse<Usuario>> mld = new MutableLiveData<>();
        this.usuarioApi.login(email,password).enqueue(new Callback<GenericResponse<Usuario>>() {
            @Override
            public void onResponse(Call<GenericResponse<Usuario>> call, Response<GenericResponse<Usuario>> response) {
                mld.setValue(response.body());
            }

            @Override
            public void onFailure(Call<GenericResponse<Usuario>> call, Throwable t) {
                mld.setValue(new GenericResponse());
                System.out.println("Se ha producido un error al iniciar sesion: " + t.getMessage());
                t.printStackTrace();
            }
        });
        return mld;
    }
}
