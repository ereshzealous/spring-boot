package com.eresh.spring.boot.batch.service.vo;

import com.eresh.spring.boot.batch.persistance.domain.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import java.time.ZonedDateTime;

/**
 * @author Gorantla, Eresh
 * @created 07-02-2019
 */
@Getter
@Setter
public class UserVO {
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

    public UserVO(User user) {
        this.id = user.getId();
        this.aboutYou = user.getAboutYou();
        this.birthPlace = user.getBirthPlace();
        this.contactNumber = user.getContactDetails() != null ? user.getContactDetails().getContactNumber() : null;
        this.createdDate = user.getCreatedDate();
        this.dateOfBirth = user.getDateOfBirth();
        this.emailAddress = user.getContactDetails() != null ? user.getContactDetails().getEmailAddress() : null;
        this.facebookUrl = user.getSocialNetworkDetails() != null ? user.getSocialNetworkDetails().getFacebookUrl() : null;
        this.fatherName = user.getFatherName();
        this.firstName = user.getFirstName();
        this.fullAddress = user.getUserAddress() != null ? user.getUserAddress().getFullAddress() : null;
        this.gender = user.getGender();
        this.lastName = user.getLastName();
        this.linkedinUrl = user.getSocialNetworkDetails() != null ? user.getSocialNetworkDetails().getLinkedinUrl() : null;
        this.married = user.isMarried();
        this.motherName = user.getMotherName();
        this.personalBlogUrl = user.getSocialNetworkDetails() != null ?user.getSocialNetworkDetails().getPersonalBlogUrl() : null;
        this.spouseName = user.getSpouseDetails() != null ? user.getSpouseDetails().getSpouseName() : null;
        this.twitterUrl = user.getSocialNetworkDetails() != null ? user.getSocialNetworkDetails().getTwitterUrl() : null;
    }
}
