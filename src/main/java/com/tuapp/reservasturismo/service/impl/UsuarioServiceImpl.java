package com.tuapp.reservasturismo.service.impl;

import com.tuapp.reservasturismo.dto.LoginRequestDTO;
import com.tuapp.reservasturismo.dto.LoginResponseDTO;
import com.tuapp.reservasturismo.model.Usuario;
import com.tuapp.reservasturismo.repository.UsuarioRepository;
import com.tuapp.reservasturismo.service.UsuarioService;
import com.tuapp.reservasturismo.session.SesionManager;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final SesionManager sesionManager;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository,
                              SesionManager sesionManager) {
        this.usuarioRepository = usuarioRepository;
        this.sesionManager     = sesionManager;
    }

    // ── CRUD base ─────────────────────────────────────────────────────────────

    @Override
    public Usuario crearUsuario(Usuario usuario) {
        if (usuario.getNombre() == null || usuario.getNombre().isBlank()) {
            throw new RuntimeException("El nombre del usuario no puede estar vacío.");
        }
        if (usuario.getEmail() == null || usuario.getEmail().isBlank()) {
            throw new RuntimeException("El email del usuario no puede estar vacío.");
        }
        if (usuarioRepository.buscarPorEmail(usuario.getEmail()).isPresent()) {
            throw new RuntimeException("Ya existe un usuario con el email: " + usuario.getEmail());
        }
        if (usuario.getRol() == null) {
            usuario.setRol("USER");
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
        if (usuario.getNombre() == null || usuario.getNombre().isBlank()) {
            throw new RuntimeException("El nombre no puede estar vacío.");
        }
        buscarPorId(id); // valida que exista
        return usuarioRepository.actualizar(id, usuario);
    }

    @Override
    public void eliminarUsuario(Long id) {
        buscarPorId(id); // valida que exista
        usuarioRepository.eliminar(id);
    }

    // ── Login / Logout ────────────────────────────────────────────────────────

    @Override
    public LoginResponseDTO login(LoginRequestDTO request) {
        // 1. Validar campos vacíos
        if (request.getEmail() == null || request.getEmail().isBlank()) {
            throw new RuntimeException("El email es obligatorio.");
        }
        if (request.getPassword() == null || request.getPassword().isBlank()) {
            throw new RuntimeException("La contraseña es obligatoria.");
        }

        // 2. Buscar usuario por email
        Optional<Usuario> optUsuario = usuarioRepository.buscarPorEmail(request.getEmail());
        if (optUsuario.isEmpty()) {
            throw new RuntimeException("No existe ninguna cuenta con ese email.");
        }

        Usuario usuario = optUsuario.get();

        // 3. Verificar contraseña
        if (!request.getPassword().equals(usuario.getPassword())) {
            throw new RuntimeException("Contraseña incorrecta. Inténtalo de nuevo.");
        }

        // 4. Crear sesión y retornar respuesta
        String token = sesionManager.crearSesion(usuario);

        return new LoginResponseDTO(
                "Bienvenido, " + usuario.getNombre() + "!",
                token,
                usuario.getId(),
                usuario.getNombre(),
                usuario.getEmail(),
                usuario.getRol(),
                usuario.getAvatarUrl()
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

    // ── Gestión de roles ──────────────────────────────────────────────────────

    @Override
    public Usuario cambiarRol(Long idObjetivo, String nuevoRol, String tokenAdmin) {
        // 1. Verificar que viene un token
        if (tokenAdmin == null || tokenAdmin.isBlank()) {
            throw new RuntimeException("Se requiere token de sesión para esta acción.");
        }

        // 2. Verificar que el token pertenece a un admin
        if (!sesionManager.esAdmin(tokenAdmin)) {
            throw new RuntimeException("Acceso denegado. Solo los administradores pueden cambiar roles.");
        }

        // 3. Verificar que el usuario objetivo existe
        Usuario objetivo = usuarioRepository.buscarPorId(idObjetivo);
        if (objetivo == null) {
            throw new RuntimeException("Usuario no encontrado con id: " + idObjetivo);
        }

        // 4. Validar el nuevo rol
        if (nuevoRol == null || (!nuevoRol.equalsIgnoreCase("ADMIN") && !nuevoRol.equalsIgnoreCase("USER"))) {
            throw new RuntimeException("Rol inválido. Los valores permitidos son: ADMIN, USER.");
        }

        // 5. Evitar que un admin se quite su propio rol accidentalmente
        Usuario adminActual = sesionManager.obtenerUsuario(tokenAdmin);
        if (adminActual.getId().equals(idObjetivo) && "USER".equalsIgnoreCase(nuevoRol)) {
            throw new RuntimeException("No puedes quitarte el rol de administrador a ti mismo.");
        }

        return usuarioRepository.cambiarRol(idObjetivo, nuevoRol.toUpperCase());
    }
}