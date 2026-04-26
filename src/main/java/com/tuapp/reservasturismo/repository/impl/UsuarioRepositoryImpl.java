package com.tuapp.reservasturismo.repository.impl;

import com.tuapp.reservasturismo.model.Usuario;
import com.tuapp.reservasturismo.repository.UsuarioRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class UsuarioRepositoryImpl implements UsuarioRepository {

    private final Map<Long, Usuario> almacenamiento = new HashMap<>();
    private Long contadorId = 1L;

    // ── Datos iniciales de prueba ─────────────────────────────────────────────
    public UsuarioRepositoryImpl() {
        Usuario admin = new Usuario(null, "Admin Principal", "admin@turismo.com",
                "admin123", "ADMIN");
        guardar(admin);

        Usuario user = new Usuario(null, "Carlos Perez", "carlos@turismo.com",
                "user123", "USER");
        guardar(user);
    }

    @Override
    public Usuario guardar(Usuario usuario) {
        if (usuario.getId() == null) {
            usuario.setId(contadorId++);
        }
        if (usuario.getRol() == null) {
            usuario.setRol("USER");
        }
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
    public Optional<Usuario> buscarPorEmail(String email) {
        if (email == null) return Optional.empty();
        return almacenamiento.values().stream()
                .filter(u -> email.equalsIgnoreCase(u.getEmail()))
                .findFirst();
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
    public Usuario cambiarRol(Long id, String nuevoRol) {
        Usuario usuario = buscarPorId(id);
        if (usuario == null) {
            throw new RuntimeException("Usuario no encontrado con id: " + id);
        }
        usuario.setRol(nuevoRol);
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