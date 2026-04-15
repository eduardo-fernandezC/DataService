package com.DataService.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.DataService.model.Ciudad;
import com.DataService.model.Region;

public interface CiudadRepository extends JpaRepository<Ciudad, Long>{

    List<Ciudad> findByRegion(Region region);
}
