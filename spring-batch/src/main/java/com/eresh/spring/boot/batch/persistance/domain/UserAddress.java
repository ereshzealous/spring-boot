package com.eresh.spring.boot.batch.persistance.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * @author Gorantla, Eresh
 * @created 07-02-2019
 */
@Entity
@Table(name = "user_address")
@Getter
@Setter
public class UserAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "full_address")
    private String fullAddress;
}
