package com.eresh.spring.boot.batch.persistance.repository;

import com.eresh.spring.boot.batch.persistance.domain.BatchDetails;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Gorantla, Eresh
 * @created 07-02-2019
 */
public interface BatchDetailsRepository extends JpaRepository<BatchDetails, Long> {
}
