package com.DataService.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.DataService.model.DetalleVenta;
import com.DataService.model.Empleado;
import com.DataService.model.Producto;
import com.DataService.model.Sucursal;
import com.DataService.model.Venta;
import com.DataService.repository.DetalleVentaRepository;
import com.DataService.repository.EmpleadoRepository;
import com.DataService.repository.ProductoRepository;
import com.DataService.repository.SucursalRepository;
import com.DataService.repository.VentaRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@Component
public class VentaScheduler {

    @Autowired
    private VentaRepository ventaRepository;

    @Autowired
    private DetalleVentaRepository detalleVentaRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private EmpleadoRepository empleadoRepository;

    @Autowired
    private SucursalRepository sucursalRepository;

    private final Random random = new Random();

    @Scheduled(initialDelay = 5000, fixedRate = 14400000)
    public void generarVentaAutomatica() {

        List<Producto> productos = productoRepository.findAll();
        List<Empleado> empleados = empleadoRepository.findAll();
        List<Sucursal> sucursales = sucursalRepository.findAll();

        if (productos.isEmpty() || empleados.isEmpty() || sucursales.isEmpty()) {
            return;
        }

        List<Producto> productosConStock = new ArrayList<>();
        for (Producto producto : productos) {
            if (producto.getStock() != null && producto.getStock() > 0) {
                productosConStock.add(producto);
            }
        }

        if (productosConStock.isEmpty()) {
            System.out.println("Sin stock disponible para generar venta");
            return;
        }

        Empleado empleado = empleados.get(random.nextInt(empleados.size()));
        Sucursal sucursal = empleado.getSucursal();

        Venta venta = new Venta();
        venta.setFecha(LocalDate.now());
        venta.setEmpleado(empleado);
        venta.setSucursal(sucursal);
        venta.setTotal(0.0);
        venta.setDetalles(new ArrayList<>());

        venta = ventaRepository.save(venta);

        Collections.shuffle(productosConStock, random);
        int cantidadProductos = random.nextInt(Math.min(3, productosConStock.size())) + 1;
        List<DetalleVenta> detalles = new ArrayList<>();
        List<Producto> productosActualizados = new ArrayList<>();

        double total = 0.0;

        for (int i = 0; i < cantidadProductos; i++) {

            Producto producto = productosConStock.get(i);
            int maxCantidadVendible = Math.min(5, producto.getStock());
            int cantidad = random.nextInt(maxCantidadVendible) + 1;

            double subtotal = producto.getPrecio() * cantidad;

            DetalleVenta detalle = new DetalleVenta();
            detalle.setVenta(venta);
            detalle.setProducto(producto);
            detalle.setCantidad(cantidad);
            detalle.setSubtotal(subtotal);

            detalles.add(detalle);
            total += subtotal;

            producto.setStock(producto.getStock() - cantidad);
            productosActualizados.add(producto);
        }

        productoRepository.saveAll(productosActualizados);
        detalleVentaRepository.saveAll(detalles);

        venta.setDetalles(detalles);
        venta.setTotal(total);
        ventaRepository.save(venta);

        System.out.println("Venta generada automaticamente");
    }
}
