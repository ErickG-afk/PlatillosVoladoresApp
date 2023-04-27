package com.example.platillosvoladoresapp.entity.service.dto;


import com.example.platillosvoladoresapp.entity.service.Cliente;
import com.example.platillosvoladoresapp.entity.service.DetallePedido;
import com.example.platillosvoladoresapp.entity.service.Pedido;
import com.facebook.stetho.json.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class GenerarPedidoDTO {
    private Pedido pedido;
    private ArrayList<DetallePedido> detallePedido;
    private Cliente cliente;

    public GenerarPedidoDTO() {
        this.pedido = new Pedido();
        this.detallePedido = new ArrayList<>();
        this.cliente = new Cliente();
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public ArrayList<DetallePedido> getDetallePedido() {
        return detallePedido;
    }

    public void setDetallePedido(ArrayList<DetallePedido> detallePedido) {
        this.detallePedido = detallePedido;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
}
