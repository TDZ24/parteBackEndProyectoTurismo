package com.tuapp.reservasturismo.service.impl;

import com.tuapp.reservasturismo.dto.ProductoRequestDTO;
import com.tuapp.reservasturismo.dto.ProductoResponseDTO;
import com.tuapp.reservasturismo.model.Categoria;
import com.tuapp.reservasturismo.model.Producto;
import com.tuapp.reservasturismo.repository.CategoriaRepository;
import com.tuapp.reservasturismo.repository.ProductoRepository;
import com.tuapp.reservasturismo.service.ProductoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;

    @Override
    public ProductoResponseDTO crear(ProductoRequestDTO dto) {
        Categoria categoria = categoriaRepository.findById(dto.getCategoriaId())
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada con id: " + dto.getCategoriaId()));

        Producto producto = Producto.builder()
                .nombre(dto.getNombre())
                .descripcion(dto.getDescripcion())
                .categoria(categoria)
                .build();

        return mapToDTO(productoRepository.save(producto));
    }

    @Override
    public List<ProductoResponseDTO> listar() {
        return productoRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ProductoResponseDTO editar(Long id, ProductoRequestDTO dto) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con id: " + id));

        Categoria categoria = categoriaRepository.findById(dto.getCategoriaId())
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada con id: " + dto.getCategoriaId()));

        producto.setNombre(dto.getNombre());
        producto.setDescripcion(dto.getDescripcion());
        producto.setCategoria(categoria);

        return mapToDTO(productoRepository.save(producto));
    }

    @Override
    public void eliminar(Long id) {
        if (!productoRepository.existsById(id)) {
            throw new RuntimeException("Producto no encontrado con id: " + id);
        }
        productoRepository.deleteById(id);
    }

    @Override
    public List<ProductoResponseDTO> filtrarPorCategoria(Long categoriaId) {
        return productoRepository.findByCategoriaId(categoriaId).stream()
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