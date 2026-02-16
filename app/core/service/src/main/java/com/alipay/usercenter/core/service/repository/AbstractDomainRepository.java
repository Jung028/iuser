package com.alipay.usercenter.core.service.repository;


import com.alipay.usercenter.common.dal.auto.custom.UserInfoDAO;

public class AbstractDomainRepository {

    protected UserInfoDAO userInfoDAO;

    public void setUserInfoDAO(UserInfoDAO userInfoDAO) {
        this.userInfoDAO = userInfoDAO;
    }
}
