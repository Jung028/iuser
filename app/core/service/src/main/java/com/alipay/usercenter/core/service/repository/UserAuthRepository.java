package com.alipay.usercenter.core.service.repository;

import com.alipay.usercenter.core.model.UserAuth;
import com.alipay.usercenter.core.model.UserInfo;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAuthRepository {

    UserAuth queryUserAuth(String userId);

    void insertUserAuth(UserAuth userAuth);
}
