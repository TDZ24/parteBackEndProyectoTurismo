package com.tuapp.reservasturismo.model;

public class Categoria {
    private Long id;
    private String nombre;

    public Categoria(Long id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public Categoria() {}

    public Long getId() { return id; }
    public String getNombre() { return nombre; }
}
