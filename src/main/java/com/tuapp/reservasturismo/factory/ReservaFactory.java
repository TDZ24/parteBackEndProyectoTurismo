package com.tuapp.reservasturismo.factory;

import com.tuapp.reservasturismo.model.Reserva;
import com.tuapp.reservasturismo.model.Usuario;
import com.tuapp.reservasturismo.model.Producto;

public class ReservaFactory {

    public static Reserva crear(Usuario usuario, Producto producto, int cantidadPersonas) {
        Reserva reserva = new Reserva();
        reserva.setUsuario(usuario);
        reserva.setProducto(producto);
        reserva.setCantidadPersonas(cantidadPersonas);
        reserva.setEstado(Reserva.EstadoReserva.ACTIVA);
        return reserva;
    }
}