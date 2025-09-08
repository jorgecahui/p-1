package com.example.ms_producto.dto;

import lombok.Data;

@Data
public class ProductoDto {
    private Integer id;
    private String nombre;
    private Double precio;
    private Integer stock;

    private Integer categoriaId;
    private CategoriaDto categoria;
}

