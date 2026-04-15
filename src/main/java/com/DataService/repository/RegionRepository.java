package com.DataService.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.DataService.model.Region;

@Repository
public interface RegionRepository extends JpaRepository<Region, Long> {

    Optional<Region> findByNombre(String nombre);

}