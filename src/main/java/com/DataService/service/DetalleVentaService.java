package com.DataService.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.DataService.dto.CompraResponse;
import com.DataService.dto.DetalleCompraResponse;
import com.DataService.model.DetalleVenta;
import com.DataService.model.Venta;
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

	public List<DetalleCompraResponse> findDetalleCompraByVentaIdVenta(Long idVenta) {
		return detalleVentaRepository.findByVentaIdVenta(idVenta)
				.stream()
				.map(detalle -> {
					DetalleCompraResponse response = new DetalleCompraResponse();
					response.setIdDetalle(detalle.getIdDetalle());
					response.setIdProducto(detalle.getProducto().getIdProducto());
					response.setNombreProducto(detalle.getProducto().getNombre());
					response.setCategoria(detalle.getProducto().getCategoria());
					response.setPrecio(detalle.getProducto().getPrecio());
					response.setCantidad(detalle.getCantidad());
					response.setSubtotal(detalle.getSubtotal());
					return response;
				})
				.toList();
	}

	public CompraResponse findCompraByVentaIdVenta(Long idVenta) {
		List<DetalleCompraResponse> productos = findDetalleCompraByVentaIdVenta(idVenta);
		Venta venta = detalleVentaRepository.findByVentaIdVenta(idVenta)
				.stream()
				.findFirst()
				.map(DetalleVenta::getVenta)
				.orElse(null);

		if (venta == null) {
			return null;
		}

		CompraResponse response = new CompraResponse();
		response.setIdVenta(venta.getIdVenta());
		response.setFecha(venta.getFecha());
		response.setTotal(venta.getTotal());
		response.setProductos(productos);
		return response;
	}

	public DetalleVenta findByIdDetalle(Long idDetalle) {
		return detalleVentaRepository.findById(idDetalle).orElse(null);
	}

}
