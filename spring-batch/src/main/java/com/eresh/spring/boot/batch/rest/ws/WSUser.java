package com.eresh.spring.boot.batch.rest.ws;

import com.eresh.spring.boot.batch.service.vo.UserVO;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;

/**
 * @author Gorantla, Eresh
 * @created 07-02-2019
 */
@Getter
@Setter
public class WSUser {
    private Long id;
    private String firstName;
    private String lastName;
    private String gender;
    private String birthPlace;
    private ZonedDateTime dateOfBirth;
    private String fatherName;
    private String motherName;
    private boolean married;
    private String spouseName;
    private String fullAddress;
    private String contactNumber;
    private String emailAddress;
    private String facebookUrl;
    private String linkedinUrl;
    private String twitterUrl;
    private String personalBlogUrl;
    private String aboutYou;
    private ZonedDateTime createdDate;

    public WSUser(UserVO user) {
        this.id = user.getId();
        this.aboutYou = user.getAboutYou();
        this.birthPlace = user.getBirthPlace();
        this.contactNumber = user.getContactNumber();
        this.createdDate = user.getCreatedDate();
        this.dateOfBirth = user.getDateOfBirth();
        this.emailAddress = user.getEmailAddress();
        this.facebookUrl = user.getFacebookUrl();
        this.fatherName = user.getFatherName();
        this.firstName = user.getFirstName();
        this.fullAddress = user.getFullAddress();
        this.gender = user.getGender();
        this.lastName = user.getLastName();
        this.linkedinUrl = user.getLinkedinUrl();
        this.married = user.isMarried();
        this.motherName = user.getMotherName();
        this.personalBlogUrl = user.getPersonalBlogUrl();
        this.spouseName = user.getSpouseName();
        this.twitterUrl = user.getTwitterUrl();
    }
}
