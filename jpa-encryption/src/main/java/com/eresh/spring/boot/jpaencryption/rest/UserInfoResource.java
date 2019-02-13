package com.eresh.spring.boot.jpaencryption.rest;

import com.eresh.spring.boot.jpaencryption.domain.UserInfo;
import com.eresh.spring.boot.jpaencryption.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserInfoResource {

    @Autowired
    private UserInfoService userInfoService;

    @PostMapping("/user")
    public ResponseEntity<Object> saveUser() {
        userInfoService.saveUserInfo();
        return new ResponseEntity<Object>(HttpStatus.OK);
    }

    @GetMapping("/users")
    public ResponseEntity<Object> getUsers() {
        List<UserInfo> userInfos = userInfoService.getUserInfos();
        return new ResponseEntity<Object>(userInfos, HttpStatus.OK);
    }
}
