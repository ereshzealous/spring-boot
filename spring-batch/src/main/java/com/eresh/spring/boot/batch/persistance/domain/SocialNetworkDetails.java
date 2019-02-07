package com.eresh.spring.boot.batch.persistance.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * @author Gorantla, Eresh
 * @created 07-02-2019
 */
@Entity
@Table(name = "social_network_details")
@Getter
@Setter
public class SocialNetworkDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "facebook_url")
    private String facebookUrl;

    @Column(name = "linkedin_url")
    private String linkedinUrl;

    @Column(name = "twitter_url")
    private String twitterUrl;

    @Column(name = "personal_blog_url")
    private String personalBlogUrl;
}
