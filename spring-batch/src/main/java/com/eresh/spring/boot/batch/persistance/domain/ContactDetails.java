package com.eresh.spring.boot.batch.persistance.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * @author Gorantla, Eresh
 * @created 07-02-2019
 */
@Getter
@Setter
@Entity
@Table(name = "contact_details")
public class ContactDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "contact_number")
    private String contactNumber;

    @Column(name ="email_address")
    private String emailAddress;
}
