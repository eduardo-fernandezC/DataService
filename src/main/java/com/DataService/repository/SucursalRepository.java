package com.DataService.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.DataService.model.Ciudad;
import com.DataService.model.Sucursal;

@Repository
public interface SucursalRepository extends JpaRepository<Sucursal, Long> {

    List<Sucursal> findByCiudad(Ciudad ciudad);

    List<Sucursal> findByCiudadIdCiudad(Long idCiudad);

}
