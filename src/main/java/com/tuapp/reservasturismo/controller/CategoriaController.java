package com.tuapp.reservasturismo.controller;

import com.tuapp.reservasturismo.dto.CategoriaRequestDTO;
import com.tuapp.reservasturismo.dto.CategoriaResponseDTO;
import com.tuapp.reservasturismo.dto.api.ApiResponse;
import com.tuapp.reservasturismo.service.CategoriaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {
    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CategoriaResponseDTO>> crear(@Valid @RequestBody CategoriaRequestDTO dto) {
        CategoriaResponseDTO creada = categoriaService.crear(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Categoría creada correctamente.", creada));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<CategoriaResponseDTO>>> listar() {
        return ResponseEntity.ok(ApiResponse.success("Categorías obtenidas correctamente.", categoriaService.listar()));
    }
}
