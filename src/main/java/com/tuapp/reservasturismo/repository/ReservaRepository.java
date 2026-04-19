package com.tuapp.reservasturismo.repository;

import com.tuapp.reservasturismo.model.Reserva;
import java.util.List;

public interface ReservaRepository {

    Reserva guardar(Reserva reserva);

    List<Reserva> listar();

    Reserva buscarPorId(Long id);

    Reserva actualizar(Long id, Reserva reserva);

    List<Reserva> filtrarPorUsuario(Long usuarioId);

    List<Reserva> filtrarPorDestino(Long destinoId);

    List<Reserva> filtrarPorEstado(String estado);

    void cambiarEstado(Long id, String estado);

    void eliminar(Long id);
}
