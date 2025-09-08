package com.example.ms_producto.feign;

import com.example.ms_producto.dto.CategoriaDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-catalogo", path = "/categoria")
public interface CatalogoFeign {
    @GetMapping("/{id}")
    CategoriaDto buscarPorId(@PathVariable("id") Integer id);
}
