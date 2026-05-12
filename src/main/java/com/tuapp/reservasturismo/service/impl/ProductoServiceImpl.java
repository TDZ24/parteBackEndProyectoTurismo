package com.tuapp.reservasturismo.service.impl;

import com.tuapp.reservasturismo.dto.ProductoRequestDTO;
import com.tuapp.reservasturismo.dto.ProductoResponseDTO;
import com.tuapp.reservasturismo.exception.ProductoNoEncontradoException;
import com.tuapp.reservasturismo.exception.ReservaException;
import com.tuapp.reservasturismo.model.Categoria;
import com.tuapp.reservasturismo.model.Producto;
import com.tuapp.reservasturismo.repository.CategoriaRepository;
import com.tuapp.reservasturismo.repository.ProductoRepository;
import com.tuapp.reservasturismo.service.ProductoService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;

    public ProductoServiceImpl(
            ProductoRepository productoRepository,
            CategoriaRepository categoriaRepository
    ) {
        this.productoRepository = productoRepository;
        this.categoriaRepository = categoriaRepository;
    }

    @Override
    public ProductoResponseDTO crear(ProductoRequestDTO dto) {

        Categoria categoria = categoriaRepository
                .findById(dto.getCategoriaId())
                .orElseThrow(() ->
                        new ReservaException(
                                "Categoría no encontrada con ID: "
                                        + dto.getCategoriaId(),
                                "CATEGORIA_NO_ENCONTRADA"
                        )
                );

        Producto producto = new Producto(
                null,
                dto.getNombre(),
                dto.getDescripcion(),
                dto.getPrecio(),
                dto.getCaracteristicas(),
                categoria
        );

        return convertirDTO(
                productoRepository.save(producto)
        );
    }

    @Override
    public List<ProductoResponseDTO> listar() {

        return productoRepository.findAll()
                .stream()
                .map(this::convertirDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ProductoResponseDTO editar(
            Long id,
            ProductoRequestDTO dto
    ) {

        Producto producto = productoRepository
                .findById(id)
                .orElseThrow(() ->
                        new ProductoNoEncontradoException(id)
                );

        Categoria categoria = categoriaRepository
                .findById(dto.getCategoriaId())
                .orElseThrow(() ->
                        new ReservaException(
                                "Categoría no encontrada con ID: "
                                        + dto.getCategoriaId(),
                                "CATEGORIA_NO_ENCONTRADA"
                        )
                );

        producto.setNombre(dto.getNombre());
        producto.setDescripcion(dto.getDescripcion());
        producto.setPrecio(dto.getPrecio());
        producto.setCaracteristicas(dto.getCaracteristicas());
        producto.setCategoria(categoria);

        return convertirDTO(
                productoRepository.save(producto)
        );
    }

    @Override
    public void eliminar(Long id) {

        if (!productoRepository.existsById(id)) {
            throw new ProductoNoEncontradoException(id);
        }

        productoRepository.deleteById(id);
    }

    @Override
    public List<ProductoResponseDTO> filtrarPorCategoria(
            Long categoriaId
    ) {

        if (!categoriaRepository.existsById(categoriaId)) {

            throw new ReservaException(
                    "Categoría no encontrada con ID: "
                            + categoriaId,
                    "CATEGORIA_NO_ENCONTRADA"
            );
        }

        return productoRepository
                .findByCategoriaId(categoriaId)
                .stream()
                .map(this::convertirDTO)
                .collect(Collectors.toList());
    }

    private ProductoResponseDTO convertirDTO(
            Producto producto
    ) {

        return new ProductoResponseDTO(
                producto.getId(),
                producto.getNombre(),
                producto.getDescripcion(),
                producto.getPrecio(),
                producto.getCaracteristicas(),
                producto.getCategoria().getNombre()
        );
    }
}