package com.DataService.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.DataService.model.Ciudad;
import com.DataService.model.Region;
import com.DataService.repository.CiudadRepository;
import com.DataService.repository.RegionRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class CiudadService {

    @Autowired
    private CiudadRepository ciudadRepository;

    @Autowired
    private RegionRepository regionRepository;

    public List<Ciudad> findAll() {
        return ciudadRepository.findAll();
    }

    public Ciudad findById(Long id) {
        return ciudadRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ciudad no encontrada"));
    }

    public Ciudad save(Ciudad ciudad) {
        return ciudadRepository.save(ciudad);
    }

    public Ciudad update(Long id, Ciudad ciudad) {
        Ciudad ciudadExistente = ciudadRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ciudad no encontrada"));

        ciudadExistente.setNombre(ciudad.getNombre());
        ciudadExistente.setRegion(ciudad.getRegion());

        return ciudadRepository.save(ciudadExistente);
    }

    public void delete(Long id) {
        ciudadRepository.deleteById(id);
    }

    public List<Ciudad> findByRegion(Long idRegion) {
        Region region = regionRepository.findById(idRegion)
                .orElseThrow(() -> new RuntimeException("Región no encontrada"));

        return ciudadRepository.findByRegion(region);
    }
    
}
