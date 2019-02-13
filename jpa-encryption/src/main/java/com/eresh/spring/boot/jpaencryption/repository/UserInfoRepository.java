package com.eresh.spring.boot.jpaencryption.repository;

import com.eresh.spring.boot.jpaencryption.domain.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {
}
