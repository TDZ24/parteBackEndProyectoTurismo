package com.tuapp.reservasturismo.service.impl;

import com.tuapp.reservasturismo.model.Usuario;
import com.tuapp.reservasturismo.repository.UsuarioRepository;
import com.tuapp.reservasturismo.service.UsuarioService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public Usuario crearUsuario(Usuario usuario) {
        if (usuario.getNombre() == null || usuario.getNombre().isEmpty()) {
            throw new RuntimeException("El nombre del usuario no puede estar vacío");
        }
        if (usuario.getEmail() == null || usuario.getEmail().isEmpty()) {
            throw new RuntimeException("El email del usuario no puede estar vacío");
        }
        return usuarioRepository.guardar(usuario);
    }

    @Override
    public List<Usuario> listarUsuarios() {
        return usuarioRepository.listar();
    }

    @Override
    public Usuario buscarPorId(Long id) {
        Usuario usuario = usuarioRepository.buscarPorId(id);
        if (usuario == null) {
            throw new RuntimeException("Usuario no encontrado con id: " + id);
        }
        return usuario;
    }

    @Override
    public Usuario actualizarUsuario(Long id, Usuario usuario) {
        if (usuario.getNombre() == null || usuario.getNombre().isEmpty()) {
            throw new RuntimeException("El nombre no puede estar vacío");
        }
        return usuarioRepository.actualizar(id, usuario);
    }

    @Override
    public void eliminarUsuario(Long id) {
        usuarioRepository.eliminar(id);
    }
}