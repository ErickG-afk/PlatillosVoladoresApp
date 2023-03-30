package com.example.platillosvoladoresapp.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.platillosvoladoresapp.R;
import com.example.platillosvoladoresapp.api.ConfigApi;
import com.example.platillosvoladoresapp.entity.service.DetallePedido;
import com.example.platillosvoladoresapp.entity.service.Plato;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

public class PlatoRecomendadoAdapter extends RecyclerView.Adapter<PlatoRecomendadoAdapter.ViewHolder> {

    private List<Plato> platoList;

    public PlatoRecomendadoAdapter(List<Plato> platoList) {
        this.platoList = platoList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_platillos,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setItem(this.platoList.get(position));
    }

    @Override
    public int getItemCount() {
        return this.platoList.size();
    }

    public void updateItems(List<Plato> plato) {
        this.platoList.clear();
        this.platoList.addAll(plato);
        this.notifyDataSetChanged();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void setItem(final Plato p) {
            ImageView imgPlatillo = itemView.findViewById(R.id.imgPlatillo);
            TextView namePlatillo = itemView.findViewById(R.id.namePlatillo);
            Button btnOrdenar = itemView.findViewById(R.id.btnOrdenar);

            String url = ConfigApi.dataB_URL + "/api/documento-almacenado/download/" + p.getFoto().getFileName();

            Picasso picasso = new Picasso.Builder(itemView.getContext())
                    .downloader(new OkHttp3Downloader(ConfigApi.getClient()))
                    .build();
            picasso.load(url)
                    //.networkPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                    .error(R.drawable.image_not_found)
                    .into(imgPlatillo);
            namePlatillo.setText(p.getNombre());
            btnOrdenar.setOnClickListener(v -> {
                DetallePedido detallePedido = new DetallePedido();
                detallePedido.setPlato(p);
                detallePedido.setCantidad(1);
                detallePedido.setPrecio(p.getPrecio());
                //mostrarBadgeCommunication.add(detallePedido);
                 });


        }
    }

}
