package com.tuapp.reservasturismo.repository.impl;

import com.tuapp.reservasturismo.model.Reserva;
import com.tuapp.reservasturismo.repository.ReservaRepository;
import org.springframework.stereotype.Repository; // <-- IMPORTANTE: Faltaba esta línea

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository // <-- IMPORTANTE: Esta etiqueta es la que quita el error UnsatisfiedDependencyException
public class ReservaRepositoryImpl implements ReservaRepository {

    private final Map<Long, Reserva> almacenamiento = new HashMap<>();
    private Long contadorId = 1L;

    @Override
    public Reserva guardar(Reserva reserva) {
        if (reserva.getId() == null) {
            reserva.setId(contadorId++);
        }
        almacenamiento.put(reserva.getId(), reserva);
        return reserva;
    }

    @Override
    public List<Reserva> listar() {
        return new ArrayList<>(almacenamiento.values());
    }

    @Override
    public Reserva buscarPorId(Long id) {
        return almacenamiento.get(id);
    }

    @Override
    public Reserva actualizar(Long id, Reserva reserva) {
        if (!almacenamiento.containsKey(id)) {
            throw new RuntimeException("Reserva no encontrada con id: " + id);
        }
        reserva.setId(id);
        almacenamiento.put(id, reserva);
        return reserva;
    }

    @Override
    public List<Reserva> filtrarPorUsuario(Long usuarioId) {
        return almacenamiento.values().stream()
                .filter(r -> r.getUsuarioId() != null &&
                        r.getUsuarioId().equals(usuarioId))
                .collect(Collectors.toList());
    }

    @Override
    public List<Reserva> filtrarPorDestino(Long destinoId) {
        return almacenamiento.values().stream()
                .filter(r -> r.getDestinoId() != null &&
                        r.getDestinoId().equals(destinoId))
                .collect(Collectors.toList());
    }

    @Override
    public List<Reserva> filtrarPorEstado(String estado) {
        return almacenamiento.values().stream()
                .filter(r -> r.getEstado() != null &&
                        r.getEstado().equalsIgnoreCase(estado))
                .collect(Collectors.toList());
    }

    @Override
    public void cambiarEstado(Long id, String estado) {
        Reserva reserva = almacenamiento.get(id);
        if (reserva == null) {
            throw new RuntimeException("Reserva no encontrada con id: " + id);
        }
        reserva.setEstado(estado);
    }

    @Override
    public void eliminar(Long id) {
        if (!almacenamiento.containsKey(id)) {
            throw new RuntimeException("Reserva no encontrada con id: " + id);
        }
        almacenamiento.remove(id);
    }
}