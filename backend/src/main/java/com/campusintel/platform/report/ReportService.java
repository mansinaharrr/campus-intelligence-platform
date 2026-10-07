package com.campusintel.platform.report;

import com.campusintel.platform.location.CampusLocation;
import com.campusintel.platform.location.CampusLocationRepository;
import com.campusintel.platform.user.User;
import com.campusintel.platform.user.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportService {

    private final ReportRepository reportRepository;
    private final CampusLocationRepository locationRepository;
    private final UserRepository userRepository;

    public ReportService(
            ReportRepository reportRepository,
            CampusLocationRepository locationRepository,
            UserRepository userRepository
    ) {
        this.reportRepository = reportRepository;
        this.locationRepository = locationRepository;
        this.userRepository = userRepository;
    }

    public List<ReportResponse> getReportsForLocation(Long locationId) {
        return reportRepository.findByLocationId(locationId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public List<ReportResponse> getActiveReports() {
        return reportRepository.findByStatus("VISIBLE")
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public ReportResponse createReport(
            Long locationId,
            String userEmail,
            Long categoryId,
            String description,
            Integer severity,
            Integer crowdLevel,
            Integer waitMinutes
    ) {
        CampusLocation location = locationRepository.findById(locationId)
                .orElseThrow(() -> new IllegalArgumentException("Location not found"));

        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (severity < 1 || severity > 3) {
            throw new IllegalArgumentException("Severity must be between 1 and 3");
        }

        if (crowdLevel != null && (crowdLevel < 1 || crowdLevel > 5)) {
            throw new IllegalArgumentException("Crowd level must be between 1 and 5");
        }

        Report report = new Report();
        report.setReporter(user);
        report.setLocation(location);
        report.setCategoryId(categoryId);
        report.setDescription(description);
        report.setSeverity(severity);
        report.setCrowdLevel(crowdLevel);
        report.setWaitMinutes(waitMinutes);

        Report savedReport = reportRepository.save(report);

        return toResponse(savedReport);
    }

    private ReportResponse toResponse(Report report) {

        User reporter = report.getReporter();
        CampusLocation location = report.getLocation();

        return new ReportResponse(
                report.getId(),
                reporter.getId(),
                reporter.getDisplayName(),
                location.getId(),
                location.getName(),
                report.getCategoryId(),
                report.getDescription(),
                report.getSeverity(),
                report.getCrowdLevel(),
                report.getWaitMinutes(),
                report.getStatus(),
                report.getEventId(),
                report.getConfirmCount(),
                report.getDisagreeCount(),
                report.getCreatedAt(),
                report.getUpdatedAt()
        );
    }
}