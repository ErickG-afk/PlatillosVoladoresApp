package com.example.platillosvoladoresapp.api;

import com.example.platillosvoladoresapp.entity.GenericResponse;
import com.example.platillosvoladoresapp.entity.service.Platillo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface PlatoApi {
    String base = "api/platillo";

    @GET(base)
    Call<GenericResponse<List<Platillo>>> listarPlatosRecomendados();

    @GET(base + "/{idC}")
    Call<GenericResponse<List<Platillo>>> listarPlatoPorCategora(@Path("idC") int idC);
}
