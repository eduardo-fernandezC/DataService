package com.DataService.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.DataService.model.Empleado;
import com.DataService.model.enums.Cargo;

@Repository
public interface EmpleadoRepository extends JpaRepository<Empleado, Long> {

    List<Empleado> findBySucursalIdSucursal(Long idSucursal);

    List<Empleado> findByCargo(Cargo cargo);

}