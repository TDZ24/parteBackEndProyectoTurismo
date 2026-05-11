package com.tuapp.reservasturismo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ProductoRequestDTO {

    @NotBlank(message = "El nombre del producto es obligatorio")
    @Size(max = 120, message = "El nombre del producto no puede superar 120 caracteres")
    private String nombre;

    @NotBlank(message = "La descripción del producto es obligatoria")
    @Size(max = 1000, message = "La descripción no puede superar 1000 caracteres")
    private String descripcion;

    @NotNull(message = "El ID de la categoría es obligatorio")
    private Long categoriaId;

    public ProductoRequestDTO() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Long getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(Long categoriaId) {
        this.categoriaId = categoriaId;
    }
}
