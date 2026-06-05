package com.DataService.controller;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.DataService.model.DetalleVenta;
import com.DataService.service.DetalleVentaService;

@ExtendWith(MockitoExtension.class)
class DetalleVentaControllerTest {

    @Mock
    private DetalleVentaService detalleVentaService;

    @InjectMocks
    private DetalleVentaController detalleVentaController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(detalleVentaController).build();
    }

    @Test
    void listar_retornaListaDeDetalles() throws Exception {
        DetalleVenta d = new DetalleVenta();
        d.setIdDetalle(801L);
        d.setCantidad(2);
        d.setSubtotal(20.0);
        when(detalleVentaService.findAll()).thenReturn(List.of(d));

        mockMvc.perform(get("/api/v1/detalle-ventas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idDetalle").value(801L))
                .andExpect(jsonPath("$[0].cantidad").value(2));

        verify(detalleVentaService).findAll();
    }

    @Test
    void buscar_retornaDetallePorId() throws Exception {
        DetalleVenta d = new DetalleVenta();
        d.setIdDetalle(802L);
        d.setCantidad(1);
        d.setSubtotal(10.0);
        when(detalleVentaService.findByIdDetalle(802L)).thenReturn(d);

        mockMvc.perform(get("/api/v1/detalle-ventas/{id}", 802L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idDetalle").value(802L))
                .andExpect(jsonPath("$.cantidad").value(1));

        verify(detalleVentaService).findByIdDetalle(802L);
    }
}
