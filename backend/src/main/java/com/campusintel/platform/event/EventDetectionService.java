package com.campusintel.platform.event;

import com.campusintel.platform.report.Report;
import com.campusintel.platform.report.ReportRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EventDetectionService {

    private final ReportRepository reportRepository;
    private final CampusEventRepository eventRepository;

    public EventDetectionService(
            ReportRepository reportRepository,
            CampusEventRepository eventRepository
    ) {
        this.reportRepository = reportRepository;
        this.eventRepository = eventRepository;
    }

    @Transactional
    public CampusEvent detectEvent(
            Long locationId,
            Long categoryId
    ) {

        LocalDateTime windowStart =
        LocalDateTime.now(java.time.ZoneOffset.UTC).minusMinutes(30);

        List<Report> recentReports =
                reportRepository.findRecentReports(
                        locationId,
                        categoryId,
                        windowStart
                );

       

if (recentReports.size() < 3) {
    return null;
}

        List<CampusEvent> activeEvents =
                eventRepository
                        .findByLocationIdAndCategoryIdAndStatus(
                                locationId,
                                categoryId,
                                "ACTIVE"
                        );

        if (!activeEvents.isEmpty()) {

            CampusEvent existingEvent = activeEvents.get(0);

            existingEvent.setLastActivityAt(
                    LocalDateTime.now()
            );

            return eventRepository.save(existingEvent);
        }

        Report firstReport = recentReports.get(0);

        CampusEvent event = new CampusEvent();

        event.setLocation(firstReport.getLocation());
        event.setCategoryId(categoryId);
        event.setSource("AUTO");
        event.setTitle(buildTitle(categoryId));
        event.setSeverity(calculateSeverity(recentReports));
        event.setConfidence(calculateConfidence(recentReports));
        event.setStatus("ACTIVE");
        event.setStartedAt(windowStart);
        event.setLastActivityAt(LocalDateTime.now());

        return eventRepository.save(event);
    }

    private String buildTitle(Long categoryId) {

        return switch (categoryId.intValue()) {
            case 1 -> "Crowding Detected";
            case 2 -> "Parking Issue Detected";
            case 3 -> "Wi-Fi Outage Detected";
            case 4 -> "Transport Delay Detected";
            case 5 -> "Infrastructure Issue Detected";
            default -> "Campus Issue Detected";
        };
    }

    private int calculateSeverity(List<Report> reports) {

        return reports.stream()
                .mapToInt(Report::getSeverity)
                .max()
                .orElse(1);
    }

    private double calculateConfidence(List<Report> reports) {

        return Math.min(1.0, reports.size() / 8.0);
    }
}