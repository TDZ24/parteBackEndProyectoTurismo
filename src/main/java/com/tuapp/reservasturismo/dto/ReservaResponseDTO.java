// Archivo: src/main/java/com/tuapp/reservasturismo/dto/ReservaResponseDTO.java

package com.tuapp.reservasturismo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;

public class ReservaResponseDTO {

    private Long id;

    @JsonProperty("usuario")
    private UsuarioMinDTO usuario;

    @JsonProperty("producto")
    private ProductoMinDTO producto;

    @JsonProperty("cantidad_personas")
    private Integer cantidadPersonas;

    @JsonProperty("estado")
    private String estado;

    @JsonProperty("fecha_creacion")
    private LocalDateTime fecha;

    // Constructor
    public ReservaResponseDTO() {}

    public ReservaResponseDTO(Long id, UsuarioMinDTO usuario, ProductoMinDTO producto,
                              Integer cantidadPersonas, String estado, LocalDateTime fecha) {
        this.id = id;
        this.usuario = usuario;
        this.producto = producto;
        this.cantidadPersonas = cantidadPersonas;
        this.estado = estado;
        this.fecha = fecha;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UsuarioMinDTO getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioMinDTO usuario) {
        this.usuario = usuario;
    }

    public ProductoMinDTO getProducto() {
        return producto;
    }

    public void setProducto(ProductoMinDTO producto) {
        this.producto = producto;
    }

    public Integer getCantidadPersonas() {
        return cantidadPersonas;
    }

    public void setCantidadPersonas(Integer cantidadPersonas) {
        this.cantidadPersonas = cantidadPersonas;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }
}

// DTOs Anidados
class UsuarioMinDTO {
    private Long id;
    private String username;
    private String email;

    public UsuarioMinDTO() {}
    public UsuarioMinDTO(Long id, String username, String email) {
        this.id = id;
        this.username = username;
        this.email = email;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}

class ProductoMinDTO {
    private Long id;
    private String nombre;
    private String descripcion;

    public ProductoMinDTO() {}
    public ProductoMinDTO(Long id, String nombre, String descripcion) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
}