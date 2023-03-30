package com.example.platillosvoladoresapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.platillosvoladoresapp.entity.GenericResponse;
import com.example.platillosvoladoresapp.entity.service.dto.PedidoConDetalleDTO;
import com.example.platillosvoladoresapp.repository.PedidoRepository;

import java.util.List;

public class PedidoViewModel extends AndroidViewModel {

    private final PedidoRepository repository;

    public PedidoViewModel(@NonNull Application application) {
        super(application);
        this.repository = PedidoRepository.getInstance();
    }
    public LiveData<GenericResponse<List<PedidoConDetalleDTO>>> listarPedidosPorCliente(int idCliente){
        return this.repository.listarPedidosPorCliente(idCliente);
    }
}
