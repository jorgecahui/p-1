package com.example.ms_producto.feign;

import com.example.ms_producto.dto.CategoriaDto;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "ms-catalogo", path = "/categoria")
public interface CatalogoFeign {

    @GetMapping("/{id}")
    @CircuitBreaker(name = "categoriaListarPorIdCB", fallbackMethod = "fallbackCategoria")
    CategoriaDto buscarPorId(@PathVariable("id") Integer id);

    @PostMapping
    @CircuitBreaker(name = "guardarCategoriaCB", fallbackMethod = "fallbackGuardar")
    CategoriaDto guardar(@RequestBody CategoriaDto categoria);

    default CategoriaDto fallbackCategoria(Integer id, Exception e) {
        CategoriaDto CategoriaDto = new CategoriaDto();
        CategoriaDto.setId(9000000);
        CategoriaDto.setNombre("Servicio Categoria no Disponible");
        CategoriaDto.setDescripcion("Servicio Categoria no Disponible");
        return CategoriaDto;
    }
    default CategoriaDto fallbackGuardar(CategoriaDto categoria, Throwable e) {
        CategoriaDto fallback = new CategoriaDto();
        fallback.setId(9000001);
        fallback.setNombre("No se pudo guardar la categoría");
        fallback.setDescripcion("Servicio catalogo no disponible");
        return fallback;
    }

}
