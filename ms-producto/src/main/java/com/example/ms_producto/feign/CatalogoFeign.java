package com.example.ms_producto.feign;

import com.example.ms_producto.dto.CategoriaDto;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-catalogo", path = "/categoria")
public interface CatalogoFeign {
    @GetMapping("/{id}")
    @CircuitBreaker(name = "categoriaListarPorIdCB", fallbackMethod = "fallbackCategoria")
    CategoriaDto buscarPorId(@PathVariable("id") Integer id);

    default CategoriaDto fallbackCategoria(Integer id, Exception e) {
        CategoriaDto CategoriaDto = new CategoriaDto();
        CategoriaDto.setId(9000000);
        CategoriaDto.setNombre("Servicio Categoria no Disponible");
        return CategoriaDto;
    }

}
