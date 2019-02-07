package com.eresh.spring.boot.batch.persistance.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.time.ZonedDateTime;

/**
 * @author Gorantla, Eresh
 * @created 07-02-2019
 */
@Entity
@Table(name = "user_details")
@Getter
@Setter
@DynamicInsert
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name= "gender")
    private String gender;

    @Column(name = "birth_place")
    private String birthPlace;

    @Column(name = "date_of_birth")
    private ZonedDateTime dateOfBirth;

    @Column(name = "father_name")
    private String fatherName;

    @Column(name = "mother_name")
    private String motherName;

    @Column(name = "married")
    private boolean married;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "spouse_details_id")
    private SpouseDetails spouseDetails;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private UserAddress userAddress;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "contact_details_id")
    private ContactDetails contactDetails;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "social_details_id")
    private SocialNetworkDetails socialNetworkDetails;

    @Column(name = "about_you")
    private String aboutYou;

    @Column(name = "created_date")
    private ZonedDateTime createdDate = ZonedDateTime.now();
}
