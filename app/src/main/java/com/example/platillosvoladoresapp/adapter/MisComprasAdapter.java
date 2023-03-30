package com.example.platillosvoladoresapp.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.platillosvoladoresapp.R;
import com.example.platillosvoladoresapp.activity.ui.compras.DetalleMisComprasActivity;
import com.example.platillosvoladoresapp.communication.Communication;
import com.example.platillosvoladoresapp.entity.service.dto.PedidoConDetalleDTO;
import com.example.platillosvoladoresapp.utils.DateSerializer;
import com.example.platillosvoladoresapp.utils.TimeSerializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.sql.Date;
import java.sql.Time;
import java.util.List;
import java.util.Locale;

public class MisComprasAdapter extends RecyclerView.Adapter<MisComprasAdapter.ViewHolder> {

    private final List<PedidoConDetalleDTO> pedidos;
    private final Communication communication;

    public MisComprasAdapter(List<PedidoConDetalleDTO> pedidos, Communication communication) {
        this.pedidos = pedidos;
        this.communication = communication;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mis_compras, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setItem(this.pedidos.get(position));
    }

    @Override
    public int getItemCount() {
        return this.pedidos.size();
    }

    public void updateItems(List<PedidoConDetalleDTO> pedido) {
        this.pedidos.clear();
        this.pedidos.addAll(pedido);
        this.notifyDataSetChanged();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void setItem(final PedidoConDetalleDTO dto) {
            final TextView txtValueCodPurchases = this.itemView.findViewById(R.id.txtValueCodPurchases),
                    txtValueDatePurchases = this.itemView.findViewById(R.id.txtValueDatePurchases),
                    txtValueAmount = this.itemView.findViewById(R.id.txtValueAmount),
                    txtValueOrder = this.itemView.findViewById(R.id.txtValueOrder);
            txtValueCodPurchases.setText("C000" + Integer.toString(dto.getPedido().getId()));
            txtValueDatePurchases.setText((dto.getPedido().getFechaCompra()).toString());
            txtValueAmount.setText(" " +(dto.getPedido().getTotalAPagar()));
            txtValueOrder.setText(dto.getPedido().isAnularPedido() ? "Pedido cancelado" : "Enviado, en proceso de envio...");

            itemView.setOnClickListener(v -> {
                final Intent i = new Intent(itemView.getContext(), DetalleMisComprasActivity.class);
                final Gson g = new GsonBuilder()
                        .registerTypeAdapter(Date.class, new DateSerializer())
                        .registerTypeAdapter(Time.class, new TimeSerializer())
                        .create();
                i.putExtra("detailsPurchases", g.toJson(dto.getDetallePedidos()));
                communication.showDetails(i);//Esto es solo para dar una animación.
            });
        }
    }
}
