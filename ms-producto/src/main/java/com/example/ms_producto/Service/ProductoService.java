package com.example.ms_producto.Service;

import com.example.ms_producto.Entity.Producto;
import com.example.ms_producto.dto.ProductoDto;

import java.util.List;
import java.util.Optional;

public interface ProductoService {
    List<ProductoDto> listar();
    ProductoDto buscarPorId(Integer id);
    ProductoDto guardar(Producto producto);
    ProductoDto actualizar(Producto producto);
    void eliminar(Integer id);
}