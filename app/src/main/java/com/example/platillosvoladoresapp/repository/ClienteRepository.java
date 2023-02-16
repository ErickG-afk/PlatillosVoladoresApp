package com.example.platillosvoladoresapp.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.platillosvoladoresapp.api.ClienteApi;
import com.example.platillosvoladoresapp.api.ConfigApi;
import com.example.platillosvoladoresapp.entity.GenericResponse;
import com.example.platillosvoladoresapp.entity.service.Cliente;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClienteRepository {
    private static ClienteRepository repository;
    private final ClienteApi clienteApi;

    public static ClienteRepository getInstance() {
        if(repository == null)
            repository = new ClienteRepository();

        return repository;
    }

    private ClienteRepository(){
        clienteApi = ConfigApi.getClienteApi();
    }

    public LiveData<GenericResponse<Cliente>> guardarCliente(Cliente cliente){
        final MutableLiveData<GenericResponse<Cliente>> mld = new MutableLiveData<>();
        this.clienteApi.guardarCliente(cliente).enqueue(new Callback<GenericResponse<Cliente>>() {
            @Override
            public void onResponse(Call<GenericResponse<Cliente>> call, Response<GenericResponse<Cliente>> response) {
                mld.setValue(response.body());
            }

            @Override
            public void onFailure(Call<GenericResponse<Cliente>> call, Throwable t) {
                mld.setValue(new GenericResponse<>());
                System.out.println("Se ha producido un error: " + t.getMessage());
                t.printStackTrace();
            }
        });
        return mld;
    }

}
