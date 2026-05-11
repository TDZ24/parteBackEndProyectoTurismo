package com.tuapp.reservasturismo.controller;

import com.tuapp.reservasturismo.dto.AuthRequestDTO;
import com.tuapp.reservasturismo.dto.AuthResponseDTO;
import com.tuapp.reservasturismo.dto.UsuarioRequestDTO;
import com.tuapp.reservasturismo.dto.UsuarioResponseDTO;
import com.tuapp.reservasturismo.dto.api.ApiResponse;
import com.tuapp.reservasturismo.exception.CredencialesInvalidasException;
import com.tuapp.reservasturismo.model.Usuario;
import com.tuapp.reservasturismo.repository.UsuarioRepository;
import com.tuapp.reservasturismo.security.JwtService;
import com.tuapp.reservasturismo.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final UsuarioService usuarioService;
    private final UsuarioRepository usuarioRepository;
    private final JwtService jwtService;
    private final BCryptPasswordEncoder encoder;

    public AuthenticationController(UsuarioService usuarioService,
                                    UsuarioRepository usuarioRepository,
                                    JwtService jwtService,
                                    BCryptPasswordEncoder encoder) {
        this.usuarioService = usuarioService;
        this.usuarioRepository = usuarioRepository;
        this.jwtService = jwtService;
        this.encoder = encoder;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UsuarioResponseDTO>> register(@Valid @RequestBody UsuarioRequestDTO dto) {
        UsuarioResponseDTO creado = usuarioService.crear(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Usuario registrado correctamente.", creado));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponseDTO>> login(@Valid @RequestBody AuthRequestDTO dto) {

        Usuario usuario = usuarioRepository
                .findByUsername(dto.getUsername())
                .orElseThrow(() -> new CredencialesInvalidasException("Usuario no encontrado"));

        boolean passwordCorrecta = encoder.matches(
                dto.getPassword(),
                usuario.getPassword()
        );

        if (!passwordCorrecta) {
            throw new CredencialesInvalidasException("Contraseña incorrecta");
        }

        String token = jwtService.generarToken(usuario.getUsername(), usuario.getRol().name());

        return ResponseEntity.ok(ApiResponse.success("Login realizado correctamente.", new AuthResponseDTO(token)));
    }
}
