package com.alipay.usercenter.core.service.repository.impl;

import com.alipay.usercenter.common.dal.auto.dataobject.UserInfoDO;
import com.alipay.usercenter.core.converter.UserInfoConvertor;
import com.alipay.usercenter.core.exception.RepositoryException;
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

    @Override
    public void updateExtInfo(String userId, String extInfo) {
        try {
            int rows = userInfoDAO.updateExtInfo(userId, extInfo);
            if (rows <= 0) {
                throw new RepositoryException("Update affected 0 rows for extInfo: "
                        + extInfo);
            }
        } catch (RepositoryException e) {
            throw e;
        } catch (Exception e) {
            throw new RepositoryException("DB error during update user info ", e);
        }
    }
}
