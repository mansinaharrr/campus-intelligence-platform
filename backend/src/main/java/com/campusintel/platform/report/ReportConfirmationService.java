package com.campusintel.platform.report;

import com.campusintel.platform.user.User;
import com.campusintel.platform.user.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReportConfirmationService {

    private final ReportConfirmationRepository confirmationRepository;
    private final ReportRepository reportRepository;
    private final UserRepository userRepository;

    public ReportConfirmationService(
            ReportConfirmationRepository confirmationRepository,
            ReportRepository reportRepository,
            UserRepository userRepository
    ) {
        this.confirmationRepository = confirmationRepository;
        this.reportRepository = reportRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public ReportResponse vote(
            Long reportId,
            String userEmail,
            String vote
    ) {
        if (!vote.equals("CONFIRM") && !vote.equals("DISAGREE")) {
            throw new IllegalArgumentException(
                    "Vote must be CONFIRM or DISAGREE"
            );
        }

        Report report = reportRepository.findById(reportId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Report not found"));

        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() ->
                        new IllegalArgumentException("User not found"));

        ReportConfirmation confirmation =
                confirmationRepository
                        .findByReportIdAndUserId(reportId, user.getId())
                        .orElse(null);

        if (confirmation != null) {

            if (confirmation.getVote().equals(vote)) {
                throw new IllegalArgumentException(
                        "You have already submitted this vote"
                );
            }

            if (confirmation.getVote().equals("CONFIRM")) {
                report.setConfirmCount(
                        Math.max(0, report.getConfirmCount() - 1)
                );
            } else {
                report.setDisagreeCount(
                        Math.max(0, report.getDisagreeCount() - 1)
                );
            }

            confirmation.setVote(vote);

        } else {

            confirmation = new ReportConfirmation();
            confirmation.setReport(report);
            confirmation.setUser(user);
            confirmation.setVote(vote);
        }

        if (vote.equals("CONFIRM")) {
            report.setConfirmCount(report.getConfirmCount() + 1);
        } else {
            report.setDisagreeCount(report.getDisagreeCount() + 1);
        }

        confirmationRepository.save(confirmation);
        reportRepository.save(report);

        return toResponse(report);
    }

    private ReportResponse toResponse(Report report) {

        User reporter = report.getReporter();

        return new ReportResponse(
                report.getId(),
                reporter.getId(),
                reporter.getDisplayName(),
                report.getLocation().getId(),
                report.getLocation().getName(),
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