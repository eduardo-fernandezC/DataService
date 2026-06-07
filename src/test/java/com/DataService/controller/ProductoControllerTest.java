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

import com.DataService.model.Producto;
import com.DataService.service.ProductoService;

@ExtendWith(MockitoExtension.class)
class ProductoControllerTest {

    @Mock
    private ProductoService productoService;

    @InjectMocks
    private ProductoController productoController;

    @Test
    void listar_retornaListaDeProductos() {
        Producto p = new Producto();
        p.setIdProducto(101L);
        p.setNombre("Zapatillas");
        p.setCategoria("Calzado");

        when(productoService.findAll()).thenReturn(List.of(p));

        var response = productoController.listar();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(101L, response.getBody().get(0).getIdProducto());

        verify(productoService).findAll();
    }

    @Test
    void buscar_retornaProductoPorId() {
        Producto p = new Producto();
        p.setIdProducto(202L);
        p.setNombre("Remera");
        when(productoService.findByIdProducto(202L)).thenReturn(p);

        var response = productoController.buscar(202L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(202L, response.getBody().getIdProducto());

        verify(productoService).findByIdProducto(202L);
    }

    @Test
    void buscarPorCategoria_retornaLista() {
        Producto p = new Producto();
        p.setIdProducto(303L);
        p.setNombre("Botas");
        p.setCategoria("Calzado");

        when(productoService.findByCategoria("Calzado")).thenReturn(List.of(p));

        var response = productoController.buscarPorCategoria("Calzado");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(303L, response.getBody().get(0).getIdProducto());

        verify(productoService).findByCategoria("Calzado");
    }
}
