package com.tuapp.reservasturismo.repository.impl;

import com.tuapp.reservasturismo.model.Usuario;
import com.tuapp.reservasturismo.repository.UsuarioRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UsuarioRepositoryImpl implements UsuarioRepository {

    private final Map<Long, Usuario> almacenamiento = new HashMap<>();
    private Long contadorId = 1L;

    @Override
    public Usuario guardar(Usuario usuario) {
        usuario.setId(contadorId++);
        almacenamiento.put(usuario.getId(), usuario);
        return usuario;
    }

    @Override
    public List<Usuario> listar() {
        return new ArrayList<>(almacenamiento.values());
    }

    @Override
    public Usuario buscarPorId(Long id) {
        return almacenamiento.get(id);
    }

    @Override
    public Usuario actualizar(Long id, Usuario usuario) {
        if (!almacenamiento.containsKey(id)) {
            throw new RuntimeException("Usuario no encontrado con id: " + id);
        }
        usuario.setId(id);
        almacenamiento.put(id, usuario);
        return usuario;
    }

    @Override
    public void eliminar(Long id) {
        if (!almacenamiento.containsKey(id)) {
            throw new RuntimeException("Usuario no encontrado con id: " + id);
        }
        almacenamiento.remove(id);
    }
}