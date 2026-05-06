package com.DataService.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CompraResponse {

    private Long idVenta;
    private LocalDate fecha;
    private Double total;
    private List<DetalleCompraResponse> productos;
}