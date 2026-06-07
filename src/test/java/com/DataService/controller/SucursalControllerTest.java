package com.DataService.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import com.DataService.model.Ciudad;
import com.DataService.model.Sucursal;
import com.DataService.service.SucursalService;

@ExtendWith(MockitoExtension.class)
class SucursalControllerTest {

    @Mock
    private SucursalService sucursalService;

    @InjectMocks
    private SucursalController sucursalController;

    @Test
    void listar_retornaListaDeSucursales() {
        Sucursal s = new Sucursal();
        s.setIdSucursal(10L);
        s.setNombre("Sucursal Central");
        Ciudad c = new Ciudad(); c.setIdCiudad(2L);
        s.setCiudad(c);

        when(sucursalService.findAll()).thenReturn(List.of(s));

        var response = sucursalController.listar();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(10L, response.getBody().get(0).getIdSucursal());

        verify(sucursalService).findAll();
    }

    @Test
    void buscar_retornaSucursalPorId() {
        Sucursal s = new Sucursal();
        s.setIdSucursal(11L);
        s.setNombre("Sucursal Norte");

        when(sucursalService.findByIdSucursal(11L)).thenReturn(s);

        var response = sucursalController.buscar(11L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(11L, response.getBody().getIdSucursal());

        verify(sucursalService).findByIdSucursal(11L);
    }

    @Test
    void buscarPorCiudad_retornaLista() {
        Sucursal s = new Sucursal();
        s.setIdSucursal(12L);
        Ciudad c = new Ciudad(); c.setIdCiudad(5L);
        s.setCiudad(c);

        when(sucursalService.findByCiudadIdCiudad(5L)).thenReturn(List.of(s));

        var response = sucursalController.buscarPorCiudad(5L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(12L, response.getBody().get(0).getIdSucursal());

        verify(sucursalService).findByCiudadIdCiudad(5L);
    }

    @Test
    void guardar_retornaCreated() {
        Sucursal req = new Sucursal();
        req.setNombre("Nueva 1");

        Sucursal saved = new Sucursal();
        saved.setIdSucursal(20L);
        saved.setNombre("Nueva 1");

        when(sucursalService.save(any(Sucursal.class))).thenReturn(saved);

        var response = sucursalController.guardar(req);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(20L, response.getBody().getIdSucursal());

        verify(sucursalService).save(any(Sucursal.class));
    }

    @Test
    void update_actualizaYretornaOk() {
        Sucursal req = new Sucursal();
        req.setNombre("Actualizada");

        Sucursal updated = new Sucursal();
        updated.setIdSucursal(21L);
        updated.setNombre("Actualizada");

        when(sucursalService.update(eq(21L), any(Sucursal.class))).thenReturn(updated);

        var response = sucursalController.actualizar(21L, req);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(21L, response.getBody().getIdSucursal());

        verify(sucursalService).update(eq(21L), any(Sucursal.class));
    }

    @Test
    void delete_eliminaYretornaNoContent() {
        var response = sucursalController.eliminar(22L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

        verify(sucursalService).delete(22L);
    }
}
