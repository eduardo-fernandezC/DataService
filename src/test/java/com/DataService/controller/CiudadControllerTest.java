package com.DataService.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.DataService.model.Ciudad;
import com.DataService.model.Region;
import com.DataService.service.CiudadService;

@ExtendWith(MockitoExtension.class)
class CiudadControllerTest {

    @Mock
    private CiudadService ciudadService;

    @InjectMocks
    private CiudadController ciudadController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(ciudadController).build();
    }

    @Test
    void findAll_retornaListaDeCiudades() throws Exception {
        Ciudad ciudad = crearCiudad(1L, "Santiago", crearRegion(10L, "Metropolitana"));
        when(ciudadService.findAll()).thenReturn(List.of(ciudad));

        mockMvc.perform(get("/api/v1/ciudades"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idCiudad").value(1L))
                .andExpect(jsonPath("$[0].nombre").value("Santiago"))
                .andExpect(jsonPath("$[0].region.idRegion").value(10L))
                .andExpect(jsonPath("$[0].region.nombre").value("Metropolitana"));

        verify(ciudadService).findAll();
    }

    @Test
    void findById_retornaCiudadPorId() throws Exception {
        Ciudad ciudad = crearCiudad(1L, "Valparaiso", crearRegion(20L, "Valparaiso"));
        when(ciudadService.findById(1L)).thenReturn(ciudad);

        mockMvc.perform(get("/api/v1/ciudades/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idCiudad").value(1L))
                .andExpect(jsonPath("$.nombre").value("Valparaiso"))
                .andExpect(jsonPath("$.region.idRegion").value(20L))
                .andExpect(jsonPath("$.region.nombre").value("Valparaiso"));

        verify(ciudadService).findById(1L);
    }

    @Test
    void save_guardaCiudadYretornaLaCreada() throws Exception {
        Ciudad request = crearCiudad(null, "Concepcion", crearRegion(30L, "Biobio"));
        Ciudad response = crearCiudad(5L, "Concepcion", crearRegion(30L, "Biobio"));
        when(ciudadService.save(any(Ciudad.class))).thenReturn(response);

        mockMvc.perform(post("/api/v1/ciudades")
                        .contentType(MediaType.APPLICATION_JSON)
                .content(asJson(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idCiudad").value(5L))
                .andExpect(jsonPath("$.nombre").value("Concepcion"))
                .andExpect(jsonPath("$.region.idRegion").value(30L))
                .andExpect(jsonPath("$.region.nombre").value("Biobio"));

        verify(ciudadService).save(any(Ciudad.class));
    }

    @Test
    void update_actualizaCiudadYretornaLaActualizada() throws Exception {
        Ciudad request = crearCiudad(null, "Temuco", crearRegion(40L, "Araucania"));
        Ciudad response = crearCiudad(2L, "Temuco", crearRegion(40L, "Araucania"));
        when(ciudadService.update(eq(2L), any(Ciudad.class))).thenReturn(response);

        mockMvc.perform(put("/api/v1/ciudades/{id}", 2L)
                        .contentType(MediaType.APPLICATION_JSON)
                .content(asJson(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idCiudad").value(2L))
                .andExpect(jsonPath("$.nombre").value("Temuco"))
                .andExpect(jsonPath("$.region.idRegion").value(40L))
                .andExpect(jsonPath("$.region.nombre").value("Araucania"));

        verify(ciudadService).update(eq(2L), any(Ciudad.class));
    }

    @Test
    void delete_eliminaCiudadYretornaNoContent() throws Exception {
        mockMvc.perform(delete("/api/v1/ciudades/{id}", 3L))
                .andExpect(status().isNoContent())
                .andExpect(content().string(""));

        verify(ciudadService).delete(3L);
    }

    @Test
    void findByRegion_retornaCiudadesDeLaRegion() throws Exception {
        Region region = crearRegion(50L, "Los Lagos");
        Ciudad ciudad = crearCiudad(7L, "Osorno", region);
        when(ciudadService.findByRegion(50L)).thenReturn(List.of(ciudad));

        mockMvc.perform(get("/api/v1/ciudades/region/{idRegion}", 50L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idCiudad").value(7L))
                .andExpect(jsonPath("$[0].nombre").value("Osorno"))
                .andExpect(jsonPath("$[0].region.idRegion").value(50L))
                .andExpect(jsonPath("$[0].region.nombre").value("Los Lagos"));

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

    private String asJson(Ciudad ciudad) {
        return "{" +
                "\"idCiudad\":" + (ciudad.getIdCiudad() == null ? "null" : ciudad.getIdCiudad()) + "," +
                "\"nombre\":\"" + ciudad.getNombre() + "\"," +
                "\"region\":{" +
                "\"idRegion\":" + ciudad.getRegion().getIdRegion() + "," +
                "\"nombre\":\"" + ciudad.getRegion().getNombre() + "\"}" +
                "}";
    }
}