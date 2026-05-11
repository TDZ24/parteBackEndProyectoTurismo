package com.tuapp.reservasturismo.controller;

import com.tuapp.reservasturismo.dto.RolRequestDTO;
import com.tuapp.reservasturismo.dto.api.ApiResponse;
import com.tuapp.reservasturismo.model.Usuario;
import com.tuapp.reservasturismo.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Usuario>>> listar() {
        return ResponseEntity.ok(ApiResponse.success("Usuarios obtenidos correctamente.", usuarioService.listarUsuarios()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Usuario>> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success("Usuario obtenido correctamente.", usuarioService.buscarPorId(id)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Usuario>> crear(@Valid @RequestBody Usuario usuario) {
        Usuario creado = usuarioService.crearUsuario(usuario);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Usuario creado correctamente.", creado));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Usuario>> actualizar(@PathVariable Long id,
                                                          @Valid @RequestBody Usuario usuario) {
        Usuario actualizado = usuarioService.actualizarUsuario(id, usuario);
        return ResponseEntity.ok(ApiResponse.success("Usuario actualizado correctamente.", actualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable Long id) {
        usuarioService.eliminarUsuario(id);
        return ResponseEntity.ok(ApiResponse.success("Usuario eliminado correctamente."));
    }

    @PutMapping("/{id}/rol")
    public ResponseEntity<ApiResponse<Usuario>> cambiarRol(
            @PathVariable Long id,
            @Valid @RequestBody RolRequestDTO request,
            @RequestHeader(value = "Authorization", required = false) String authHeader) {
        String token = extraerToken(authHeader);
        Usuario actualizado = usuarioService.cambiarRol(id, request.getRol(), token);
        String accion = "ADMIN".equalsIgnoreCase(actualizado.getRol().name())
                ? "Rol de administrador asignado correctamente."
                : "Rol de administrador removido correctamente.";
        return ResponseEntity.ok(ApiResponse.success(accion, actualizado));
    }

    private String extraerToken(String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new RuntimeException("Token de sesión no proporcionado.");
        }
        return authHeader.substring(7).trim();
    }
}
