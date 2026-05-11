package com.tuapp.reservasturismo.controller;

import com.tuapp.reservasturismo.dto.LoginRequestDTO;
import com.tuapp.reservasturismo.dto.LoginResponseDTO;
import com.tuapp.reservasturismo.dto.api.ApiResponse;
import com.tuapp.reservasturismo.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Endpoints de autenticación:
 *   POST /api/auth/login   → iniciar sesión
 *   POST /api/auth/logout  → cerrar sesión
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UsuarioService usuarioService;

    public AuthController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponseDTO>> login(@Valid @RequestBody LoginRequestDTO request) {
        LoginResponseDTO response = usuarioService.login(request);
        return ResponseEntity.ok(ApiResponse.success("Login realizado correctamente.", response));
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<Void>> logout(@RequestHeader(value = "Authorization", required = false) String authHeader) {
        String token = extraerToken(authHeader);
        usuarioService.logout(token);
        return ResponseEntity.ok(ApiResponse.success("Sesión cerrada correctamente."));
    }

    private String extraerToken(String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new RuntimeException("Token de sesión no proporcionado.");
        }
        return authHeader.substring(7).trim();
    }
}
