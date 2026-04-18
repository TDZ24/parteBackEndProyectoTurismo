package com.tuapp.repository;

import com.tuapp.model.Destino;
import java.util.List;

public interface DestinoRepository {

    Destino guardar(Destino destino);

    List<Destino> listar();

    Destino buscarPorId(Long id);

    Destino actualizar(Long id, Destino destino);

    List<Destino> filtrarPorPrecio(double precioMax);

    List<Destino> filtrarPorUbicacion(String ubicacion);

    void eliminar(Long id);
}