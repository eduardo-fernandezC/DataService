package com.DataService.scheduler;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.DataService.model.Ciudad;
import com.DataService.model.Empleado;
import com.DataService.model.Producto;
import com.DataService.model.Region;
import com.DataService.model.Sucursal;
import com.DataService.model.enums.Cargo;
import com.DataService.repository.CiudadRepository;
import com.DataService.repository.EmpleadoRepository;
import com.DataService.repository.ProductoRepository;
import com.DataService.repository.RegionRepository;
import com.DataService.repository.SucursalRepository;

@Component
public class DataSeedRunner implements CommandLineRunner {

    private final RegionRepository regionRepository;
    private final CiudadRepository ciudadRepository;
    private final SucursalRepository sucursalRepository;
    private final EmpleadoRepository empleadoRepository;
    private final ProductoRepository productoRepository;

    public DataSeedRunner(
            RegionRepository regionRepository,
            CiudadRepository ciudadRepository,
            SucursalRepository sucursalRepository,
            EmpleadoRepository empleadoRepository,
            ProductoRepository productoRepository) {
        this.regionRepository = regionRepository;
        this.ciudadRepository = ciudadRepository;
        this.sucursalRepository = sucursalRepository;
        this.empleadoRepository = empleadoRepository;
        this.productoRepository = productoRepository;
    }

    @Override
    public void run(String... args) {
        crearRegionesSiNoExisten();
        crearCiudadesSiNoExisten();
        crearSucursalesSiNoExisten();
        crearEmpleadosSiNoExisten();
        crearProductosSiNoExisten();
    }

    private void crearRegionesSiNoExisten() {
        if (regionRepository.count() > 0) {
            return;
        }

        List<Region> regiones = new ArrayList<>();

        Region regionCentro = new Region();
        regionCentro.setNombre("Region Centro");

        Region regionNorte = new Region();
        regionNorte.setNombre("Region Norte");

        regiones.add(regionCentro);
        regiones.add(regionNorte);

        regionRepository.saveAll(regiones);
        System.out.println("Regiones base creadas");
    }

    private void crearCiudadesSiNoExisten() {
        if (ciudadRepository.count() > 0) {
            return;
        }

        List<Region> regiones = regionRepository.findAll();
        if (regiones.isEmpty()) {
            return;
        }

        Region regionCentro = regionRepository.findByNombre("Region Centro").orElse(regiones.get(0));
        Region regionNorte = regionRepository.findByNombre("Region Norte").orElse(regiones.get(0));

        List<Ciudad> ciudades = new ArrayList<>();

        Ciudad ciudadSantiago = new Ciudad();
        ciudadSantiago.setNombre("Santiago");
        ciudadSantiago.setRegion(regionCentro);

        Ciudad ciudadValparaiso = new Ciudad();
        ciudadValparaiso.setNombre("Valparaiso");
        ciudadValparaiso.setRegion(regionCentro);

        Ciudad ciudadLaSerena = new Ciudad();
        ciudadLaSerena.setNombre("La Serena");
        ciudadLaSerena.setRegion(regionNorte);

        ciudades.add(ciudadSantiago);
        ciudades.add(ciudadValparaiso);
        ciudades.add(ciudadLaSerena);

        ciudadRepository.saveAll(ciudades);
        System.out.println("Ciudades base creadas");
    }

    private void crearSucursalesSiNoExisten() {
        if (sucursalRepository.count() > 0) {
            return;
        }

        List<Ciudad> ciudades = ciudadRepository.findAll();
        if (ciudades.isEmpty()) {
            return;
        }

        List<Sucursal> sucursales = new ArrayList<>();

        Sucursal sucursalCentro = new Sucursal();
        sucursalCentro.setNombre("Sucursal Centro");
        sucursalCentro.setCiudad(ciudades.get(0));

        Sucursal sucursalCostanera = new Sucursal();
        sucursalCostanera.setNombre("Sucursal Costanera");
        sucursalCostanera.setCiudad(ciudades.get(ciudades.size() > 1 ? 1 : 0));

        Sucursal sucursalNorte = new Sucursal();
        sucursalNorte.setNombre("Sucursal Norte");
        sucursalNorte.setCiudad(ciudades.get(ciudades.size() > 2 ? 2 : 0));

        sucursales.add(sucursalCentro);
        sucursales.add(sucursalCostanera);
        sucursales.add(sucursalNorte);

        sucursalRepository.saveAll(sucursales);
        System.out.println("Sucursales base creadas");
    }

    private void crearEmpleadosSiNoExisten() {
        if (empleadoRepository.count() > 0) {
            return;
        }

        List<Sucursal> sucursales = sucursalRepository.findAll();
        if (sucursales.isEmpty()) {
            return;
        }

        List<Empleado> empleados = new ArrayList<>();

        empleados.add(crearEmpleado("Ana Rojas", Cargo.ADMIN, sucursales.get(0)));
        empleados.add(crearEmpleado("Pedro Soto", Cargo.SUPERVISOR, sucursales.get(sucursales.size() > 1 ? 1 : 0)));
        empleados.add(crearEmpleado("Maria Lopez", Cargo.VENDEDOR, sucursales.get(0)));
        empleados.add(crearEmpleado("Javier Diaz", Cargo.VENDEDOR, sucursales.get(sucursales.size() > 1 ? 1 : 0)));
        empleados.add(crearEmpleado("Camila Perez", Cargo.VENDEDOR, sucursales.get(sucursales.size() > 2 ? 2 : 0)));

        empleadoRepository.saveAll(empleados);
        System.out.println("Empleados base creados");
    }

    private void crearProductosSiNoExisten() {
        if (productoRepository.count() > 0) {
            return;
        }

        List<Producto> productos = new ArrayList<>();
        productos.add(crearProducto("Notebook 14", "Tecnologia", 599990.0));
        productos.add(crearProducto("Mouse Inalambrico", "Tecnologia", 19990.0));
        productos.add(crearProducto("Silla Ergonomica", "Muebles", 129990.0));
        productos.add(crearProducto("Escritorio Roble", "Muebles", 169990.0));
        productos.add(crearProducto("Audifonos Bluetooth", "Tecnologia", 39990.0));

        productoRepository.saveAll(productos);
        System.out.println("Productos base creados");
    }

    private Empleado crearEmpleado(String nombre, Cargo cargo, Sucursal sucursal) {
        Empleado empleado = new Empleado();
        empleado.setNombre(nombre);
        empleado.setCargo(cargo);
        empleado.setSucursal(sucursal);
        return empleado;
    }

    private Producto crearProducto(String nombre, String categoria, Double precio) {
        Producto producto = new Producto();
        producto.setNombre(nombre);
        producto.setCategoria(categoria);
        producto.setPrecio(precio);
        return producto;
    }
}
