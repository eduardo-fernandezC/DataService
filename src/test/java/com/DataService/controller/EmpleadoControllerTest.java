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

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(empleadoController).build();
    }

    @Test
    void listar_retornaListaDeEmpleados() throws Exception {
        Empleado e = new Empleado();
        e.setIdEmpleado(11L);
        e.setNombre("Ana");
        e.setCargo(Cargo.VENDEDOR);
        Sucursal s = new Sucursal(); s.setIdSucursal(5L);
        e.setSucursal(s);

        when(empleadoService.findAll()).thenReturn(List.of(e));

        mockMvc.perform(get("/api/v1/empleados"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idEmpleado").value(11L))
                .andExpect(jsonPath("$[0].nombre").value("Ana"));

        verify(empleadoService).findAll();
    }

    @Test
    void buscar_retornaEmpleadoPorId() throws Exception {
        Empleado e = new Empleado();
        e.setIdEmpleado(22L);
        e.setNombre("Luis");
        e.setCargo(Cargo.ADMIN);
        when(empleadoService.findByIdEmpleado(22L)).thenReturn(e);

        mockMvc.perform(get("/api/v1/empleados/{id}", 22L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idEmpleado").value(22L))
                .andExpect(jsonPath("$.nombre").value("Luis"));

        verify(empleadoService).findByIdEmpleado(22L);
    }
}
