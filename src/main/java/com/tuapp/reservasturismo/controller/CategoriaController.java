package com.tuapp.reservasturismo.controller;
import com.tuapp.reservasturismo.dto.CategoriaRequestDTO;
import com.tuapp.reservasturismo.dto.CategoriaResponseDTO;
import com.tuapp.reservasturismo.service.CategoriaService;
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
    public CategoriaResponseDTO crear(@RequestBody CategoriaRequestDTO dto) {
        return categoriaService.crear(dto);
    }
    @GetMapping
    public List<CategoriaResponseDTO> listar() {
        return categoriaService.listar();
    }
}