package com.alipay.usercenter.core.service.repository.impl;

import com.alipay.usercenter.common.dal.auto.dataobject.UserInfoDO;
import com.alipay.usercenter.core.converter.UserInfoConvertor;
import com.alipay.usercenter.core.model.UserInfo;
import com.alipay.usercenter.core.service.repository.AbstractUserRepository;
import com.alipay.usercenter.core.service.repository.UserInfoRepository;
import org.springframework.stereotype.Repository;

@Repository
public class UserInfoRepositoryImpl extends AbstractUserRepository implements UserInfoRepository {

    @Override
    public UserInfo queryUserInfo(String phoneNo) {
        UserInfoDO userInfoDO = userInfoDAO.queryUserInfo(phoneNo);
        if(userInfoDO == null) {
            return null;
        }
        return UserInfoConvertor.convertToDomain(userInfoDO);
    }

    @Override
    public void insertUserInfo(UserInfo userInfo) {
        UserInfoDO userInfoDO = UserInfoConvertor.convertToDO(userInfo);
        try {
            userInfoDAO.insertUserInfo(userInfoDO);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
