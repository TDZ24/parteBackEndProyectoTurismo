package com.tuapp.model;

import java.time.LocalDate;

public class Reserva {

    private Long id;
    private Long usuarioId;
    private Long destinoId;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private int cantidadPersonas;
    private String estado; // ACTIVA, CANCELADA

    public Reserva() {
    }

    public Reserva(Long id, Long usuarioId, Long destinoId,
                   LocalDate fechaInicio, LocalDate fechaFin,
                   int cantidadPersonas, String estado) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.destinoId = destinoId;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.cantidadPersonas = cantidadPersonas;
        this.estado = estado;
    }

    public Long getId() {
        return id;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public Long getDestinoId() {
        return destinoId;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public int getCantidadPersonas() {
        return cantidadPersonas;
    }

    public String getEstado() {
        return estado;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public void setDestinoId(Long destinoId) {
        this.destinoId = destinoId;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    public void setCantidadPersonas(int cantidadPersonas) {
        this.cantidadPersonas = cantidadPersonas;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}