package com.example.platillosvoladoresapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.platillosvoladoresapp.R;
import com.example.platillosvoladoresapp.adapter.PlatosPorCategoriaAdapter;
import com.example.platillosvoladoresapp.communication.MostrarBadgeCommunication;
import com.example.platillosvoladoresapp.entity.service.DetallePedido;
import com.example.platillosvoladoresapp.entity.service.Platillo;
import com.example.platillosvoladoresapp.utils.Carrito;
import com.example.platillosvoladoresapp.viewmodel.PlatoViewModel;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class ListarPlatosPorCategoriaActivity extends AppCompatActivity implements MostrarBadgeCommunication {
    private PlatoViewModel platoViewModel;
    private PlatosPorCategoriaAdapter adapter;
    private List<Platillo> platos = new ArrayList<>();
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
        adapter = new PlatosPorCategoriaAdapter(platos, this);
        rcvPlatoPorCategoria = findViewById(R.id.rcvPlatillosPorCategoria);
        rcvPlatoPorCategoria.setAdapter(adapter);
        rcvPlatoPorCategoria.setLayoutManager(new LinearLayoutManager(this));
        //rcvPlatoPorCategoria.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
    }

    private void loadData() {
        int idC = getIntent().getIntExtra("idC", 0);//Recibimos el idCategoria
        platoViewModel.listarPlatosPorCategoria(idC).observe(this, response -> {
            adapter.updateItems(response.getBody());
        });
    }


    @Override
    public void add(DetallePedido dp) {
        successMessage(Carrito.agregarPlatillos(dp));

    }
    public void successMessage(String message) {
        new SweetAlertDialog(this,
                SweetAlertDialog.SUCCESS_TYPE).setTitleText("Buen Trabajo!")
                .setContentText(message).show();
    }
}