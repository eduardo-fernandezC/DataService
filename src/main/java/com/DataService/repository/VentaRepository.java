package com.DataService.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.DataService.model.Empleado;
import com.DataService.model.Venta;

@Repository
public interface VentaRepository extends JpaRepository<Venta, Long> {

    List<Venta> findByFecha(LocalDate fecha);

    List<Venta> findBySucursalIdSucursal(Long idSucursal);

    List<Venta> findByEmpleado(Empleado empleado);

    List<Venta> findByFechaBetween(LocalDate inicio, LocalDate fin); // aca fin es el ultimo dia del mes

}
