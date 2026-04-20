package com.tuapp.reservasturismo.factory;

import com.tuapp.reservasturismo.model.Reserva;
import java.time.LocalDate;

// Patrón Factory: Centraliza la creación de objetos Reserva
public class ReservaFactory {
    public static Reserva crearNuevaReserva(Long usuarioId, Long destinoId, LocalDate inicio, LocalDate fin, int personas) {
        Reserva reserva = new Reserva();
        reserva.setUsuarioId(usuarioId);
        reserva.setDestinoId(destinoId);
        reserva.setFechaInicio(inicio);
        reserva.setFechaFin(fin);
        reserva.setCantidadPersonas(personas);
        reserva.setEstado("ACTIVA"); // Estado inicial por defecto
        return reserva;
    }
}