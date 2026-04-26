package com.tuapp.reservasturismo.repository;

import com.tuapp.reservasturismo.model.Usuario;
import java.util.List;
import java.util.Optional;

public interface UsuarioRepository {

    Usuario guardar(Usuario usuario);

    List<Usuario> listar();

    Usuario buscarPorId(Long id);

    Optional<Usuario> buscarPorEmail(String email);

    Usuario actualizar(Long id, Usuario usuario);

    Usuario cambiarRol(Long id, String nuevoRol);

    void eliminar(Long id);
}