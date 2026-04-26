package com.tuapp.reservasturismo.model;

public class Producto {

    private Long id;
    private String nombre;
    private String descripcion;
    private Categoria categoria;

    public Producto(Long id, String nombre, String descripcion, Categoria categoria) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.categoria = categoria;
    }

    public Producto() {}

    public Long getId() { return id; }
    public String getNombre() { return nombre; }
    public String getDescripcion() { return descripcion; }
    public Categoria getCategoria() { return categoria; }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }
}
