package com.tuapp.reservasturismo.repository;
import com.tuapp.reservasturismo.model.Producto;
import java.util.*;

public class ProductoRepository {

    private List<Producto> productos = new ArrayList<>();
    private Long contador = 1L;

    public Producto guardar(Producto producto) {
        producto = new Producto(
                contador++,
                producto.getNombre(),
                producto.getDescripcion(),
                producto.getCategoria()
        );
        productos.add(producto);
        return producto;
    }

    public List<Producto> listar() {
        return productos;
    }
}
