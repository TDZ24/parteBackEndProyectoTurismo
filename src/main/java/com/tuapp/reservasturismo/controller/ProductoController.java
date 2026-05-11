package com.tuapp.reservasturismo.controller;

import com.tuapp.reservasturismo.dto.ProductoRequestDTO;
import com.tuapp.reservasturismo.dto.ProductoResponseDTO;
import com.tuapp.reservasturismo.dto.api.ApiResponse;
import com.tuapp.reservasturismo.service.ProductoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/productos")
public class ProductoController {
    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ProductoResponseDTO>> crear(@Valid @RequestBody ProductoRequestDTO dto) {
        ProductoResponseDTO creado = productoService.crear(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Producto creado correctamente.", creado));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ProductoResponseDTO>>> listar() {
        return ResponseEntity.ok(ApiResponse.success("Productos obtenidos correctamente.", productoService.listar()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductoResponseDTO>> editar(@PathVariable Long id,
                                                                   @Valid @RequestBody ProductoRequestDTO dto) {
        ProductoResponseDTO editado = productoService.editar(id, dto);
        return ResponseEntity.ok(ApiResponse.success("Producto actualizado correctamente.", editado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable Long id) {
        productoService.eliminar(id);
        return ResponseEntity.ok(ApiResponse.success("Producto eliminado correctamente."));
    }

    @GetMapping("/categoria/{categoriaId}")
    public ResponseEntity<ApiResponse<List<ProductoResponseDTO>>> filtrarPorCategoria(@PathVariable Long categoriaId) {
        return ResponseEntity.ok(ApiResponse.success(
                "Productos filtrados por categoría correctamente.",
                productoService.filtrarPorCategoria(categoriaId)
        ));
    }
}
