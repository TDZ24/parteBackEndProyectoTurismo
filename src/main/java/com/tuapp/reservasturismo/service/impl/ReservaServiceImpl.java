package com.tuapp.reservasturismo.service.impl;

import com.tuapp.reservasturismo.model.Producto;
import com.tuapp.reservasturismo.model.Reserva;
import com.tuapp.reservasturismo.model.Usuario;
import com.tuapp.reservasturismo.repository.ProductoRepository;
import com.tuapp.reservasturismo.repository.ReservaRepository;
import com.tuapp.reservasturismo.repository.UsuarioRepository;
import com.tuapp.reservasturismo.service.ReservaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservaServiceImpl implements ReservaService {

    private final ReservaRepository reservaRepository;
    private final UsuarioRepository usuarioRepository;
    private final ProductoRepository productoRepository;

    @Override
    public Reserva crearReserva(Reserva reserva) {
        Usuario usuario = usuarioRepository.findById(reserva.getUsuario().getId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Producto producto = productoRepository.findById(reserva.getProducto().getId())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        reserva.setUsuario(usuario);
        reserva.setProducto(producto);
        reserva.setEstado(Reserva.EstadoReserva.ACTIVA);

        return reservaRepository.save(reserva);
    }

    @Override
    public List<Reserva> listarReservas() {
        return reservaRepository.findAll();
    }

    @Override
    public Reserva buscarPorId(Long id) {
        return reservaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada con id: " + id));
    }

    @Override
    public Reserva actualizarReserva(Long id, Reserva reserva) {
        Reserva existente = buscarPorId(id);
        existente.setEstado(reserva.getEstado());
        return reservaRepository.save(existente);
    }

    @Override
    public List<Reserva> filtrarPorUsuario(Long usuarioId) {
        return reservaRepository.findByUsuarioId(usuarioId);
    }

    @Override
    public List<Reserva> filtrarPorDestino(Long productoId) {
        return reservaRepository.findByProductoId(productoId);
    }

    @Override
    public List<Reserva> filtrarPorEstado(String estado) {
        return reservaRepository.findByEstado(Reserva.EstadoReserva.valueOf(estado));
    }

    @Override
    public void cambiarEstado(Long id, String estado) {
        Reserva reserva = buscarPorId(id);
        reserva.setEstado(Reserva.EstadoReserva.valueOf(estado));
        reservaRepository.save(reserva);
    }

    @Override
    public void eliminarReserva(Long id) {
        if (!reservaRepository.existsById(id)) {
            throw new RuntimeException("Reserva no encontrada con id: " + id);
        }
        reservaRepository.deleteById(id);
    }
}