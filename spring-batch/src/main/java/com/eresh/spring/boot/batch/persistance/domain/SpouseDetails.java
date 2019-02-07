package com.eresh.spring.boot.batch.persistance.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * @author Gorantla, Eresh
 * @created 07-02-2019
 */
@Entity
@Table(name = "spouse_details")
@Getter
@Setter
public class SpouseDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "spouse_name", nullable = true)
    private String spouseName;
}
