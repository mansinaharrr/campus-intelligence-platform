package com.campusintel.platform.report;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReportConfirmationRepository
        extends JpaRepository<ReportConfirmation, Long> {

    Optional<ReportConfirmation> findByReportIdAndUserId(
            Long reportId,
            Long userId
    );
}