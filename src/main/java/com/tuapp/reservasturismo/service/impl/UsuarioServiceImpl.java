package com.tuapp.reservasturismo.service.impl;

import com.tuapp.reservasturismo.dto.UsuarioRequestDTO;
import com.tuapp.reservasturismo.dto.UsuarioResponseDTO;
import com.tuapp.reservasturismo.exception.UsuarioNoEncontradoException;
import com.tuapp.reservasturismo.model.Usuario;
import com.tuapp.reservasturismo.repository.UsuarioRepository;
import com.tuapp.reservasturismo.service.UsuarioService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository repo;
    private final BCryptPasswordEncoder encoder; // FIX: inyectado como @Bean, no instanciado con new

    public UsuarioServiceImpl(UsuarioRepository repo, BCryptPasswordEncoder encoder) {
        this.repo = repo;
        this.encoder = encoder;
    }

    @Override
    public UsuarioResponseDTO crear(UsuarioRequestDTO dto) {

        if (repo.findByEmail(dto.getEmail()).isPresent()) {
            throw new RuntimeException("El email ya existe");
        }

        Usuario usuario = new Usuario();
        usuario.setUsername(dto.getUsername());
        usuario.setEmail(dto.getEmail());
        usuario.setPassword(encoder.encode(dto.getPassword()));
        usuario.setRol(dto.getRol());

        return mapToDTO(repo.save(usuario));
    }

    @Override
    public List<UsuarioResponseDTO> listar() {
        return repo.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public UsuarioResponseDTO editar(Long id, UsuarioRequestDTO dto) {

        Usuario usuario = repo.findById(id)
                .orElseThrow(() -> new UsuarioNoEncontradoException(id)); // FIX: excepción custom

        // FIX: validar que el nuevo email no esté en uso por otro usuario
        repo.findByEmail(dto.getEmail()).ifPresent(existente -> {
            if (!existente.getId().equals(id)) {
                throw new RuntimeException("El email ya está en uso por otro usuario");
            }
        });

        usuario.setUsername(dto.getUsername());
        usuario.setEmail(dto.getEmail());

        if (dto.getPassword() != null && !dto.getPassword().isEmpty()) {
            usuario.setPassword(encoder.encode(dto.getPassword()));
        }

        usuario.setRol(dto.getRol());

        return mapToDTO(repo.save(usuario));
    }

    @Override
    public void eliminar(Long id) {
        if (!repo.existsById(id)) {
            throw new UsuarioNoEncontradoException(id); // FIX: excepción custom
        }
        repo.deleteById(id);
    }

    private UsuarioResponseDTO mapToDTO(Usuario u) {
        return new UsuarioResponseDTO(
                u.getId(),
                u.getUsername(),
                u.getEmail(),
                u.getRol()
        );
    }
}
