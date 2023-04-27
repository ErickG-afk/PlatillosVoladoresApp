package com.example.platillosvoladoresapp.entity.service;


import java.sql.Date;

public class Pedido {
    private int id;
    private Date fechaCompra;
    private Cliente cliente;
    private Double cantidad;
    private boolean anularPedido;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getFechaCompra() {
        return fechaCompra;
    }

    public void setFechaCompra(Date fechaCompra) {
        this.fechaCompra = fechaCompra;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCantidad(Double cantidad) {
        this.cantidad = cantidad;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Double getCantidad() {
        return cantidad;
    }

    public void getTotalAPagar(Double cantidad) {
        this.cantidad = cantidad;
    }

    public boolean isAnularPedido() {
        return anularPedido;
    }

    public void setAnularPedido(boolean anularPedido) {
        this.anularPedido = anularPedido;
    }
}
