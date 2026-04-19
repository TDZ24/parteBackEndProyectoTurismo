package com.tuapp.reservasturismo.model;

public class Destino {

    private Long id;
    private String nombre;
    private String ubicacion;
    private double precio;

    public Destino() {
    }

    public Destino(Long id, String nombre, String ubicacion, double precio) {
        this.id = id;
        this.nombre = nombre;
        this.ubicacion = ubicacion;
        this.precio = precio;
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public double getPrecio() {
        return precio;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }
}
