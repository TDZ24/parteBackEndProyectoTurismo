package com.tuapp.service;

import com.tuapp.model.Destino;
import java.util.List;

public interface DestinoService {

    Destino crearDestino(Destino destino);

    List<Destino> listarDestinos();

    Destino buscarPorId(Long id);

    Destino actualizarDestino(Long id, Destino destino);

    List<Destino> filtrarPorPrecio(double precioMax);

    List<Destino> filtrarPorUbicacion(String ubicacion);

    void eliminarDestino(Long id);
}