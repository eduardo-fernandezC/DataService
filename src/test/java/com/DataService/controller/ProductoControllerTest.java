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

import com.DataService.model.Producto;
import com.DataService.service.ProductoService;

@ExtendWith(MockitoExtension.class)
class ProductoControllerTest {

    @Mock
    private ProductoService productoService;

    @InjectMocks
    private ProductoController productoController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(productoController).build();
    }

    @Test
    void listar_retornaListaDeProductos() throws Exception {
        Producto p = new Producto();
        p.setIdProducto(101L);
        p.setNombre("Zapatillas");
        p.setCategoria("Calzado");

        when(productoService.findAll()).thenReturn(List.of(p));

        mockMvc.perform(get("/api/v1/productos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idProducto").value(101L))
                .andExpect(jsonPath("$[0].nombre").value("Zapatillas"));

        verify(productoService).findAll();
    }

    @Test
    void buscar_retornaProductoPorId() throws Exception {
        Producto p = new Producto();
        p.setIdProducto(202L);
        p.setNombre("Remera");
        when(productoService.findByIdProducto(202L)).thenReturn(p);

        mockMvc.perform(get("/api/v1/productos/{id}", 202L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idProducto").value(202L))
                .andExpect(jsonPath("$.nombre").value("Remera"));

        verify(productoService).findByIdProducto(202L);
    }

    @Test
    void buscarPorCategoria_retornaLista() throws Exception {
        Producto p = new Producto();
        p.setIdProducto(303L);
        p.setNombre("Botas");
        p.setCategoria("Calzado");

        when(productoService.findByCategoria("Calzado")).thenReturn(List.of(p));

        mockMvc.perform(get("/api/v1/productos/buscarCategoria/{categoria}", "Calzado"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idProducto").value(303L))
                .andExpect(jsonPath("$[0].categoria").value("Calzado"));

        verify(productoService).findByCategoria("Calzado");
    }
}
