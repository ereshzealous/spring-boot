package com.eresh.spring.boot.jpaencryption.service;

import com.eresh.spring.boot.jpaencryption.domain.UserInfo;
import com.eresh.spring.boot.jpaencryption.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserInfoService {

    @Autowired
    private UserInfoRepository userInfoRepository;

    public void saveUserInfo() {
        try {
            UserInfo userInfo = new UserInfo();
            userInfo.setFirstName("Eresh");
            userInfo.setLastName("Gorantla");
            userInfo.setSid("12345566666");
            userInfoRepository.save(userInfo);
        } catch (Exception e) {
            e.printStackTrace();;
        }
    }

    public List<UserInfo> getUserInfos() {
        return userInfoRepository.findAll();
    }
}
