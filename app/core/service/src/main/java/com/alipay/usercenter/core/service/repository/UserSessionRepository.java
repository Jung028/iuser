package com.alipay.usercenter.core.service.repository;

import com.alipay.usercenter.core.domain.UserInfo;

public interface UserSessionRepository {

    UserInfo queryUserInfo(String phoneNo);
}
