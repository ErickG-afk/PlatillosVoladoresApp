package com.example.platillosvoladoresapp.api;

import com.example.platillosvoladoresapp.entity.GenericResponse;
import com.example.platillosvoladoresapp.entity.service.Categoria;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CategoriaAPI {
    String base = "api/category";

    @GET(base)
    Call<GenericResponse<List<Categoria>>> listarCategoriasActivas();
}
