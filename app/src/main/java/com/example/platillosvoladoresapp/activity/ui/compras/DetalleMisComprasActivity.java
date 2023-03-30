package com.example.platillosvoladoresapp.activity.ui.compras;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.platillosvoladoresapp.R;
import com.example.platillosvoladoresapp.adapter.DetalleMisComprasAdapter;
import com.example.platillosvoladoresapp.entity.service.DetallePedido;
import com.example.platillosvoladoresapp.utils.DateSerializer;
import com.example.platillosvoladoresapp.utils.TimeSerializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class DetalleMisComprasActivity extends AppCompatActivity {
    private RecyclerView rcvDetallesMisCompras;
    private DetalleMisComprasAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_mis_compras);
        init();
        initAdapter();
        loadData();
    }

    private void init() {
        rcvDetallesMisCompras = findViewById(R.id.rcvDetalleMisCompras);
        rcvDetallesMisCompras.setLayoutManager(new GridLayoutManager(this, 1));
        Toolbar toolbar = this.findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_volver_atras);
        toolbar.setNavigationOnClickListener(v -> {
            this.onBackPressed();
        });
    }

    private void initAdapter() {
        adapter = new DetalleMisComprasAdapter(new ArrayList<>());
        rcvDetallesMisCompras.setAdapter(adapter);
    }

    private void loadData() {
        final String detalleString = this.getIntent().getStringExtra("detailsPurchases");
        if (detalleString != null) {
            final Gson g = new GsonBuilder()
                    .registerTypeAdapter(Date.class, new DateSerializer())
                    .registerTypeAdapter(Time.class, new TimeSerializer())
                    .create();
            List<DetallePedido> detalles = g.fromJson(detalleString,
                    new TypeToken<List<DetallePedido>>() {
                    }.getType());
            adapter.updateItems(detalles);
        }
    }

    @Override
    public void onBackPressed() {
        this.finish();
        this.overridePendingTransition(R.anim.down_in, R.anim.down_out);
    }
}
