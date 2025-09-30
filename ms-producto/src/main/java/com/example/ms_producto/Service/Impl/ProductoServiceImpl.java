package com.example.ms_producto.Service.Impl;

import com.example.ms_producto.Entity.Producto;
import com.example.ms_producto.Repository.ProductoRepository;
import com.example.ms_producto.Service.CatalogoService;
import com.example.ms_producto.Service.ProductoService;
import com.example.ms_producto.dto.CategoriaDto;
import com.example.ms_producto.dto.ProductoDto;
import com.example.ms_producto.feign.CatalogoFeign;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository productoRepository;
    private final CatalogoService catalogoService;

    public ProductoServiceImpl(ProductoRepository productoRepository, CatalogoService catalogoService) {
        this.productoRepository = productoRepository;
        this.catalogoService = catalogoService;
    }

    @Override
    public List<ProductoDto> listar() {
        return productoRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public ProductoDto buscarPorId(Integer id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        return mapToDto(producto);
    }

    @Override
    public ProductoDto guardar(Producto producto) {
        CategoriaDto categoria = catalogoService.buscarPorId(producto.getCategoriaId());

        Producto guardado = productoRepository.save(producto);

        ProductoDto dto = new ProductoDto();
        dto.setId(guardado.getId());
        dto.setNombre(guardado.getNombre());
        dto.setPrecio(guardado.getPrecio());
        dto.setStock(guardado.getStock());
        dto.setCategoriaId(guardado.getCategoriaId());
        dto.setCategoria(categoria);
        return dto;
    }

    @Override
    public ProductoDto actualizar(Producto producto) {
        if (!productoRepository.existsById(producto.getId())) {
            throw new RuntimeException("El producto con id " + producto.getId() + " no existe");
        }
        Producto actualizado = productoRepository.save(producto);
        return mapToDto(actualizado);
    }

    @Override
    public void eliminar(Integer id) {
        productoRepository.deleteById(id);
    }

    private ProductoDto mapToDto(Producto producto) {
        CategoriaDto categoriaDto = catalogoService.buscarPorId(producto.getCategoriaId());
        ProductoDto dto = new ProductoDto();
        dto.setId(producto.getId());
        dto.setNombre(producto.getNombre());
        dto.setPrecio(producto.getPrecio());
        dto.setStock(producto.getStock());
        dto.setCategoriaId(producto.getCategoriaId());
        dto.setCategoria(categoriaDto);
        return dto;
    }
}

