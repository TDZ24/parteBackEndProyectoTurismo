package com.tuapp.reservasturismo.service.impl;

import com.tuapp.reservasturismo.model.Destino;
import com.tuapp.reservasturismo.repository.DestinoRepository;
import com.tuapp.reservasturismo.service.DestinoService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DestinoServiceImpl implements DestinoService {

    private final DestinoRepository destinoRepository;

    public DestinoServiceImpl(DestinoRepository destinoRepository) {
        this.destinoRepository = destinoRepository;
    }

    @Override
    public Destino crearDestino(Destino destino) {
        if (destino.getNombre() == null || destino.getNombre().isEmpty()) {
            throw new RuntimeException("El nombre del destino no puede estar vacío");
        }
        if (destino.getPrecio() <= 0) {
            throw new RuntimeException("El precio debe ser mayor a 0");
        }
        return destinoRepository.guardar(destino);
    }

    @Override
    public List<Destino> listarDestinos() {
        return destinoRepository.listar();
    }

    @Override
    public Destino buscarPorId(Long id) {
        Destino destino = destinoRepository.buscarPorId(id);
        if (destino == null) {
            throw new RuntimeException("Destino no encontrado con id: " + id);
        }
        return destino;
    }

    @Override
    public Destino actualizarDestino(Long id, Destino destino) {
        if (destino.getPrecio() <= 0) {
            throw new RuntimeException("El precio debe ser mayor a 0");
        }
        return destinoRepository.actualizar(id, destino);
    }

    @Override
    public List<Destino> filtrarPorPrecio(double precioMax) {
        return destinoRepository.filtrarPorPrecio(precioMax);
    }

    @Override
    public List<Destino> filtrarPorUbicacion(String ubicacion) {
        return destinoRepository.filtrarPorUbicacion(ubicacion);
    }

    @Override
    public void eliminarDestino(Long id) {
        destinoRepository.eliminar(id);
    }
}