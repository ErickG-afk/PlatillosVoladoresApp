package com.example.platillosvoladoresapp.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.platillosvoladoresapp.api.ConfigApi;
import com.example.platillosvoladoresapp.api.PedidoApi;
import com.example.platillosvoladoresapp.entity.GenericResponse;
import com.example.platillosvoladoresapp.entity.service.dto.GenerarPedidoDTO;
import com.example.platillosvoladoresapp.entity.service.dto.PedidoConDetalleDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PedidoRepository {
    private final PedidoApi api;
    private static PedidoRepository repository;

    public PedidoRepository() {
        this.api = ConfigApi.getPedidoApi();
    }

    public static PedidoRepository getInstance(){
        if (repository == null)
            repository = new PedidoRepository();
        return repository;
    }

    public LiveData<GenericResponse<List<PedidoConDetalleDTO>>> listarPedidosPorCliente(int idCliente){
        final MutableLiveData<GenericResponse<List<PedidoConDetalleDTO>>> mld = new MutableLiveData<>();
        this.api.listarPedidosPorCliente(idCliente).enqueue(new Callback<GenericResponse<List<PedidoConDetalleDTO>>>() {
            @Override
            public void onResponse(Call<GenericResponse<List<PedidoConDetalleDTO>>> call, Response<GenericResponse<List<PedidoConDetalleDTO>>> response) {
                mld.setValue(response.body());
            }

            @Override
            public void onFailure(Call<GenericResponse<List<PedidoConDetalleDTO>>> call, Throwable t) {
                mld.setValue(new GenericResponse());
                System.out.println("Error al obtener los pedidos: " + t.getMessage());
                t.printStackTrace();
            }
        });
        return mld;
    }
    public LiveData<GenericResponse<GenerarPedidoDTO>> save(GenerarPedidoDTO dto) {
        MutableLiveData<GenericResponse<GenerarPedidoDTO>> data = new MutableLiveData<>();
        api.guardarPedido(dto).enqueue(new Callback<GenericResponse<GenerarPedidoDTO>>() {
            @Override
            public void onResponse(Call<GenericResponse<GenerarPedidoDTO>> call, Response<GenericResponse<GenerarPedidoDTO>> response) {
                if (response.isSuccessful()) {
                    data.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<GenericResponse<GenerarPedidoDTO>> call, Throwable t) {
                data.setValue(new GenericResponse<>());
                t.printStackTrace();
            }
        });
        return data;
    }


}
