package com.example.ms_producto.Service;

import com.example.ms_producto.dto.CategoriaDto;
import com.example.ms_producto.feign.CatalogoFeign;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.stereotype.Service;

@Service
public class CatalogoService {

    private final CatalogoFeign catalogoFeign;

    public CatalogoService(CatalogoFeign catalogoFeign) {
        this.catalogoFeign = catalogoFeign;
    }

    @CircuitBreaker(name = "categoriaListarPorIdCB", fallbackMethod = "fallbackCategoria")
    public CategoriaDto buscarPorId(Integer id) {
        return catalogoFeign.buscarPorId(id);
    }

    public CategoriaDto fallbackCategoria(Integer id, Throwable e) {
        CategoriaDto categoria = new CategoriaDto();
        categoria.setId(9000000);
        categoria.setNombre("Servicio Categoría no disponible");
        return categoria;
    }
}
