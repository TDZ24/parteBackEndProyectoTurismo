package com.tuapp.reservasturismo.controller;

import com.tuapp.reservasturismo.model.Destino;
import com.tuapp.reservasturismo.service.DestinoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/destinos")
public class DestinoController {

    private final DestinoService destinoService;

    public DestinoController(DestinoService destinoService) {
        this.destinoService = destinoService;
    }

    @GetMapping
    public List<Destino> listar() {
        return destinoService.listarDestinos();
    }

    @GetMapping("/{id}")
    public Destino buscarPorId(@PathVariable Long id) {
        return destinoService.buscarPorId(id);
    }

    @PostMapping
    public Destino crear(@RequestBody Destino destino) {
        return destinoService.crearDestino(destino);
    }

    @PutMapping("/{id}")
    public Destino actualizar(@PathVariable Long id, @RequestBody Destino destino) {
        return destinoService.actualizarDestino(id, destino);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        destinoService.eliminarDestino(id);
    }

    @GetMapping("/filtrar/precio")
    public List<Destino> filtrarPorPrecio(@RequestParam double precioMax) {
        return destinoService.filtrarPorPrecio(precioMax);
    }

    @GetMapping("/filtrar/ubicacion")
    public List<Destino> filtrarPorUbicacion(@RequestParam String ubicacion) {
        return destinoService.filtrarPorUbicacion(ubicacion);
    }
}