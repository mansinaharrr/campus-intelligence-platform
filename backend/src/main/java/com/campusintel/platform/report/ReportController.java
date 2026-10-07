package com.campusintel.platform.report;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    private final ReportService reportService;
    private final ReportConfirmationService reportConfirmationService;

    public ReportController(
            ReportService reportService,
            ReportConfirmationService reportConfirmationService
    ) {
        this.reportService = reportService;
        this.reportConfirmationService = reportConfirmationService;
    }

    @GetMapping("/active")
    public List<ReportResponse> getActiveReports() {
        return reportService.getActiveReports();
    }

    @GetMapping("/location/{locationId}")
    public List<ReportResponse> getReportsForLocation(
            @PathVariable Long locationId
    ) {
        return reportService.getReportsForLocation(locationId);
    }

    @PostMapping
    public ResponseEntity<ReportResponse> createReport(
            @RequestParam Long locationId,
            @RequestParam Long categoryId,
            @RequestParam String description,
            @RequestParam Integer severity,
            @RequestParam(required = false) Integer crowdLevel,
            @RequestParam(required = false) Integer waitMinutes,
            Authentication authentication
    ) {
        ReportResponse report = reportService.createReport(
                locationId,
                authentication.getName(),
                categoryId,
                description,
                severity,
                crowdLevel,
                waitMinutes
        );

        return ResponseEntity.ok(report);
    }

    @PostMapping("/{reportId}/vote")
    public ResponseEntity<ReportResponse> voteOnReport(
            @PathVariable Long reportId,
            @RequestParam String vote,
            Authentication authentication
    ) {
        ReportResponse response = reportConfirmationService.vote(
                reportId,
                authentication.getName(),
                vote.toUpperCase()
        );

        return ResponseEntity.ok(response);
    }
}