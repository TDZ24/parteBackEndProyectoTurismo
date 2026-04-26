package com.tuapp.reservasturismo.controller;

import com.tuapp.reservasturismo.dto.ProductoRequestDTO;
import com.tuapp.reservasturismo.dto.ProductoResponseDTO;
import com.tuapp.reservasturismo.service.impl.ProductoServiceImpl;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/productos")
public class ProductoController {

    private ProductoServiceImpl service = new ProductoServiceImpl();

    @PostMapping
    public ProductoResponseDTO crear(@RequestBody ProductoRequestDTO dto) {
        return service.crear(dto);
    }

    @GetMapping
    public List<ProductoResponseDTO> listar() {
        return service.listar();
    }

    @PutMapping("/{id}")
    public ProductoResponseDTO editar(@PathVariable Long id, @RequestBody ProductoRequestDTO dto) {
        return service.editar(id, dto);
    }

    @DeleteMapping("/{id}")
    public String eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return "Producto eliminado";
    }

    @GetMapping("/categoria/{id}")
    public List<ProductoResponseDTO> filtrar(@PathVariable Long id) {
        return service.filtrarPorCategoria(id);
    }
}