package com.tuapp.reservasturismo.controller;
import com.tuapp.reservasturismo.model.Categoria;
import com.tuapp.reservasturismo.repository.CategoriaRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

    private CategoriaRepository repo = new CategoriaRepository();

    @GetMapping
    public List<Categoria> listar() {
        return repo.listar();
    }
}
