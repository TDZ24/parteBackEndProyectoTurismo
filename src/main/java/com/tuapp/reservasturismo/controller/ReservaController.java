package com.tuapp.reservasturismo.controller;

import com.tuapp.reservasturismo.model.Reserva;
import com.tuapp.reservasturismo.service.ReservaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservas")
public class ReservaController {

    private final ReservaService reservaService;

    public ReservaController(ReservaService reservaService) {
        this.reservaService = reservaService;
    }

    @GetMapping
    public List<Reserva> listar() {
        return reservaService.listarReservas();
    }

    @GetMapping("/{id}")
    public Reserva buscarPorId(@PathVariable Long id) {
        return reservaService.buscarPorId(id);
    }

    @PostMapping
    public Reserva crear(@RequestBody Reserva reserva) {
        return reservaService.crearReserva(reserva);
    }

    @PutMapping("/{id}")
    public Reserva actualizar(@PathVariable Long id, @RequestBody Reserva reserva) {
        return reservaService.actualizarReserva(id, reserva);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        reservaService.eliminarReserva(id);
    }

    @GetMapping("/filtrar/usuario/{usuarioId}")
    public List<Reserva> filtrarPorUsuario(@PathVariable Long usuarioId) {
        return reservaService.filtrarPorUsuario(usuarioId);
    }

    @GetMapping("/filtrar/destino/{destinoId}")
    public List<Reserva> filtrarPorDestino(@PathVariable Long destinoId) {
        return reservaService.filtrarPorDestino(destinoId);
    }

    @GetMapping("/filtrar/estado/{estado}")
    public List<Reserva> filtrarPorEstado(@PathVariable String estado) {
        return reservaService.filtrarPorEstado(estado);
    }

    @PatchMapping("/{id}/estado")
    public void cambiarEstado(@PathVariable Long id, @RequestParam String estado) {
        reservaService.cambiarEstado(id, estado);
    }
}