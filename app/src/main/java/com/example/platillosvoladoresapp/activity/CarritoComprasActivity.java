package com.example.platillosvoladoresapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.platillosvoladoresapp.R;
import com.example.platillosvoladoresapp.adapter.CarritoComprasAdapter;
import com.example.platillosvoladoresapp.communication.CarritoCommunication;
import com.example.platillosvoladoresapp.entity.service.DetallePedido;
import com.example.platillosvoladoresapp.entity.service.Usuario;
import com.example.platillosvoladoresapp.entity.service.dto.GenerarPedidoDTO;
import com.example.platillosvoladoresapp.utils.Carrito;
import com.example.platillosvoladoresapp.utils.DateSerializer;
import com.example.platillosvoladoresapp.utils.TimeSerializer;
import com.example.platillosvoladoresapp.viewmodel.PedidoViewModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class CarritoComprasActivity extends AppCompatActivity implements CarritoCommunication {

    private PedidoViewModel pedidoViewModel;
    private CarritoComprasAdapter adapter;
    private RecyclerView rcvCarritoCompras;
    Button btnFinalizarCompra;

    final Gson g = new GsonBuilder()
            .registerTypeAdapter(Date.class, new DateSerializer())
            .registerTypeAdapter(Time.class, new TimeSerializer())
            .create();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrito_compras);
        init();
        initViewModel();
        initAdapter();
    }



    private void init(){
        Toolbar toolbar = this.findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_volver_atras);
        toolbar.setNavigationOnClickListener(v -> {//Reemplazo con lamba
            this.finish();
            this.overridePendingTransition(R.anim.rigth_in, R.anim.rigth_out);
        });
        rcvCarritoCompras = findViewById(R.id.rcvBolsaCompras);
        btnFinalizarCompra = findViewById(R.id.btnFinalizarCompra);
        btnFinalizarCompra.setOnClickListener(v -> {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
            String pref = preferences.getString("UsuarioJson", "");
            Usuario u = g.fromJson(pref, Usuario.class);
            int idC = u.getCliente().getId();
            if (idC != 0) {
                if (Carrito.getDetallePedidos().isEmpty()) {
                    toastIncorrecto("¡Ups!, La bolsa de compras está vacia.");
                } else {
                    toastCorrecto("Procesando pedido...");
                    registrarPedido(idC);
                }
            } else {
                toastIncorrecto("No ha iniciado sesión, se le redirigirá al login");
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                overridePendingTransition(R.anim.left_in, R.anim.left_out);
            }
        });
    }

    private void initViewModel() {
        final ViewModelProvider vmp = new ViewModelProvider(this);
        this.pedidoViewModel = vmp.get(PedidoViewModel.class);
    }
    private void initAdapter() {
        adapter = new CarritoComprasAdapter(Carrito.getDetallePedidos(), this);
        rcvCarritoCompras.setLayoutManager(new LinearLayoutManager(this));
        rcvCarritoCompras.setAdapter(adapter);
    }

    private void registrarPedido(int idC){
        ArrayList<DetallePedido> detallePedidos = Carrito.getDetallePedidos();
        GenerarPedidoDTO dto = new GenerarPedidoDTO();
        java.util.Date date = new java.util.Date();
        dto.getPedido().setFechaCompra(new Date(date.getTime()));
        dto.getPedido().setAnularPedido(false);
        dto.getPedido().setCantidad(getTotalV(detallePedidos));
        dto.getCliente().setId(idC);
        dto.setDetallePedido(detallePedidos);
        this.pedidoViewModel.guardarPedido(dto).observe(this,response ->{
            if(response.getRpta() == 1){
                toastCorrecto("Pedido registrado con éxito");
                Carrito.limpiar();
                finish();
                overridePendingTransition(R.anim.left_in, R.anim.left_out);
            }else{

                toastIncorrecto("No se pudo registrar el pedido");
            }
        });
    }

    private double getTotalV(List<DetallePedido> detalles) {
        float total = 0;
        for (DetallePedido dp : detalles) {
            total += dp.getTotal();
        }
        return total;
    }

    public void toastIncorrecto(String texto) {
        LayoutInflater layoutInflater = getLayoutInflater();
        View layouView = layoutInflater.inflate(R.layout.custom_toast_error, (ViewGroup) findViewById(R.id.ll_custom_toast_error));
        TextView textView = layouView.findViewById(R.id.txtMensajeToast2);
        textView.setText(texto);

        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.BOTTOM, 0, 200);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layouView);
        toast.show();

    }

    public void toastCorrecto(String texto) {
        LayoutInflater layoutInflater = getLayoutInflater();
        View layouView = layoutInflater.inflate(R.layout.custom_toast_ok, (ViewGroup) findViewById(R.id.ll_custom_toast_ok));
        TextView textView = layouView.findViewById(R.id.txtMensajeToast1);
        textView.setText(texto);

        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.BOTTOM, 0, 200);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layouView);
        toast.show();
    }

    @Override
    public void eliminarDetalle(int idP) {
        Carrito.eliminar(idP);
        this.adapter.notifyDataSetChanged();
    }
}