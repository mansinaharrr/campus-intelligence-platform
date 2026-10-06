package com.campusintel.platform.location;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CampusLocationRepository
        extends JpaRepository<CampusLocation, Long> {

    List<CampusLocation> findByActiveTrue();

    Optional<CampusLocation> findByName(String name);

    boolean existsByName(String name);
}