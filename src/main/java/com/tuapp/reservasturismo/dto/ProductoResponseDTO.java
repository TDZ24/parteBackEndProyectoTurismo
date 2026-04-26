package com.tuapp.reservasturismo.dto;

public class ProductoResponseDTO {

    private Long id;
    private String nombre;
    private String descripcion;
    private String categoria;

    public ProductoResponseDTO(Long id, String nombre, String descripcion, String categoria) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.categoria = categoria;
    }

    public Long getId() { return id; }
    public String getNombre() { return nombre; }
    public String getDescripcion() { return descripcion; }
    public String getCategoria() { return categoria; }
}
