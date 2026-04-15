package com.DataService.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.DataService.model.Producto;
import com.DataService.repository.ProductoRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ProductoService {

	@Autowired
	private ProductoRepository productoRepository;

	public List<Producto> findAll() {
		return productoRepository.findAll();
	}

	public Producto save(Producto producto) {
		return productoRepository.save(producto);
	}

	public Producto update(Long id, Producto producto) {
		Producto productoToUpdate = productoRepository.findById(id).orElse(null);
		if (productoToUpdate != null) {
			productoToUpdate.setNombre(producto.getNombre());
			productoToUpdate.setCategoria(producto.getCategoria());
			productoToUpdate.setPrecio(producto.getPrecio());
			productoToUpdate.setStock(producto.getStock());
			return productoRepository.save(productoToUpdate);
		} else {
			return null;
		}
	}

	public Producto patch(Long id, Producto producto) {
		Producto productoToPatch = productoRepository.findById(id).orElse(null);
		if (productoToPatch != null) {
			if (producto.getNombre() != null) {
				productoToPatch.setNombre(producto.getNombre());
			}
			if (producto.getCategoria() != null) {
				productoToPatch.setCategoria(producto.getCategoria());
			}
			if (producto.getPrecio() != null) {
				productoToPatch.setPrecio(producto.getPrecio());
			}
			if (producto.getStock() != null) {
				productoToPatch.setStock(producto.getStock());
			}
			return productoRepository.save(productoToPatch);
		} else {
			return null;
		}
	}

	public void delete(Long id) {
		productoRepository.deleteById(id);
	}

	public List<Producto> findByCategoria(String categoria) {
		return productoRepository.findByCategoria(categoria);
	}

	public Producto findByIdProducto(Long idProducto) {
		return productoRepository.findById(idProducto).orElse(null);
	}

}
