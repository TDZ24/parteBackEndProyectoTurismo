package com.tuapp.reservasturismo.repository.impl;

import com.tuapp.reservasturismo.model.Destino;
import com.tuapp.reservasturismo.repository.DestinoRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DestinoRepositoryImpl implements DestinoRepository {

    private final Map<Long, Destino> almacenamiento = new HashMap<>();
    private Long contadorId = 1L;

    @Override
    public Destino guardar(Destino destino) {
        destino.setId(contadorId++);
        almacenamiento.put(destino.getId(), destino);
        return destino;
    }

    @Override
    public List<Destino> listar() {
        return new ArrayList<>(almacenamiento.values());
    }

    @Override
    public Destino buscarPorId(Long id) {
        return almacenamiento.get(id);
    }

    @Override
    public Destino actualizar(Long id, Destino destino) {
        if (!almacenamiento.containsKey(id)) {
            throw new RuntimeException("Destino no encontrado con id: " + id);
        }
        destino.setId(id);
        almacenamiento.put(id, destino);
        return destino;
    }

    @Override
    public List<Destino> filtrarPorPrecio(double precioMax) {
        return almacenamiento.values().stream()
                .filter(d -> d.getPrecio() <= precioMax)
                .collect(Collectors.toList());
    }

    @Override
    public List<Destino> filtrarPorUbicacion(String ubicacion) {
        return almacenamiento.values().stream()
                .filter(d -> d.getUbicacion() != null &&
                        d.getUbicacion().equalsIgnoreCase(ubicacion))
                .collect(Collectors.toList());
    }

    @Override
    public void eliminar(Long id) {
        if (!almacenamiento.containsKey(id)) {
            throw new RuntimeException("Destino no encontrado con id: " + id);
        }
        almacenamiento.remove(id);
    }
}