package com.tuapp.reservasturismo.service.impl;

import com.tuapp.reservasturismo.dto.LoginRequestDTO;
import com.tuapp.reservasturismo.dto.LoginResponseDTO;
import com.tuapp.reservasturismo.model.Usuario;
import com.tuapp.reservasturismo.repository.UsuarioRepository;
import com.tuapp.reservasturismo.service.UsuarioService;
import com.tuapp.reservasturismo.session.SesionManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final SesionManager sesionManager;

    @Override
    public Usuario crearUsuario(Usuario usuario) {
        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            throw new RuntimeException("Ya existe un usuario con el email: " + usuario.getEmail());
        }
        if (usuarioRepository.existsByUsername(usuario.getUsername())) {
            throw new RuntimeException("Ya existe un usuario con el username: " + usuario.getUsername());
        }
        if (usuario.getRol() == null) {
            usuario.setRol(Usuario.Rol.USER);
        }
        return usuarioRepository.save(usuario);
    }

    @Override
    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    @Override
    public Usuario buscarPorId(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + id));
    }

    @Override
    public Usuario actualizarUsuario(Long id, Usuario usuario) {
        Usuario existente = buscarPorId(id);
        existente.setUsername(usuario.getUsername());
        existente.setEmail(usuario.getEmail());
        if (usuario.getRol() != null) {
            existente.setRol(usuario.getRol());
        }
        return usuarioRepository.save(existente);
    }

    @Override
    public void eliminarUsuario(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new RuntimeException("Usuario no encontrado con id: " + id);
        }
        usuarioRepository.deleteById(id);
    }

    @Override
    public LoginResponseDTO login(LoginRequestDTO request) {
        if (request.getEmail() == null || request.getEmail().isBlank()) {
            throw new RuntimeException("El email es obligatorio.");
        }
        if (request.getPassword() == null || request.getPassword().isBlank()) {
            throw new RuntimeException("La contraseña es obligatoria.");
        }

        Optional<Usuario> optUsuario = usuarioRepository.findByEmail(request.getEmail());
        if (optUsuario.isEmpty()) {
            throw new RuntimeException("No existe ninguna cuenta con ese email.");
        }

        Usuario usuario = optUsuario.get();

        if (!request.getPassword().equals(usuario.getPassword())) {
            throw new RuntimeException("Contraseña incorrecta.");
        }

        String token = sesionManager.crearSesion(usuario);

        return new LoginResponseDTO(
                "Bienvenido, " + usuario.getUsername() + "!",
                token,
                usuario.getId(),
                usuario.getUsername(),
                usuario.getEmail(),
                usuario.getRol().name()
        );
    }

    @Override
    public void logout(String token) {
        if (token == null || token.isBlank()) {
            throw new RuntimeException("Token de sesión no proporcionado.");
        }
        if (sesionManager.obtenerUsuario(token) == null) {
            throw new RuntimeException("La sesión no existe o ya fue cerrada.");
        }
        sesionManager.cerrarSesion(token);
    }

    @Override
    public Usuario cambiarRol(Long idObjetivo, String nuevoRol, String tokenAdmin) {
        if (tokenAdmin == null || tokenAdmin.isBlank()) {
            throw new RuntimeException("Se requiere token de sesión para esta acción.");
        }
        if (!sesionManager.esAdmin(tokenAdmin)) {
            throw new RuntimeException("Acceso denegado. Solo los administradores pueden cambiar roles.");
        }

        Usuario objetivo = buscarPorId(idObjetivo);

        if (nuevoRol == null || (!nuevoRol.equalsIgnoreCase("ADMIN") && !nuevoRol.equalsIgnoreCase("USER"))) {
            throw new RuntimeException("Rol inválido. Los valores permitidos son: ADMIN, USER.");
        }

        Usuario adminActual = sesionManager.obtenerUsuario(tokenAdmin);
        if (adminActual.getId().equals(idObjetivo) && "USER".equalsIgnoreCase(nuevoRol)) {
            throw new RuntimeException("No puedes quitarte el rol de administrador a ti mismo.");
        }

        objetivo.setRol(Usuario.Rol.valueOf(nuevoRol.toUpperCase()));
        return usuarioRepository.save(objetivo);
    }
}