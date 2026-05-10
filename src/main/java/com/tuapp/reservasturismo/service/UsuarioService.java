package com.tuapp.reservasturismo.service;

import com.tuapp.reservasturismo.dto.*;

import java.util.List;

public interface UsuarioService {

    UsuarioResponseDTO crear(UsuarioRequestDTO dto);
    List<UsuarioResponseDTO> listar();
    UsuarioResponseDTO editar(Long id, UsuarioRequestDTO dto);
    void eliminar(Long id);
}