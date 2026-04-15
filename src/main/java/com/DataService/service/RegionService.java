package com.DataService.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.DataService.model.Region;
import com.DataService.repository.RegionRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class RegionService {

	@Autowired
	private RegionRepository regionRepository;

	public List<Region> findAll() {
		return regionRepository.findAll();
	}

	public Region save(Region region) {
		return regionRepository.save(region);
	}

	public Region update(Long id, Region region) {
		Region regionToUpdate = regionRepository.findById(id).orElse(null);
		if (regionToUpdate != null) {
			regionToUpdate.setNombre(region.getNombre());
			return regionRepository.save(regionToUpdate);
		} else {
			return null;
		}
	}

	public Region patch(Long id, Region region) {
		Region regionToPatch = regionRepository.findById(id).orElse(null);
		if (regionToPatch != null) {
			if (region.getNombre() != null) {
				regionToPatch.setNombre(region.getNombre());
			}
			return regionRepository.save(regionToPatch);
		} else {
			return null;
		}
	}

	public void delete(Long id) {
		regionRepository.deleteById(id);
	}

	public Region findByNombre(String nombre) {
		return regionRepository.findByNombre(nombre).orElse(null);
	}

	public Region findByIdRegion(Long idRegion) {
		return regionRepository.findById(idRegion).orElse(null);
	}

}
