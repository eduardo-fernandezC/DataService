package com.DataService.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.DataService.model.Empleado;
import com.DataService.model.enums.Cargo;
import com.DataService.service.EmpleadoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/empleados")
public class EmpleadoController {

    @Autowired
    private EmpleadoService empleadoService;

    @GetMapping
    public ResponseEntity<List<Empleado>> listar() {
        List<Empleado> empleados = empleadoService.findAll();
        if (empleados.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(empleados);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Empleado> buscar(@PathVariable Long id) {
        Empleado empleado = empleadoService.findByIdEmpleado(id);
        if (empleado != null) {
            return ResponseEntity.ok(empleado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/buscarSucursal/{idSucursal}")
    public ResponseEntity<List<Empleado>> buscarPorSucursal(@PathVariable Long idSucursal) {
        List<Empleado> empleados = empleadoService.findBySucursalIdSucursal(idSucursal);
        if (empleados.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(empleados);
    }

    @GetMapping("/buscarCargo/{cargo}")
    public ResponseEntity<List<Empleado>> buscarPorCargo(@PathVariable Cargo cargo) {
        List<Empleado> empleados = empleadoService.findByCargo(cargo);
        if (empleados.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(empleados);
    }

    @PostMapping
    public ResponseEntity<Empleado> guardar(@Valid @RequestBody Empleado empleado) {
        Empleado guardado = empleadoService.save(empleado);
        return ResponseEntity.status(HttpStatus.CREATED).body(guardado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Empleado> actualizar(@PathVariable Long id, @Valid @RequestBody Empleado empleado) {
        Empleado actualizado = empleadoService.update(id, empleado);
        if (actualizado != null) {
            return ResponseEntity.ok(actualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Empleado> patchEmpleado(@PathVariable Long id, @Valid @RequestBody Empleado empleado) {
        Empleado actualizado = empleadoService.patch(id, empleado);
        if (actualizado != null) {
            return ResponseEntity.ok(actualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        try {
            empleadoService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
