package com.example.platillosvoladoresapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
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
import com.example.platillosvoladoresapp.utils.Carrito;
import com.example.platillosvoladoresapp.utils.DateSerializer;
import com.example.platillosvoladoresapp.utils.TimeSerializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

public class CarritoComprasActivity extends AppCompatActivity implements CarritoCommunication {

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
                    //registrarPedido(idC);
                }
            } else {
                toastIncorrecto("No ha iniciado sesión, se le redirigirá al login");
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                overridePendingTransition(R.anim.left_in, R.anim.left_out);
            }
        });
    }

    private void initAdapter() {
        adapter = new CarritoComprasAdapter(Carrito.getDetallePedidos(), this);
        rcvCarritoCompras.setLayoutManager(new LinearLayoutManager(this));
        rcvCarritoCompras.setAdapter(adapter);
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