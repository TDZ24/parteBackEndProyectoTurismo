package com.tuapp.reservasturismo.controller;

import com.tuapp.reservasturismo.dto.AuthRequestDTO;
import com.tuapp.reservasturismo.dto.AuthResponseDTO;
import com.tuapp.reservasturismo.dto.UsuarioRequestDTO;
import com.tuapp.reservasturismo.dto.UsuarioResponseDTO;
import com.tuapp.reservasturismo.model.Usuario;
import com.tuapp.reservasturismo.repository.UsuarioRepository;
import com.tuapp.reservasturismo.security.JwtService;

import com.tuapp.reservasturismo.service.UsuarioService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final UsuarioService usuarioService;
    private final UsuarioRepository usuarioRepository;
    private final JwtService jwtService;

    private final BCryptPasswordEncoder encoder =
            new BCryptPasswordEncoder();

    public AuthenticationController(UsuarioService usuarioService,
                                    UsuarioRepository usuarioRepository,
                                    JwtService jwtService) {

        this.usuarioService = usuarioService;
        this.usuarioRepository = usuarioRepository;
        this.jwtService = jwtService;
    }

    @PostMapping("/register")
    public UsuarioResponseDTO register(
            @RequestBody UsuarioRequestDTO dto) {

        return usuarioService.crear(dto);
    }

    @PostMapping("/login")
    public AuthResponseDTO login(
            @RequestBody AuthRequestDTO dto) {

        Usuario usuario = usuarioRepository
                .findByUsername(dto.getUsername())
                .orElseThrow(() ->
                        new RuntimeException("Usuario no encontrado"));

        boolean passwordCorrecta = encoder.matches(
                dto.getPassword(),
                usuario.getPassword()
        );

        if (!passwordCorrecta) {
            throw new RuntimeException("Contraseña incorrecta");
        }

        String token = jwtService.generarToken(
                usuario.getUsername()
        );

        return new AuthResponseDTO(token);
    }
}
