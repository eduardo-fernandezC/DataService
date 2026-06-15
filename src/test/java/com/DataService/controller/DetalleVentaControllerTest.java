package com.DataService.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import com.DataService.model.DetalleVenta;
import com.DataService.service.DetalleVentaService;

@ExtendWith(MockitoExtension.class)
class DetalleVentaControllerTest {

    @Mock
    private DetalleVentaService detalleVentaService;

    @InjectMocks
    private DetalleVentaController detalleVentaController;

    @Test
    void listar_retornaListaDeDetalles() {
        DetalleVenta d = new DetalleVenta();
        d.setIdDetalle(801L);
        d.setCantidad(2);
        d.setSubtotal(20.0);
        when(detalleVentaService.findAll()).thenReturn(List.of(d));

        var response = detalleVentaController.listar();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(801L, response.getBody().get(0).getIdDetalle());

        verify(detalleVentaService).findAll();
    }

    @Test
    void buscar_retornaDetallePorId() {
        DetalleVenta d = new DetalleVenta();
        d.setIdDetalle(802L);
        d.setCantidad(1);
        d.setSubtotal(10.0);
        when(detalleVentaService.findByIdDetalle(802L)).thenReturn(d);

        var response = detalleVentaController.buscar(802L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(802L, response.getBody().getIdDetalle());

        verify(detalleVentaService).findByIdDetalle(802L);
    }
}
