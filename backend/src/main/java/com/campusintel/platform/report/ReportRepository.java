package com.campusintel.platform.report;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReportRepository extends JpaRepository<Report, Long> {

    @Query("""
            SELECT r
            FROM Report r
            JOIN FETCH r.reporter
            JOIN FETCH r.location
            WHERE r.location.id = :locationId
            """)
    List<Report> findByLocationId(Long locationId);

    @Query("""
            SELECT r
            FROM Report r
            JOIN FETCH r.reporter
            JOIN FETCH r.location
            WHERE r.status = :status
            """)
    List<Report> findByStatus(String status);
}