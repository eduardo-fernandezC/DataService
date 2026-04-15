package com.DataService.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.DataService.model.Ciudad;
import com.DataService.service.CiudadService;

@RestController
@RequestMapping("/api/v1/ciudades")
public class CiudadController {
    
    @Autowired
    private CiudadService ciudadService;

     @GetMapping
    public ResponseEntity<List<Ciudad>> findAll() {
        return ResponseEntity.ok(ciudadService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ciudad> findById(@PathVariable Long id) {
        return ResponseEntity.ok(ciudadService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Ciudad> save(@RequestBody Ciudad ciudad) {
        return ResponseEntity.ok(ciudadService.save(ciudad));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Ciudad> update(@PathVariable Long id, @RequestBody Ciudad ciudad) {
        return ResponseEntity.ok(ciudadService.update(id, ciudad));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        ciudadService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/region/{idRegion}")
    public ResponseEntity<List<Ciudad>> findByRegion(@PathVariable Long idRegion) {
        return ResponseEntity.ok(ciudadService.findByRegion(idRegion));
    }
}
