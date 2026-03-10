package com.alipay.usercenter.core.service.repository.impl;

import com.alipay.usercenter.common.dal.auto.dataobject.UserAuthDO;
import com.alipay.usercenter.common.dal.auto.dataobject.UserInfoDO;
import com.alipay.usercenter.core.converter.UserAuthConvertor;
import com.alipay.usercenter.core.converter.UserInfoConvertor;
import com.alipay.usercenter.core.model.UserAuth;
import com.alipay.usercenter.core.model.UserInfo;
import com.alipay.usercenter.core.service.repository.AbstractUserRepository;
import com.alipay.usercenter.core.service.repository.UserAuthRepository;
import com.alipay.usercenter.core.service.repository.UserInfoRepository;
import org.springframework.stereotype.Repository;

@Repository
public class UserAuthRepositoryImpl extends AbstractUserRepository implements UserAuthRepository {
    @Override
    public UserAuth queryUserAuth(String userId) {
        UserAuthDO userAuthDO = userAuthDAO.queryUserAuth(userId);
        if (userAuthDO == null) {
            return null;
        }
        return UserAuthConvertor.convertToDomain(userAuthDO);
    }

    @Override
    public void insertUserAuth(UserAuth userAuth) {
        UserAuthDO userAuthDO = UserAuthConvertor.convertToDO(userAuth);
        try {
            userAuthDAO.insertUserAuth(userAuthDO);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
