package com.DataService.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.DataService.model.Venta;
import com.DataService.repository.VentaRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class VentaService {

	@Autowired
	private VentaRepository ventaRepository;

	public List<Venta> findAll() {
		return ventaRepository.findAll();
	}

	public Venta save(Venta venta) {
		return ventaRepository.save(venta);
	}

	public Venta update(Long id, Venta venta) {
		Venta ventaToUpdate = ventaRepository.findById(id).orElse(null);
		if (ventaToUpdate != null) {
			ventaToUpdate.setFecha(venta.getFecha());
			ventaToUpdate.setTotal(venta.getTotal());
			ventaToUpdate.setSucursal(venta.getSucursal());
			ventaToUpdate.setEmpleado(venta.getEmpleado());
			return ventaRepository.save(ventaToUpdate);
		} else {
			return null;
		}
	}

	public Venta patch(Long id, Venta venta) {
		Venta ventaToPatch = ventaRepository.findById(id).orElse(null);
		if (ventaToPatch != null) {
			if (venta.getFecha() != null) {
				ventaToPatch.setFecha(venta.getFecha());
			}
			if (venta.getTotal() != null) {
				ventaToPatch.setTotal(venta.getTotal());
			}
			if (venta.getSucursal() != null) {
				ventaToPatch.setSucursal(venta.getSucursal());
			}
			if (venta.getEmpleado() != null) {
				ventaToPatch.setEmpleado(venta.getEmpleado());
			}
			return ventaRepository.save(ventaToPatch);
		} else {
			return null;
		}
	}

	public void delete(Long id) {
		ventaRepository.deleteById(id);
	}

	public List<Venta> findByFecha(LocalDate fecha) {
		return ventaRepository.findByFecha(fecha);
	}

	public List<Venta> findBySucursalIdSucursal(Long idSucursal) {
		return ventaRepository.findBySucursalIdSucursal(idSucursal);
	}

	public List<Venta> findByFechaBetween(LocalDate inicio, LocalDate fin) {
		return ventaRepository.findByFechaBetween(inicio, fin);
	}

	public Venta findByIdVenta(Long idVenta) {
		return ventaRepository.findById(idVenta).orElse(null);
	}

}
