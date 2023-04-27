package com.example.platillosvoladoresapp.api;

import com.example.platillosvoladoresapp.entity.GenericResponse;
import com.example.platillosvoladoresapp.entity.service.dto.GenerarPedidoDTO;
import com.example.platillosvoladoresapp.entity.service.dto.PedidoConDetalleDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface PedidoApi {
    String base = "api/pedido";
    @GET(base + "/misPedidos/{idCliente}")
    Call<GenericResponse<List<PedidoConDetalleDTO>>> listarPedidosPorCliente(@Path("idCliente") int idCliente);

    @POST(base)
    Call<GenericResponse<GenerarPedidoDTO>>guardarPedido(@Body GenerarPedidoDTO dto);
}
