package com.alipay.usercenter.core.service.repository.impl;

import com.alipay.usercenter.common.dal.auto.dataobject.UserAuthDO;
import com.alipay.usercenter.common.dal.auto.dataobject.UserInfoDO;
import com.alipay.usercenter.core.converter.UserAuthConvertor;
import com.alipay.usercenter.core.converter.UserInfoConvertor;
import com.alipay.usercenter.core.exception.RepositoryException;
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
        if (userId == null) {
            return null;
        }
        UserAuthDO userAuthDO = userAuthDAO.queryUserAuth(userId);
        if (userAuthDO == null) {
            return null;
        }
        return UserAuthConvertor.convertToDomain(userAuthDO);
    }

    @Override
    public void insertUserAuth(UserAuth userAuth) {
        try {
            UserAuthDO userAuthDO = UserAuthConvertor.convertToDO(userAuth);
            int rows = userAuthDAO.insertUserAuth(userAuthDO);
            if (rows <= 0) {
                throw new RepositoryException("Insert failed for userId: " + userAuth.getUserId());
            }
        } catch (RepositoryException e) {
            throw e;
        } catch (Exception e) {
            throw new RepositoryException("DB error during insert user auth", e);
        }
    }
}
