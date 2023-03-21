package com.example.platillosvoladoresapp.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.platillosvoladoresapp.api.CategoriaAPI;
import com.example.platillosvoladoresapp.api.ConfigApi;
import com.example.platillosvoladoresapp.entity.GenericResponse;
import com.example.platillosvoladoresapp.entity.service.Categoria;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoriaRepository {
    private final CategoriaAPI categoriaAPI;
    private static CategoriaRepository categoriaRepository;

    public CategoriaRepository() {
        this.categoriaAPI = ConfigApi.getCategoriaAPI();
    }

    public static CategoriaRepository getInstance() {
        if (categoriaRepository == null) {
            categoriaRepository = new CategoriaRepository();
        }
        return categoriaRepository;
    }

    public LiveData<GenericResponse<List<Categoria>>> listarCategoriasActivas(){
        final MutableLiveData<GenericResponse<List<Categoria>>> mld = new MutableLiveData<>();
        this.categoriaAPI.listarCategoriasActivas().enqueue(new Callback<GenericResponse<List<Categoria>>>() {
            @Override
            public void onResponse(Call<GenericResponse<List<Categoria>>> call, Response<GenericResponse<List<Categoria>>> response) {
                mld.setValue(response.body());
            }

            @Override
            public void onFailure(Call<GenericResponse<List<Categoria>>> call, Throwable t) {
                System.out.println("Error al obtener categorias: " + t.getMessage());
                t.printStackTrace();
            }
        });
        return mld;
    }
}

