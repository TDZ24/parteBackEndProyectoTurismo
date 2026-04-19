package com.tuapp;

import com.tuapp.model.Destino;
import com.tuapp.model.Reserva;
import com.tuapp.model.Usuario;
import com.tuapp.repository.impl.DestinoRepositoryImpl;
import com.tuapp.repository.impl.ReservaRepositoryImpl;
import com.tuapp.repository.impl.UsuarioRepositoryImpl;

import java.time.LocalDate;

public class MainPrueba {

    public static void main(String[] args) {

        // ── USUARIOS ──────────────────────────────────────
        UsuarioRepositoryImpl usuarioRepo = new UsuarioRepositoryImpl();

        usuarioRepo.guardar(new Usuario(null, "Thomas", "thomas@email.com"));
        usuarioRepo.guardar(new Usuario(null, "Maria", "maria@email.com"));

        System.out.println("=== USUARIOS ===");
        usuarioRepo.listar().forEach(u ->
                System.out.println(u.getId() + " - " + u.getNombre() + " - " + u.getEmail())
        );

        // ── DESTINOS ──────────────────────────────────────
        DestinoRepositoryImpl destinoRepo = new DestinoRepositoryImpl();

        destinoRepo.guardar(new Destino(null, "Cartagena", "Costa", 500000));
        destinoRepo.guardar(new Destino(null, "Medellín", "Antioquia", 300000));
        destinoRepo.guardar(new Destino(null, "San Andrés", "Isla", 800000));

        System.out.println("\n=== DESTINOS ===");
        destinoRepo.listar().forEach(d ->
                System.out.println(d.getId() + " - " + d.getNombre() + " - $" + d.getPrecio())
        );

        System.out.println("\n=== FILTRAR DESTINOS <= $400.000 ===");
        destinoRepo.filtrarPorPrecio(400000).forEach(d ->
                System.out.println(d.getNombre() + " - $" + d.getPrecio())
        );

        System.out.println("\n=== FILTRAR POR UBICACION: Isla ===");
        destinoRepo.filtrarPorUbicacion("Isla").forEach(d ->
                System.out.println(d.getNombre() + " - " + d.getUbicacion())
        );

        // ── RESERVAS ──────────────────────────────────────
        ReservaRepositoryImpl reservaRepo = new ReservaRepositoryImpl();

        reservaRepo.guardar(new Reserva(null, 1L, 1L,
                LocalDate.of(2025, 6, 1),
                LocalDate.of(2025, 6, 10),
                2, "ACTIVA"));

        reservaRepo.guardar(new Reserva(null, 1L, 2L,
                LocalDate.of(2025, 7, 1),
                LocalDate.of(2025, 7, 5),
                3, "ACTIVA"));

        reservaRepo.guardar(new Reserva(null, 2L, 3L,
                LocalDate.of(2025, 8, 1),
                LocalDate.of(2025, 8, 8),
                1, "CANCELADA"));

        System.out.println("\n=== RESERVAS ===");
        reservaRepo.listar().forEach(r ->
                System.out.println("Reserva " + r.getId() +
                        " | Usuario: " + r.getUsuarioId() +
                        " | Destino: " + r.getDestinoId() +
                        " | Estado: " + r.getEstado())
        );

        System.out.println("\n=== FILTRAR POR ESTADO: ACTIVA ===");
        reservaRepo.filtrarPorEstado("ACTIVA").forEach(r ->
                System.out.println("Reserva " + r.getId() + " - " + r.getEstado())
        );

        System.out.println("\n=== CAMBIAR ESTADO reserva 1 a CANCELADA ===");
        reservaRepo.cambiarEstado(1L, "CANCELADA");
        System.out.println("Estado nuevo: " + reservaRepo.buscarPorId(1L).getEstado());

        System.out.println("\n=== ELIMINAR usuario 2 ===");
        usuarioRepo.eliminar(2L);
        System.out.println("Usuarios tras eliminar:");
        usuarioRepo.listar().forEach(u -> System.out.println(u.getNombre()));
    }
}