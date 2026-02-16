package com.alipay.usercenter.core.service.repository;

import com.alipay.usercenter.core.domain.UserInfo;

public interface UserInfoRepository {

    UserInfo queryUserInfo(String phoneNo);

    void insertUserInfo(UserInfo userInfo);
}
