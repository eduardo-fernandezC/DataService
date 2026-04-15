package com.DataService.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.DataService.model.Region;
import com.DataService.model.Sucursal;
import com.DataService.repository.SucursalRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class SucursalService {

	@Autowired
	private SucursalRepository sucursalRepository;

	public List<Sucursal> findAll() {
		return sucursalRepository.findAll();
	}

	public Sucursal save(Sucursal sucursal) {
		return sucursalRepository.save(sucursal);
	}

	public Sucursal update(Long id, Sucursal sucursal) {
		Sucursal sucursalToUpdate = sucursalRepository.findById(id).orElse(null);
		if (sucursalToUpdate != null) {
			sucursalToUpdate.setNombre(sucursal.getNombre());
			sucursalToUpdate.setRegion(sucursal.getRegion());
			return sucursalRepository.save(sucursalToUpdate);
		} else {
			return null;
		}
	}

	public Sucursal patch(Long id, Sucursal sucursal) {
		Sucursal sucursalToPatch = sucursalRepository.findById(id).orElse(null);
		if (sucursalToPatch != null) {
			if (sucursal.getNombre() != null) {
				sucursalToPatch.setNombre(sucursal.getNombre());
			}
			if (sucursal.getRegion() != null) {
				sucursalToPatch.setRegion(sucursal.getRegion());
			}
			return sucursalRepository.save(sucursalToPatch);
		} else {
			return null;
		}
	}

	public void delete(Long id) {
		sucursalRepository.deleteById(id);
	}

	public List<Sucursal> findByRegion(Region region) {
		return sucursalRepository.findByRegion(region);
	}

	public List<Sucursal> findByRegionIdRegion(Long idRegion) {
		return sucursalRepository.findByRegionIdRegion(idRegion);
	}

	public Sucursal findByIdSucursal(Long idSucursal) {
		return sucursalRepository.findById(idSucursal).orElse(null);
	}

}
