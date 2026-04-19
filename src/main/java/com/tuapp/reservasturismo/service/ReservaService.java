package com.tuapp.reservasturismo.service;

import com.tuapp.reservasturismo.model.Reserva;
import java.util.List;

public interface ReservaService {

    Reserva crearReserva(Reserva reserva);

    List<Reserva> listarReservas();

    Reserva buscarPorId(Long id);

    Reserva actualizarReserva(Long id, Reserva reserva);

    List<Reserva> filtrarPorUsuario(Long usuarioId);

    List<Reserva> filtrarPorDestino(Long destinoId);

    List<Reserva> filtrarPorEstado(String estado);

    void cambiarEstado(Long id, String estado);

    void eliminarReserva(Long id);
}
