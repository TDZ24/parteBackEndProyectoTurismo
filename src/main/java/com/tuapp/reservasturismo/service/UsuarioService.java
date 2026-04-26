package com.tuapp.reservasturismo.service;

import com.tuapp.reservasturismo.dto.LoginRequestDTO;
import com.tuapp.reservasturismo.dto.LoginResponseDTO;
import com.tuapp.reservasturismo.model.Usuario;

import java.util.List;

public interface UsuarioService {

    // CRUD base
    Usuario crearUsuario(Usuario usuario);
    List<Usuario> listarUsuarios();
    Usuario buscarPorId(Long id);
    Usuario actualizarUsuario(Long id, Usuario usuario);
    void eliminarUsuario(Long id);

    // Login / sesión
    LoginResponseDTO login(LoginRequestDTO request);
    void logout(String token);

    // Gestión de roles
    Usuario cambiarRol(Long idUsuarioObjetivo, String nuevoRol, String tokenAdmin);
}