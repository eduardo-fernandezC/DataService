package com.DataService.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.DataService.model.DetalleVenta;
import com.DataService.repository.DetalleVentaRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class DetalleVentaService {

	@Autowired
	private DetalleVentaRepository detalleVentaRepository;

	public List<DetalleVenta> findAll() {
		return detalleVentaRepository.findAll();
	}

	public DetalleVenta save(DetalleVenta detalleVenta) {
		return detalleVentaRepository.save(detalleVenta);
	}

	public DetalleVenta update(Long id, DetalleVenta detalleVenta) {
		DetalleVenta detalleVentaToUpdate = detalleVentaRepository.findById(id).orElse(null);
		if (detalleVentaToUpdate != null) {
			detalleVentaToUpdate.setCantidad(detalleVenta.getCantidad());
			detalleVentaToUpdate.setSubtotal(detalleVenta.getSubtotal());
			detalleVentaToUpdate.setVenta(detalleVenta.getVenta());
			detalleVentaToUpdate.setProducto(detalleVenta.getProducto());
			return detalleVentaRepository.save(detalleVentaToUpdate);
		} else {
			return null;
		}
	}

	public DetalleVenta patch(Long id, DetalleVenta detalleVenta) {
		DetalleVenta detalleVentaToPatch = detalleVentaRepository.findById(id).orElse(null);
		if (detalleVentaToPatch != null) {
			if (detalleVenta.getCantidad() != null) {
				detalleVentaToPatch.setCantidad(detalleVenta.getCantidad());
			}
			if (detalleVenta.getSubtotal() != null) {
				detalleVentaToPatch.setSubtotal(detalleVenta.getSubtotal());
			}
			if (detalleVenta.getVenta() != null) {
				detalleVentaToPatch.setVenta(detalleVenta.getVenta());
			}
			if (detalleVenta.getProducto() != null) {
				detalleVentaToPatch.setProducto(detalleVenta.getProducto());
			}
			return detalleVentaRepository.save(detalleVentaToPatch);
		} else {
			return null;
		}
	}

	public void delete(Long id) {
		detalleVentaRepository.deleteById(id);
	}

	public List<DetalleVenta> findByVentaIdVenta(Long idVenta) {
		return detalleVentaRepository.findByVentaIdVenta(idVenta);
	}

	public DetalleVenta findByIdDetalle(Long idDetalle) {
		return detalleVentaRepository.findById(idDetalle).orElse(null);
	}

}
