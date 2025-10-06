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

    @CircuitBreaker(name = "categoriaListarPorIdCB", fallbackMethod = "fallbackBuscarPorId")
    public CategoriaDto buscarPorId(Integer id) {
        return catalogoFeign.buscarPorId(id);
    }

    public CategoriaDto fallbackBuscarPorId(Integer id, Throwable e) {
        CategoriaDto categoria = new CategoriaDto();
        categoria.setId(9000000);
        categoria.setNombre("Servicio Categoria no Disponible");
        categoria.setDescripcion("Fallback activado porque ms-catalogo no está disponible");
        return categoria;
    }

    @CircuitBreaker(name = "guardarCategoriaCB", fallbackMethod = "fallbackGuardar")
    public CategoriaDto guardar(CategoriaDto categoria) {
        return catalogoFeign.guardar(categoria);
    }

    public CategoriaDto fallbackGuardar(CategoriaDto categoria, Throwable e) {
        CategoriaDto fallback = new CategoriaDto();
        fallback.setId(9000001);
        fallback.setNombre("No se pudo validar/guardar la categoría");
        fallback.setDescripcion("Servicio catalogo no disponible");
        return fallback;
    }
}
