package com.eresh.spring.boot.batch.service;

import com.eresh.spring.boot.batch.persistance.domain.User;
import com.eresh.spring.boot.batch.persistance.repository.UserRepository;
import com.eresh.spring.boot.batch.service.vo.UserVO;
import com.eresh.spring.boot.commons.exception.ApplicationException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import reactor.core.publisher.Flux;

import java.io.File;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Gorantla, Eresh
 * @created 07-02-2019
 */
@Service
public class UserDataService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;


    public Flux<UserVO> getUserDetailsByFirstName(String firstName) throws ApplicationException {
        Flux<UserVO> users = Flux.defer(() -> Flux.fromIterable(userRepository.findByFirstName(firstName)).map(UserVO :: new));
        return users;
    }

    public Flux<UserVO> getAllUsers() throws ApplicationException {
        Flux<UserVO> users = Flux.defer(() -> Flux.fromIterable(userRepository.findAll()).map(UserVO :: new));
        return users;
    }

    public void manuplate() throws ApplicationException {
        try {
            File propertiesFile = ResourceUtils.getFile("data.json");
            List<User> users = objectMapper.readValue(propertiesFile, objectMapper.getTypeFactory().constructCollectionType(List.class, User.class));
            List<User> users1 = users.stream().limit(100000).collect(Collectors.toList());
            Instant start = Instant.now();
            List<UserVO> userVOS = users1.stream().map(UserVO::new).collect(Collectors.toList());
            Instant end = Instant.now();
            Duration interval = Duration.between(start, end);
            System.out.println("Java 8 streams time -> " + interval.toMillis() + " ::: Size is -> " + userVOS.size());
            start = Instant.now();
            Flux<UserVO> userVOFlux = Flux.defer(() -> Flux.fromIterable(users1).map(UserVO::new));
            end = Instant.now();
            interval = Duration.between(start, end);
            Long x;
            System.out.println("Reactive time -> " + interval.toMillis() + " ::: Size is -> " + userVOFlux.count().subscribe(z -> new Long(z)));
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }
}
