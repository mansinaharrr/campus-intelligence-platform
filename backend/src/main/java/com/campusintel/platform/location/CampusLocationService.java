package com.campusintel.platform.location;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CampusLocationService {

    private final CampusLocationRepository locationRepository;

    public CampusLocationService(
            CampusLocationRepository locationRepository
    ) {
        this.locationRepository = locationRepository;
    }

    public List<CampusLocation> getActiveLocations() {
        return locationRepository.findByActiveTrue();
    }

    public CampusLocation getLocation(Long id) {
        return locationRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("Location not found")
                );
    }
}