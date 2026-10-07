package com.campusintel.platform.report;

import java.time.LocalDateTime;

public class ReportResponse {

    private Long id;
    private Long reporterId;
    private String reporterName;

    private Long locationId;
    private String locationName;

    private Long categoryId;
    private String description;
    private Integer severity;
    private Integer crowdLevel;
    private Integer waitMinutes;

    private String status;
    private Long eventId;

    private Integer confirmCount;
    private Integer disagreeCount;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public ReportResponse() {
    }

    public ReportResponse(
            Long id,
            Long reporterId,
            String reporterName,
            Long locationId,
            String locationName,
            Long categoryId,
            String description,
            Integer severity,
            Integer crowdLevel,
            Integer waitMinutes,
            String status,
            Long eventId,
            Integer confirmCount,
            Integer disagreeCount,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {
        this.id = id;
        this.reporterId = reporterId;
        this.reporterName = reporterName;
        this.locationId = locationId;
        this.locationName = locationName;
        this.categoryId = categoryId;
        this.description = description;
        this.severity = severity;
        this.crowdLevel = crowdLevel;
        this.waitMinutes = waitMinutes;
        this.status = status;
        this.eventId = eventId;
        this.confirmCount = confirmCount;
        this.disagreeCount = disagreeCount;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return id;
    }

    public Long getReporterId() {
        return reporterId;
    }

    public String getReporterName() {
        return reporterName;
    }

    public Long getLocationId() {
        return locationId;
    }

    public String getLocationName() {
        return locationName;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public String getDescription() {
        return description;
    }

    public Integer getSeverity() {
        return severity;
    }

    public Integer getCrowdLevel() {
        return crowdLevel;
    }

    public Integer getWaitMinutes() {
        return waitMinutes;
    }

    public String getStatus() {
        return status;
    }

    public Long getEventId() {
        return eventId;
    }

    public Integer getConfirmCount() {
        return confirmCount;
    }

    public Integer getDisagreeCount() {
        return disagreeCount;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}