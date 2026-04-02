package com.alipay.usercenter.core.service.repository;

import com.alipay.usercenter.core.model.UserInfo;
import org.springframework.stereotype.Repository;

@Repository
public interface UserInfoRepository {

    UserInfo queryUserInfo(String phoneNo);

    UserInfo queryUserInfoByUserId(String userId);

    void insertUserInfo(UserInfo userInfo);

    void updateExtInfo(String userId, String extInfo);
}
