package com.tuapp.reservasturismo.repository;

import com.tuapp.reservasturismo.model.Categoria;
import java.util.*;

public class CategoriaRepository {

    private List<Categoria> categorias = new ArrayList<>();

    public CategoriaRepository() {
        categorias.add(new Categoria(1L, "Aventura"));
        categorias.add(new Categoria(2L, "Playa"));
        categorias.add(new Categoria(3L, "Cultural"));
    }

    public List<Categoria> listar() {
        return categorias;
    }

    public Categoria buscarPorId(Long id) {
        return categorias.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
}