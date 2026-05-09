package com.tuapp.reservasturismo.controller;

import com.tuapp.reservasturismo.dto.ProductoRequestDTO;
import com.tuapp.reservasturismo.dto.ProductoResponseDTO;
import com.tuapp.reservasturismo.service.ProductoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class ProductoController {

    private final ProductoService productoService;

    @PostMapping
    public ProductoResponseDTO crear(@RequestBody ProductoRequestDTO dto) {
        return productoService.crear(dto);
    }

    @GetMapping
    public List<ProductoResponseDTO> listar() {
        return productoService.listar();
    }

    @PutMapping("/{id}")
    public ProductoResponseDTO editar(@PathVariable Long id,
                                      @RequestBody ProductoRequestDTO dto) {
        return productoService.editar(id, dto);
    }

    @DeleteMapping("/{id}")
    public String eliminar(@PathVariable Long id) {
        productoService.eliminar(id);
        return "Producto eliminado";
    }

    @GetMapping("/categoria/{id}")
    public List<ProductoResponseDTO> filtrarPorCategoria(@PathVariable Long id) {
        return productoService.filtrarPorCategoria(id);
    }
}