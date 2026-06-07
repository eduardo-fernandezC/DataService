package com.DataService.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import com.DataService.model.Sucursal;
import com.DataService.model.Venta;
import com.DataService.service.VentaService;

@ExtendWith(MockitoExtension.class)
class VentaControllerTest {

    @Mock
    private VentaService ventaService;

    @InjectMocks
    private VentaController ventaController;

    @Test
    void listar_retornaListaDeVentas() {
        Venta v = new Venta();
        v.setIdVenta(1001L);
        v.setFecha(LocalDate.now());
        v.setTotal(150.0);

        when(ventaService.findAll()).thenReturn(List.of(v));

        var response = ventaController.listar();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1001L, response.getBody().get(0).getIdVenta());

        verify(ventaService).findAll();
    }

    @Test
    void buscar_retornaVentaPorId() {
        Venta v = new Venta();
        v.setIdVenta(2002L);
        v.setFecha(LocalDate.now());
        v.setTotal(250.0);

        when(ventaService.findByIdVenta(2002L)).thenReturn(v);

        var response = ventaController.buscar(2002L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2002L, response.getBody().getIdVenta());

        verify(ventaService).findByIdVenta(2002L);
    }

    @Test
    void guardar_retornaCreated() {
        Venta req = new Venta();
        req.setFecha(LocalDate.now());
        req.setTotal(99.0);
        Sucursal s = new Sucursal(); s.setIdSucursal(3L);
        req.setSucursal(s);

        Venta saved = new Venta();
        saved.setIdVenta(3003L);
        saved.setFecha(req.getFecha());
        saved.setTotal(99.0);

        when(ventaService.save(any(Venta.class))).thenReturn(saved);

        var response = ventaController.guardar(req);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(3003L, response.getBody().getIdVenta());

        verify(ventaService).save(any(Venta.class));
    }

    @Test
    void delete_eliminaYretornaNoContent() {
        var response = ventaController.eliminar(4004L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

        verify(ventaService).delete(4004L);
    }
}
