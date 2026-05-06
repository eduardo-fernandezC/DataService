package com.DataService.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DetalleCompraResponse {

    private Long idDetalle;
    private Long idProducto;
    private String nombreProducto;
    private String categoria;
    private Double precio;
    private Integer cantidad;
    private Double subtotal;
}