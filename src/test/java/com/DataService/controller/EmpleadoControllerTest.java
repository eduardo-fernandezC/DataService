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

import com.DataService.model.Empleado;
import com.DataService.model.Sucursal;
import com.DataService.model.enums.Cargo;
import com.DataService.service.EmpleadoService;

@ExtendWith(MockitoExtension.class)
class EmpleadoControllerTest {

    @Mock
    private EmpleadoService empleadoService;

    @InjectMocks
    private EmpleadoController empleadoController;

    @Test
    void listar_retornaListaDeEmpleados() {
        Empleado e = new Empleado();
        e.setIdEmpleado(11L);
        e.setNombre("Ana");
        e.setCargo(Cargo.VENDEDOR);
        Sucursal s = new Sucursal(); s.setIdSucursal(5L);
        e.setSucursal(s);

        when(empleadoService.findAll()).thenReturn(List.of(e));

        var response = empleadoController.listar();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(11L, response.getBody().get(0).getIdEmpleado());

        verify(empleadoService).findAll();
    }

    @Test
    void buscar_retornaEmpleadoPorId() {
        Empleado e = new Empleado();
        e.setIdEmpleado(22L);
        e.setNombre("Luis");
        e.setCargo(Cargo.ADMIN);
        when(empleadoService.findByIdEmpleado(22L)).thenReturn(e);

        var response = empleadoController.buscar(22L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(22L, response.getBody().getIdEmpleado());

        verify(empleadoService).findByIdEmpleado(22L);
    }
}
