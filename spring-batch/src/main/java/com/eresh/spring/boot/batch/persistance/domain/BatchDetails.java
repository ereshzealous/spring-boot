package com.eresh.spring.boot.batch.persistance.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.ZonedDateTime;

/**
 * @author Gorantla, Eresh
 * @created 07-02-2019
 */
@Getter
@Setter
@Entity
@Table(name = "batch_details")
@DynamicUpdate
public class BatchDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "batch_size")
    private Integer batchSize;

    @Column(name = "data")
    private String data;

    @Column(name = "status")
    private String status;

    @Column(name = "error_stacktrace")
    private String errorStacktrace;

    @Column(name = "created_date")
    private ZonedDateTime createdDate = ZonedDateTime.now();

    @Column(name = "processing_time")
    private Long processingTime;
}
