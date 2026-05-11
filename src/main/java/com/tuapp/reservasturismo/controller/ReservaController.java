package com.tuapp.reservasturismo.controller;

import com.tuapp.reservasturismo.dto.ReservaRequestDTO;
import com.tuapp.reservasturismo.dto.ReservaResponseDTO;
import com.tuapp.reservasturismo.dto.api.ApiResponse;
import com.tuapp.reservasturismo.service.ReservaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservas")
@RequiredArgsConstructor
public class ReservaController {

    private final ReservaService reservaService;

    @PostMapping
    public ResponseEntity<ApiResponse<ReservaResponseDTO>> crear(@Valid @RequestBody ReservaRequestDTO dto) {
        ReservaResponseDTO creada = reservaService.crearReserva(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Reserva creada correctamente.", creada));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<List<ReservaResponseDTO>>> listar() {
        return ResponseEntity.ok(ApiResponse.success("Reservas obtenidas correctamente.", reservaService.listarReservas()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ReservaResponseDTO>> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success("Reserva obtenida correctamente.", reservaService.buscarPorId(id)));
    }

    @PatchMapping("/{id}/cancelar")
    public ResponseEntity<ApiResponse<ReservaResponseDTO>> cancelar(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success("Reserva cancelada correctamente.", reservaService.cancelarReserva(id)));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable Long id) {
        reservaService.eliminarReserva(id);
        return ResponseEntity.ok(ApiResponse.success("Reserva eliminada correctamente."));
    }

    @GetMapping("/filtrar/usuario/{usuarioId}")
    public ResponseEntity<ApiResponse<List<ReservaResponseDTO>>> filtrarPorUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(ApiResponse.success(
                "Reservas filtradas por usuario correctamente.",
                reservaService.filtrarPorUsuario(usuarioId)
        ));
    }

    @GetMapping("/filtrar/producto/{productoId}")
    public ResponseEntity<ApiResponse<List<ReservaResponseDTO>>> filtrarPorProducto(@PathVariable Long productoId) {
        return ResponseEntity.ok(ApiResponse.success(
                "Reservas filtradas por producto correctamente.",
                reservaService.filtrarPorProducto(productoId)
        ));
    }

    @GetMapping("/filtrar/estado/{estado}")
    public ResponseEntity<ApiResponse<List<ReservaResponseDTO>>> filtrarPorEstado(@PathVariable String estado) {
        return ResponseEntity.ok(ApiResponse.success(
                "Reservas filtradas por estado correctamente.",
                reservaService.filtrarPorEstado(estado)
        ));
    }
}
