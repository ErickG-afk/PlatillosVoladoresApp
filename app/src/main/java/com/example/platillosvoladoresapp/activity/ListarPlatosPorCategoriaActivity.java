package com.example.platillosvoladoresapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.platillosvoladoresapp.R;
import com.example.platillosvoladoresapp.adapter.PlatosPorCategoriaAdapter;
import com.example.platillosvoladoresapp.entity.service.Plato;
import com.example.platillosvoladoresapp.viewmodel.PlatoViewModel;

import java.util.ArrayList;
import java.util.List;

public class ListarPlatosPorCategoriaActivity extends AppCompatActivity {
    private PlatoViewModel platoViewModel;
    private PlatosPorCategoriaAdapter adapter;
    private List<Plato> platos = new ArrayList<>();
    private RecyclerView rcvPlatoPorCategoria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_platos_por_categoria);

        init();
        initViewModel();
        initAdapter();
        loadData();
    }
    private void init() {
        Toolbar toolbar = this.findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_volver_atras);
        toolbar.setNavigationOnClickListener(v -> {
            this.finish();
            this.overridePendingTransition(R.anim.rigth_in, R.anim.rigth_out);
        });
    }

    private void initViewModel() {
        final ViewModelProvider vmp = new ViewModelProvider(this);
        this.platoViewModel = vmp.get(PlatoViewModel.class);
    }

    private void initAdapter() {
        adapter = new PlatosPorCategoriaAdapter(platos);
        rcvPlatoPorCategoria = findViewById(R.id.rcvPlatillosPorCategoria);
        rcvPlatoPorCategoria.setAdapter(adapter);
        rcvPlatoPorCategoria.setLayoutManager(new LinearLayoutManager(this));
        //rcvPlatilloPorCategoria.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
    }

    private void loadData() {
        int idC = getIntent().getIntExtra("idC", 0);//Recibimos el idCategoria
        platoViewModel.listarPlatosPorCategoria(idC).observe(this, response -> {
            adapter.updateItems(response.getBody());
        });
    }
}