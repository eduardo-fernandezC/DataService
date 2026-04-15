package com.DataService.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.DataService.model.Empleado;
import com.DataService.model.enums.Cargo;
import com.DataService.repository.EmpleadoRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class EmpleadoService {

	@Autowired
	private EmpleadoRepository empleadoRepository;

	public List<Empleado> findAll() {
		return empleadoRepository.findAll();
	}

	public Empleado save(Empleado empleado) {
		return empleadoRepository.save(empleado);
	}

	public Empleado update(Long id, Empleado empleado) {
		Empleado empleadoToUpdate = empleadoRepository.findById(id).orElse(null);
		if (empleadoToUpdate != null) {
			empleadoToUpdate.setNombre(empleado.getNombre());
			empleadoToUpdate.setCargo(empleado.getCargo());
			empleadoToUpdate.setSucursal(empleado.getSucursal());
			return empleadoRepository.save(empleadoToUpdate);
		} else {
			return null;
		}
	}

	public Empleado patch(Long id, Empleado empleado) {
		Empleado empleadoToPatch = empleadoRepository.findById(id).orElse(null);
		if (empleadoToPatch != null) {
			if (empleado.getNombre() != null) {
				empleadoToPatch.setNombre(empleado.getNombre());
			}
			if (empleado.getCargo() != null) {
				empleadoToPatch.setCargo(empleado.getCargo());
			}
			if (empleado.getSucursal() != null) {
				empleadoToPatch.setSucursal(empleado.getSucursal());
			}
			return empleadoRepository.save(empleadoToPatch);
		} else {
			return null;
		}
	}

	public void delete(Long id) {
		empleadoRepository.deleteById(id);
	}

	public List<Empleado> findBySucursalIdSucursal(Long idSucursal) {
		return empleadoRepository.findBySucursalIdSucursal(idSucursal);
	}

	public List<Empleado> findByCargo(Cargo cargo) {
		return empleadoRepository.findByCargo(cargo);
	}

	public Empleado findByIdEmpleado(Long idEmpleado) {
		return empleadoRepository.findById(idEmpleado).orElse(null);
	}

}
