package com.alipay.usercenter.core.service.repository;


import com.alipay.usercenter.common.dal.auto.custom.UserInfoDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class AbstractUserRepository {

    @Autowired
    protected UserInfoDAO userInfoDAO;

    public void setUserInfoDAO(UserInfoDAO userInfoDAO) {
        this.userInfoDAO = userInfoDAO;
    }
}
