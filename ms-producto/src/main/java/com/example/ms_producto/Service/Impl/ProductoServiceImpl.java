package com.example.ms_producto.Service.Impl;

import com.example.ms_producto.Entity.Producto;
import com.example.ms_producto.Repository.ProductoRepository;
import com.example.ms_producto.Service.ProductoService;
import com.example.ms_producto.dto.CategoriaDto;
import com.example.ms_producto.dto.ProductoDto;
import com.example.ms_producto.feign.CatalogoFeign;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class ProductoServiceImpl implements ProductoService {

    @Autowired
    private ProductoRepository productoRepository;
    @Autowired
    private CatalogoFeign catalogoFeign;

    @Override
    public List<Producto> listar() {
        return productoRepository.findAll();
    }

    @Override
    public ProductoDto buscarPorId(Integer id) {
        Producto producto = productoRepository.findById(id).get();
        CategoriaDto catagoriaDto = catalogoFeign.buscarPorId(producto.getCategoriaId());
        ProductoDto productoDto = new ProductoDto();
        productoDto.setId(producto.getId());
        productoDto.setNombre(producto.getNombre());
        productoDto.setPrecio(producto.getPrecio());
        productoDto.setStock(producto.getStock());
        productoDto.setCategoriaId(producto.getCategoriaId());
        productoDto.setCategoria(catagoriaDto);
        return productoDto;
    }

    @Override
    public Producto guardar(Producto producto) {
        return productoRepository.save(producto);
    }

    @Override
    public Producto actualizar(Producto producto) {
        if (!productoRepository.existsById(producto.getId())) {
            throw new RuntimeException("El producto con id " + producto.getId() + " no existe");
        }
        return productoRepository.save(producto);
    }

    @Override
    public void eliminar(Integer id) {
        productoRepository.deleteById(id);
    }
}

