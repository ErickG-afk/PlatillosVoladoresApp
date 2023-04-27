package com.example.platillosvoladoresapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.platillosvoladoresapp.entity.GenericResponse;
import com.example.platillosvoladoresapp.entity.service.Platillo;
import com.example.platillosvoladoresapp.repository.PlatoRepository;

import java.util.List;

public class PlatoViewModel extends AndroidViewModel {
    private final PlatoRepository repository;


    public PlatoViewModel(@NonNull Application application) {
        super(application);
        repository = PlatoRepository.getInstance();
    }

    public LiveData<GenericResponse<List<Platillo>>> listarPlatosRecomendados(){
        return this.repository.listarPlatosRecomendados();
    }
    public LiveData<GenericResponse<List<Platillo>>> listarPlatosPorCategoria(int idC){
        return this.repository.listarPlatosPorCategoria(idC);
    }

}
