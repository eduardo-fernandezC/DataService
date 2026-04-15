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

    @Scheduled(initialDelay = 5000, fixedRate = 30000)
    public void generarVentaAutomatica() {

        List<Producto> productos = productoRepository.findAll();
        List<Empleado> empleados = empleadoRepository.findAll();
        List<Sucursal> sucursales = sucursalRepository.findAll();

        if (productos.isEmpty() || empleados.isEmpty() || sucursales.isEmpty()) {
            return;
        }

        Empleado empleado = empleados.get(random.nextInt(empleados.size()));
        Sucursal sucursal = sucursales.get(random.nextInt(sucursales.size()));

        Venta venta = new Venta();
        venta.setFecha(LocalDate.now());
        venta.setEmpleado(empleado);
        venta.setSucursal(sucursal);
        venta.setTotal(0.0);
        venta.setDetalles(new ArrayList<>());

        venta = ventaRepository.save(venta);

        int cantidadProductos = random.nextInt(3) + 1;
        List<DetalleVenta> detalles = new ArrayList<>();

        double total = 0.0;

        for (int i = 0; i < cantidadProductos; i++) {

            Producto producto = productos.get(random.nextInt(productos.size()));
            int cantidad = random.nextInt(5) + 1;

            double subtotal = producto.getPrecio() * cantidad;

            DetalleVenta detalle = new DetalleVenta();
            detalle.setVenta(venta);
            detalle.setProducto(producto);
            detalle.setCantidad(cantidad);
            detalle.setSubtotal(subtotal);

            detalles.add(detalle);
            total += subtotal;
        }

        detalleVentaRepository.saveAll(detalles);

        venta.setDetalles(detalles);
        venta.setTotal(total);
        ventaRepository.save(venta);

        System.out.println("Venta generada automaticamente");
    }
}
