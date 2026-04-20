package com.tuapp.reservasturismo.service.impl;

import com.tuapp.reservasturismo.model.Reserva;
import com.tuapp.reservasturismo.repository.ReservaRepository;
import com.tuapp.reservasturismo.service.ReservaService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservaServiceImpl implements ReservaService {

    private final ReservaRepository reservaRepository;

    public ReservaServiceImpl(ReservaRepository reservaRepository) {
        this.reservaRepository = reservaRepository;
    }

    @Override
    public Reserva crearReserva(Reserva reserva) {
        if (reserva.getFechaInicio() == null || reserva.getFechaFin() == null) {
            throw new RuntimeException("Las fechas no pueden estar vacías");
        }
        if (reserva.getFechaFin().isBefore(reserva.getFechaInicio())) {
            throw new RuntimeException("La fecha fin no puede ser antes que la fecha inicio");
        }
        if (reserva.getCantidadPersonas() <= 0) {
            throw new RuntimeException("La cantidad de personas debe ser mayor a 0");
        }
        reserva.setEstado("ACTIVA");
        return reservaRepository.guardar(reserva);
    }

    @Override
    public List<Reserva> listarReservas() {
        return reservaRepository.listar();
    }

    @Override
    public Reserva buscarPorId(Long id) {
        Reserva reserva = reservaRepository.buscarPorId(id);
        if (reserva == null) {
            throw new RuntimeException("Reserva no encontrada con id: " + id);
        }
        return reserva;
    }

    @Override
    public Reserva actualizarReserva(Long id, Reserva reserva) {
        if (reserva.getFechaFin().isBefore(reserva.getFechaInicio())) {
            throw new RuntimeException("La fecha fin no puede ser antes que la fecha inicio");
        }
        return reservaRepository.actualizar(id, reserva);
    }

    @Override
    public List<Reserva> filtrarPorUsuario(Long usuarioId) {
        return reservaRepository.filtrarPorUsuario(usuarioId);
    }

    @Override
    public List<Reserva> filtrarPorDestino(Long destinoId) {
        return reservaRepository.filtrarPorDestino(destinoId);
    }

    @Override
    public List<Reserva> filtrarPorEstado(String estado) {
        return reservaRepository.filtrarPorEstado(estado);
    }

    @Override
    public void cambiarEstado(Long id, String estado) {
        if (!estado.equals("ACTIVA") && !estado.equals("CANCELADA")) {
            throw new RuntimeException("Estado inválido. Use ACTIVA o CANCELADA");
        }
        reservaRepository.cambiarEstado(id, estado);
    }

    @Override
    public void eliminarReserva(Long id) {
        reservaRepository.eliminar(id);


    }
}