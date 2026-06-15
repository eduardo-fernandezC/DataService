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
import com.DataService.model.Region;
import com.DataService.service.CiudadService;

@ExtendWith(MockitoExtension.class)
class CiudadControllerTest {

    @Mock
    private CiudadService ciudadService;

    @InjectMocks
    private CiudadController ciudadController;

    @Test
    void findAll_retornaListaDeCiudades() {
        Ciudad ciudad = crearCiudad(1L, "Santiago", crearRegion(10L, "Metropolitana"));
        when(ciudadService.findAll()).thenReturn(List.of(ciudad));

        var response = ciudadController.findAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1L, response.getBody().get(0).getIdCiudad());

        verify(ciudadService).findAll();
    }

    @Test
    void findById_retornaCiudadPorId() {
        Ciudad ciudad = crearCiudad(1L, "Valparaiso", crearRegion(20L, "Valparaiso"));
        when(ciudadService.findById(1L)).thenReturn(ciudad);

        var response = ciudadController.findById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1L, response.getBody().getIdCiudad());

        verify(ciudadService).findById(1L);
    }

    @Test
    void update_actualizaCiudadYretornaLaActualizada() {
        Ciudad request = crearCiudad(null, "Temuco", crearRegion(40L, "Araucania"));
        Ciudad responseMock = crearCiudad(2L, "Temuco", crearRegion(40L, "Araucania"));
        when(ciudadService.update(eq(2L), any(Ciudad.class))).thenReturn(responseMock);

        var response = ciudadController.update(2L, request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2L, response.getBody().getIdCiudad());

        verify(ciudadService).update(eq(2L), any(Ciudad.class));
    }

    @Test
    void delete_eliminaCiudadYretornaNoContent() {
        var response = ciudadController.delete(3L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

        verify(ciudadService).delete(3L);
    }

    @Test
    void findByRegion_retornaCiudadesDeLaRegion() {
        Region region = crearRegion(50L, "Los Lagos");
        Ciudad ciudad = crearCiudad(7L, "Osorno", region);
        when(ciudadService.findByRegion(50L)).thenReturn(List.of(ciudad));

        var response = ciudadController.findByRegion(50L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(7L, response.getBody().get(0).getIdCiudad());

        verify(ciudadService).findByRegion(50L);
    }

    private Ciudad crearCiudad(Long idCiudad, String nombre, Region region) {
        Ciudad ciudad = new Ciudad();
        ciudad.setIdCiudad(idCiudad);
        ciudad.setNombre(nombre);
        ciudad.setRegion(region);
        return ciudad;
    }

    private Region crearRegion(Long idRegion, String nombre) {
        Region region = new Region();
        region.setIdRegion(idRegion);
        region.setNombre(nombre);
        return region;
    }
}