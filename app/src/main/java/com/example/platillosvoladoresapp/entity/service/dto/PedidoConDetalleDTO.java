package com.example.platillosvoladoresapp.entity.service.dto;


import com.example.platillosvoladoresapp.entity.service.DetallePedido;
import com.example.platillosvoladoresapp.entity.service.Pedido;

import java.util.ArrayList;
import java.util.List;

public class PedidoConDetalleDTO {
    private Pedido pedido;
    private List<DetallePedido> detallePedidos;

    public PedidoConDetalleDTO() {
        this.pedido = new Pedido();
        this.detallePedidos = new ArrayList<>();
    }

    public PedidoConDetalleDTO(Pedido pedido, List<DetallePedido> detallePedido) {
        this.pedido = pedido;
        this.detallePedidos = detallePedido;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public List<DetallePedido> getDetallePedidos() {
        return detallePedidos;
    }

    public void setDetallePedidos(List<DetallePedido> detallePedidos) {
        this.detallePedidos = detallePedidos;
    }
}
