package com.tuapp.reservasturismo.service.impl;

import com.tuapp.reservasturismo.dto.ProductoRequestDTO;
import com.tuapp.reservasturismo.dto.ProductoResponseDTO;
import com.tuapp.reservasturismo.model.Categoria;
import com.tuapp.reservasturismo.model.Producto;
import com.tuapp.reservasturismo.repository.CategoriaRepository;
import com.tuapp.reservasturismo.repository.ProductoRepository;
import com.tuapp.reservasturismo.service.ProductoService;

import java.util.List;
import java.util.stream.Collectors;

public class ProductoServiceImpl implements ProductoService {

    private ProductoRepository productoRepo = new ProductoRepository();
    private CategoriaRepository categoriaRepo = new CategoriaRepository();

    @Override
    public ProductoResponseDTO crear(ProductoRequestDTO dto) {

        Categoria categoria = categoriaRepo.buscarPorId(dto.getCategoriaId());

        if (categoria == null) {
            throw new RuntimeException("Categoría no válida");
        }

        Producto producto = new Producto(
                null,
                dto.getNombre(),
                dto.getDescripcion(),
                categoria
        );

        Producto guardado = productoRepo.guardar(producto);

        return mapToDTO(guardado);
    }

    @Override
    public List<ProductoResponseDTO> listar() {
        return productoRepo.listar().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ProductoResponseDTO editar(Long id, ProductoRequestDTO dto) {

        Producto producto = productoRepo.listar().stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        Categoria categoria = categoriaRepo.buscarPorId(dto.getCategoriaId());

        if (categoria == null) {
            throw new RuntimeException("Categoría no válida");
        }

        producto.setCategoria(categoria);

        return mapToDTO(producto);
    }

    @Override
    public void eliminar(Long id) {
        productoRepo.listar().removeIf(p -> p.getId().equals(id));
    }

    @Override
    public List<ProductoResponseDTO> filtrarPorCategoria(Long categoriaId) {

        return productoRepo.listar().stream()
                .filter(p -> p.getCategoria().getId().equals(categoriaId))
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private ProductoResponseDTO mapToDTO(Producto p) {
        return new ProductoResponseDTO(
                p.getId(),
                p.getNombre(),
                p.getDescripcion(),
                p.getCategoria().getNombre()
        );
    }
}