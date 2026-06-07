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

import com.DataService.model.Region;
import com.DataService.service.RegionService;

@ExtendWith(MockitoExtension.class)
class RegionControllerTest {

    @Mock
    private RegionService regionService;

    @InjectMocks
    private RegionController regionController;

    @Test
    void listar_retornaListaDeRegiones() {
        Region r = new Region();
        r.setIdRegion(1L);
        r.setNombre("Norte");

        when(regionService.findAll()).thenReturn(List.of(r));

        var response = regionController.listar();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1L, response.getBody().get(0).getIdRegion());

        verify(regionService).findAll();
    }

    @Test
    void buscar_retornaRegionPorId() {
        Region r = new Region();
        r.setIdRegion(2L);
        r.setNombre("Centro");

        when(regionService.findByIdRegion(2L)).thenReturn(r);

        var response = regionController.buscar(2L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2L, response.getBody().getIdRegion());

        verify(regionService).findByIdRegion(2L);
    }

    @Test
    void guardar_retornaCreated() {
        Region request = new Region();
        request.setNombre("Sur");

        Region saved = new Region();
        saved.setIdRegion(5L);
        saved.setNombre("Sur");

        when(regionService.save(any(Region.class))).thenReturn(saved);

        var response = regionController.guardar(request);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(5L, response.getBody().getIdRegion());

        verify(regionService).save(any(Region.class));
    }

    @Test
    void update_actualizaYretornaOk() {
        Region request = new Region();
        request.setNombre("Norte Grande");

        Region updated = new Region();
        updated.setIdRegion(3L);
        updated.setNombre("Norte Grande");

        when(regionService.update(eq(3L), any(Region.class))).thenReturn(updated);

        var response = regionController.actualizar(3L, request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(3L, response.getBody().getIdRegion());

        verify(regionService).update(eq(3L), any(Region.class));
    }

    @Test
    void delete_eliminaYretornaNoContent() {
        var response = regionController.eliminar(4L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

        verify(regionService).delete(4L);
    }
}
