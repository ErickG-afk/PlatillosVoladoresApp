package com.example.platillosvoladoresapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.platillosvoladoresapp.R;
import com.example.platillosvoladoresapp.api.ConfigApi;
import com.example.platillosvoladoresapp.communication.MostrarBadgeCommunication;
import com.example.platillosvoladoresapp.entity.service.DetallePedido;
import com.example.platillosvoladoresapp.entity.service.Platillo;
import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Locale;

public class PlatosPorCategoriaAdapter extends RecyclerView.Adapter<PlatosPorCategoriaAdapter.ViewHolder> {
    private List<Platillo> listadoPlatoPorCategoria;
    private final MostrarBadgeCommunication mostrarBadgeCommunication;

    public PlatosPorCategoriaAdapter(List<Platillo> listadoPlatoPorCategoria, MostrarBadgeCommunication mostrarBadgeCommunication) {
        this.listadoPlatoPorCategoria = listadoPlatoPorCategoria;


        this.mostrarBadgeCommunication = mostrarBadgeCommunication;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_platillos_por_categoria, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setItem(this.listadoPlatoPorCategoria.get(position));
    }

    @Override
    public int getItemCount() {
        return this.listadoPlatoPorCategoria.size();
    }

    public void updateItems(List<Platillo> platosByCategoria) {
        this.listadoPlatoPorCategoria.clear();
        this.listadoPlatoPorCategoria.addAll(platosByCategoria);
        this.notifyDataSetChanged();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imgPlatilloC;
        private final TextView namePlatilloC, txtPricePlatilloC;
        private final Button btnOrdenarPC;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.imgPlatilloC = itemView.findViewById(R.id.imgPlatilloC);
            this.namePlatilloC = itemView.findViewById(R.id.namePlatilloC);
            this.txtPricePlatilloC = itemView.findViewById(R.id.txtPricePlatilloC);
            this.btnOrdenarPC = itemView.findViewById(R.id.btnOrdenarPC);
        }

        public void setItem(final Platillo p) {
            String url = ConfigApi.dataB_URL + "/api/documento-almacenado/download/" + p.getFoto().getFileName();

            Picasso picasso = new Picasso.Builder(itemView.getContext())
                    .downloader(new OkHttp3Downloader(ConfigApi.getClient()))
                    .build();
            picasso.load(url)
                    //.networkPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE) //No lo almacena el la caché ni en el disco
                    .error(R.drawable.image_not_found)
                    .into(imgPlatilloC);
            namePlatilloC.setText(p.getNombre());
            txtPricePlatilloC.setText(String.format(Locale.FRENCH, "%.2f €", p.getPrecio()));
            btnOrdenarPC.setOnClickListener(v -> {
                DetallePedido detallePedido = new DetallePedido();
                detallePedido.setPlatillo(p);
                detallePedido.setCantidad(1);
                detallePedido.setPrecio(p.getPrecio());
                mostrarBadgeCommunication.add(detallePedido);

            });
        }
    }
}
