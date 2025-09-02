package com.example.ms_producto.Service;

import com.example.ms_producto.Entity.Producto;

import java.util.List;
import java.util.Optional;

public interface ProductoService {
    List<Producto> listar();
    Optional<Producto> buscarPorId(Integer id);
    Producto guardar(Producto producto);
    Producto actualizar(Producto producto);
    void eliminar(Integer id);
}
