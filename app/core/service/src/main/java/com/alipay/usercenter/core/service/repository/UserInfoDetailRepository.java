package com.alipay.usercenter.core.service.repository;

import com.alipay.usercenter.core.domain.UserInfo;

public interface UserInfoDetailRepository {

    UserInfo queryUserInfo(String phoneNo);
}
