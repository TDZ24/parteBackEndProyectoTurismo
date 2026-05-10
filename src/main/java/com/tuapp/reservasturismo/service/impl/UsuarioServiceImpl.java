package com.tuapp.reservasturismo.service.impl;

import com.tuapp.reservasturismo.dto.UsuarioRequestDTO;
import com.tuapp.reservasturismo.dto.UsuarioResponseDTO;
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
    private final BCryptPasswordEncoder encoder;

    public UsuarioServiceImpl(UsuarioRepository repo) {
        this.repo = repo;
        this.encoder = new BCryptPasswordEncoder();
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
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

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
            throw new RuntimeException("Usuario no existe");
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