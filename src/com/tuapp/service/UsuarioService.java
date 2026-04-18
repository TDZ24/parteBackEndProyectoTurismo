package com.tuapp.service;

import com.tuapp.model.Usuario;
import java.util.List;

public interface UsuarioService {

    Usuario crearUsuario(Usuario usuario);

    List<Usuario> listarUsuarios();

    Usuario buscarPorId(Long id);

    Usuario actualizarUsuario(Long id, Usuario usuario);

    void eliminarUsuario(Long id);
}