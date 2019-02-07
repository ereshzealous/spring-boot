package com.eresh.spring.boot.batch.persistance.repository;

import com.eresh.spring.boot.batch.persistance.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Gorantla, Eresh
 * @created 07-02-2019
 */
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByFirstName(String firstName);
}
