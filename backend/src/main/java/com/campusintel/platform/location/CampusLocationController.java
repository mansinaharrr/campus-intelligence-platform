package com.campusintel.platform.location;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/locations")
public class CampusLocationController {

    private final CampusLocationService locationService;

    public CampusLocationController(
            CampusLocationService locationService
    ) {
        this.locationService = locationService;
    }

    @GetMapping
    public List<CampusLocation> getLocations() {
        return locationService.getActiveLocations();
    }

    @GetMapping("/{id}")
    public CampusLocation getLocation(@PathVariable Long id) {
        return locationService.getLocation(id);
    }
}