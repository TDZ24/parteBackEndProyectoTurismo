package com.tuapp.reservasturismo.service.impl;

import com.tuapp.reservasturismo.model.Destino;
import com.tuapp.reservasturismo.model.Reserva;
import com.tuapp.reservasturismo.model.Usuario;
import com.tuapp.reservasturismo.repository.DestinoRepository;
import com.tuapp.reservasturismo.repository.ReservaRepository;
import com.tuapp.reservasturismo.repository.UsuarioRepository;
import com.tuapp.reservasturismo.service.ReservaService;
import com.tuapp.reservasturismo.service.email.EmailService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservaServiceImpl implements ReservaService {

    private final ReservaRepository   reservaRepository;
    private final UsuarioRepository   usuarioRepository;
    private final DestinoRepository   destinoRepository;
    private final EmailService        emailService;

    public ReservaServiceImpl(ReservaRepository reservaRepository,
                              UsuarioRepository usuarioRepository,
                              DestinoRepository destinoRepository,
                              EmailService emailService) {
        this.reservaRepository = reservaRepository;
        this.usuarioRepository = usuarioRepository;
        this.destinoRepository = destinoRepository;
        this.emailService      = emailService;
    }

    @Override
    public Reserva crearReserva(Reserva reserva) {
        // ── Validaciones ──────────────────────────────────────────────────────
        if (reserva.getFechaInicio() == null || reserva.getFechaFin() == null) {
            throw new RuntimeException("Las fechas no pueden estar vacías.");
        }
        if (reserva.getFechaFin().isBefore(reserva.getFechaInicio())) {
            throw new RuntimeException("La fecha fin no puede ser antes que la fecha inicio.");
        }
        if (reserva.getCantidadPersonas() <= 0) {
            throw new RuntimeException("La cantidad de personas debe ser mayor a 0.");
        }
        if (reserva.getUsuarioId() == null) {
            throw new RuntimeException("Debe indicar el usuario que hace la reserva.");
        }
        if (reserva.getDestinoId() == null) {
            throw new RuntimeException("Debe indicar el destino de la reserva.");
        }

        // ── Guardar ───────────────────────────────────────────────────────────
        reserva.setEstado("ACTIVA");
        Reserva guardada = reservaRepository.guardar(reserva);

        // ── Enviar correo de confirmación ────────────────────────────────────
        Usuario usuario = usuarioRepository.buscarPorId(guardada.getUsuarioId());
        Destino destino = destinoRepository.buscarPorId(guardada.getDestinoId());

        if (usuario != null && usuario.getEmail() != null) {
            emailService.enviarConfirmacionReserva(usuario, guardada, destino);
        }

        return guardada;
    }

    @Override
    public List<Reserva> listarReservas() {
        return reservaRepository.listar();
    }

    @Override
    public Reserva buscarPorId(Long id) {
        Reserva reserva = reservaRepository.buscarPorId(id);
        if (reserva == null) {
            throw new RuntimeException("Reserva no encontrada con id: " + id);
        }
        return reserva;
    }

    @Override
    public Reserva actualizarReserva(Long id, Reserva reserva) {
        if (reserva.getFechaFin().isBefore(reserva.getFechaInicio())) {
            throw new RuntimeException("La fecha fin no puede ser antes que la fecha inicio.");
        }
        return reservaRepository.actualizar(id, reserva);
    }

    @Override
    public List<Reserva> filtrarPorUsuario(Long usuarioId) {
        return reservaRepository.filtrarPorUsuario(usuarioId);
    }

    @Override
    public List<Reserva> filtrarPorDestino(Long destinoId) {
        return reservaRepository.filtrarPorDestino(destinoId);
    }

    @Override
    public List<Reserva> filtrarPorEstado(String estado) {
        return reservaRepository.filtrarPorEstado(estado);
    }

    @Override
    public void cambiarEstado(Long id, String estado) {
        if (!estado.equals("ACTIVA") && !estado.equals("CANCELADA")) {
            throw new RuntimeException("Estado inválido. Use ACTIVA o CANCELADA.");
        }
        reservaRepository.cambiarEstado(id, estado);
    }

    @Override
    public void eliminarReserva(Long id) {
        reservaRepository.eliminar(id);
    }
}