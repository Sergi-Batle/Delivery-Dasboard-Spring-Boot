package dev.danvega.jte.classes;

import java.util.Date;

public class Envio {
    public int id;
    public Repartidor repartidor;
    public Factura factura;
    public String direccion;
    public Date fecha;
    public Ubicacion ubicacion;
    public Cliente cliente;
    public String estado;

    public Envio() {}

    public Envio(int id, Repartidor repartidor, Factura factura, String direccion) {
        this.id = id;
        this.repartidor = repartidor;
        this.factura = factura;
        this.direccion = direccion;
    }

    public Envio(String direccion, Cliente cliente, Repartidor repartidor, Ubicacion ubicacion, String estado) {
        this.direccion = direccion;
        this.cliente = cliente;
        this.repartidor = repartidor;
        this.ubicacion = ubicacion;
        this.estado = estado;
    }

    public Envio(String direccion, Date fecha) {
        this.direccion = direccion;
        this.fecha = fecha;
    }

    public int getId() {
        return id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Ubicacion getUbicacion() {
        return ubicacion;
    }

    public Repartidor getRepartidor() {
        return repartidor;
    }

    public Factura getItems() {
        return factura;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public void setRepartidor(Repartidor repartidor) {
        this.repartidor = repartidor;
    }

    public void setItems(Factura factura) {
        this.factura = factura;
    }

    public void setUbicacion(Ubicacion ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getDireccion() {return this.direccion;}

    public String getFecha() {return this.direccion;}
}
