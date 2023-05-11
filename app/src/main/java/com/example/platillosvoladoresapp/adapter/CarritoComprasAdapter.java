package com.example.platillosvoladoresapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.platillosvoladoresapp.R;
import com.example.platillosvoladoresapp.api.ConfigApi;
import com.example.platillosvoladoresapp.communication.CarritoCommunication;
import com.example.platillosvoladoresapp.entity.service.DetallePedido;
import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Locale;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class CarritoComprasAdapter extends RecyclerView.Adapter<CarritoComprasAdapter.ViewHolder> {

    private final List<DetallePedido> detalles;
    private final CarritoCommunication c;

    public CarritoComprasAdapter(List<DetallePedido> detalles, CarritoCommunication c) {
        this.detalles = detalles;
        this.c = c;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_platillos_carrito, parent, false);
        return new ViewHolder(v, this.c);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setItem(this.detalles.get(position));
    }

    @Override
    public int getItemCount() {
        return this.detalles.size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder{

        private final ImageView imgPlatilloDC, btnDecrease, btnAdd, btnEliminarPCarrito;
        private final EditText edtCantidad;
        private final TextView tvNombrePlatilloDC, tvPrecioPDC;
        private final CarritoCommunication c;

        public ViewHolder(@NonNull View itemView, CarritoCommunication c) {
            super(itemView);

            this.c = c;
            this.imgPlatilloDC = itemView.findViewById(R.id.imgPlatilloDC);
            this.btnEliminarPCarrito = itemView.findViewById(R.id.btnEliminarPCarrito);
            this.btnAdd = itemView.findViewById(R.id.btnAdd);
            this.btnDecrease = itemView.findViewById(R.id.btnDecrease);
            this.edtCantidad = itemView.findViewById(R.id.edtCantidad);
            this.tvNombrePlatilloDC = itemView.findViewById(R.id.tvNombrePlatilloDC);
            this.tvPrecioPDC = itemView.findViewById(R.id.tvPrecioPDC);
        }
        public void setItem(final DetallePedido dp) {
            this.tvNombrePlatilloDC.setText(dp.getPlatillo().getNombre());
            this.tvPrecioPDC.setText(String.format(Locale.FRENCH, "%.2f €", dp.getPrecio()));
            int cant = dp.getCantidad();
            this.edtCantidad.setText(Integer.toString(cant));
            String url = ConfigApi.dataB_URL + "/api/documento-almacenado/download/" + dp.getPlatillo().getFoto().getFileName();
            Picasso picasso = new Picasso.Builder(itemView.getContext())
                    .downloader(new OkHttp3Downloader(ConfigApi.getClient()))
                    .build();
            picasso.load(url)
                    .error(R.drawable.image_not_found)
                    .into(this.imgPlatilloDC);

            //-------------Actualizar Cantidad del Carrito-------------------------
            btnAdd.setOnClickListener(v -> {
                if (dp.getCantidad() != 10) {//Si el valor todavía no llega a 10, que siga aumentando
                    dp.addOne();
                    CarritoComprasAdapter.this.notifyDataSetChanged();
                }
            });
            btnDecrease.setOnClickListener(v -> {
                if (dp.getCantidad() != 1) {
                    dp.removeOne();
                    CarritoComprasAdapter.this.notifyDataSetChanged();
                }
            });

            //------------------Eliminar item del carrito-----------------------
            this.btnEliminarPCarrito.setOnClickListener(v -> {
                showMsg(dp.getPlatillo().getId());
            });
        }
        private void showMsg(int idPlatillo) {
            new SweetAlertDialog(itemView.getContext(), SweetAlertDialog.WARNING_TYPE).setTitleText("Aviso del sistema !")
                    .setContentText("¿Estás seguro de eliminar el producto de tu bolsa de compras?")
                    .setCancelText("No, Cancelar!").setConfirmText("Sí, Confirmar")
                    .showCancelButton(true).setCancelClickListener(sDialog -> {
                        sDialog.dismissWithAnimation();
                        new SweetAlertDialog(itemView.getContext(), SweetAlertDialog.ERROR_TYPE).setTitleText("Operación cancelada")
                                .setContentText("No eliminaste ningún platillo de tu bolsa de compras")
                                .show();
                    }).setConfirmClickListener(sweetAlertDialog -> {
                        c.eliminarDetalle(idPlatillo);
                        sweetAlertDialog.dismissWithAnimation();
                        new SweetAlertDialog(itemView.getContext(), SweetAlertDialog.SUCCESS_TYPE).setTitleText("Buen Trabajo !")
                                .setContentText("Excelente, el platillo acaba de ser eliminado de tu bolsa de compras")
                                .show();
                    }).show();
        }
    }
}
