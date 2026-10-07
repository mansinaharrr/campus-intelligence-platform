package com.campusintel.platform.event;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/events")
public class CampusEventController {

    private final EventDetectionService eventDetectionService;

    public CampusEventController(
            EventDetectionService eventDetectionService
    ) {
        this.eventDetectionService = eventDetectionService;
    }

    @PostMapping("/detect")
    public ResponseEntity<CampusEvent> detectEvent(
            @RequestParam Long locationId,
            @RequestParam Long categoryId
    ) {
        CampusEvent event =
                eventDetectionService.detectEvent(
                        locationId,
                        categoryId
                );

        if (event == null) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(event);
    }
}