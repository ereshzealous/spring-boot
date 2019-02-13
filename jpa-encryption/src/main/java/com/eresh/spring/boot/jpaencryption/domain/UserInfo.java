package com.eresh.spring.boot.jpaencryption.domain;

import com.eresh.spring.boot.jpa.converters.StringCryptoConverter;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "user_info")
@DynamicUpdate
public class UserInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "sid")
    @Convert(converter = StringCryptoConverter.class)
    private String sid;
}
