package com.example.platillosvoladoresapp.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.platillosvoladoresapp.api.ConfigApi;
import com.example.platillosvoladoresapp.api.PlatoApi;
import com.example.platillosvoladoresapp.entity.GenericResponse;
import com.example.platillosvoladoresapp.entity.service.Platillo;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlatoRepository {
    private final PlatoApi api;
    private static PlatoRepository repository;

    public PlatoRepository() {
        this.api = ConfigApi.getPlatoApi();
    }

    public static PlatoRepository getInstance(){
        if (repository == null)
            repository = new PlatoRepository();
        return repository;
    }
    public LiveData<GenericResponse<List<Platillo>>> listarPlatosRecomendados(){
        final MutableLiveData<GenericResponse<List<Platillo>>> mld = new MutableLiveData<>();
        this.api.listarPlatosRecomendados().enqueue(new Callback<GenericResponse<List<Platillo>>>() {
            @Override
            public void onResponse(Call<GenericResponse<List<Platillo>>> call, Response<GenericResponse<List<Platillo>>> response) {
                mld.setValue(response.body());
            }

            @Override
            public void onFailure(Call<GenericResponse<List<Platillo>>> call, Throwable t) {
                mld.setValue(new GenericResponse<>());
                t.printStackTrace();
            }
        });
        return mld;
    }

    public LiveData<GenericResponse<List<Platillo>>> listarPlatosPorCategoria(int idC){
        final MutableLiveData<GenericResponse<List<Platillo>>> mld = new MutableLiveData<>();
        this.api.listarPlatoPorCategora(idC).enqueue(new Callback<GenericResponse<List<Platillo>>>() {
            @Override
            public void onResponse(Call<GenericResponse<List<Platillo>>> call, Response<GenericResponse<List<Platillo>>> response) {
                mld.setValue(response.body());
            }

            @Override
            public void onFailure(Call<GenericResponse<List<Platillo>>> call, Throwable t) {
                mld.setValue(new GenericResponse<>());
                t.printStackTrace();
            }
        });
        return mld;
    }
}
