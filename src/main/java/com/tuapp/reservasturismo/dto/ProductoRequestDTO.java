package com.tuapp.reservasturismo.dto;

public class ProductoRequestDTO {

    private String nombre;
    private String descripcion;
    private Long categoriaId;

    public String getNombre() { return nombre; }
    public String getDescripcion() { return descripcion; }
    public Long getCategoriaId() { return categoriaId; }
}