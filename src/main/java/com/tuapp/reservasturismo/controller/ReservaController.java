package com.tuapp.reservasturismo.controller;

import com.tuapp.reservasturismo.model.Reserva;
import com.tuapp.reservasturismo.repository.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservas")
public class ReservaController {

    @Autowired
    private ReservaRepository reservaRepository;

    @GetMapping
    public List<Reserva> listar() {
        return reservaRepository.listar();
    }

    @PostMapping
    public Reserva crear(@RequestBody Reserva reserva) {
        return reservaRepository.guardar(reserva);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        reservaRepository.eliminar(id);
    }
}