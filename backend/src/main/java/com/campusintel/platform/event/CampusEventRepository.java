package com.campusintel.platform.event;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CampusEventRepository
        extends JpaRepository<CampusEvent, Long> {

    List<CampusEvent> findByStatus(String status);

    List<CampusEvent> findByLocationId(Long locationId);

    List<CampusEvent> findByLocationIdAndCategoryIdAndStatus(
            Long locationId,
            Long categoryId,
            String status
    );
}