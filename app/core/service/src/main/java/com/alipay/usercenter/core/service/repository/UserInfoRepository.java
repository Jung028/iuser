package com.alipay.usercenter.core.service.repository;

import com.alipay.usercenter.core.domain.UserInfo;
import org.springframework.stereotype.Repository;

@Repository
public interface UserInfoRepository {

    UserInfo queryUserInfo(String phoneNo);

    void insertUserInfo(UserInfo userInfo);
}
