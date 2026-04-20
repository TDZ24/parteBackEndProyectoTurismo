package com.tuapp.reservasturismo.controller;

import com.tuapp.reservasturismo.model.Destino;
import com.tuapp.reservasturismo.repository.DestinoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/destinos")
public class DestinoController {

    @Autowired
    private DestinoRepository destinoRepository;

    @GetMapping
    public List<Destino> listar() {
        return destinoRepository.listar();
    }

    @PostMapping
    public Destino crear(@RequestBody Destino destino) {
        return destinoRepository.guardar(destino);
    }
}