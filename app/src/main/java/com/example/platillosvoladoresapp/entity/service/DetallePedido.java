package com.example.platillosvoladoresapp.entity.service;

public class DetallePedido {
    private int id;
    private int cantidad;
    private Double precio;
    private Plato plato;

    public Plato getPlato() {
        return plato;
    }

    public void setPlato(Plato plato) {
        this.plato = plato;
    }

    private Pedido pedido;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }



    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public void addOne() {
        this.cantidad++;
    }

    public void removeOne() {
        this.cantidad--;
    }

    public double getTotal() {
        return this.cantidad * this.precio;
    }
}
