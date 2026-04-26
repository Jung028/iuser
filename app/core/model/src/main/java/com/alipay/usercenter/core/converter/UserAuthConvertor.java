package com.alipay.usercenter.core.converter;

import com.alipay.usercenter.common.dal.auto.dataobject.UserAuthDO;
import com.alipay.usercenter.core.model.UserAuth;

/**
 * @author adam
 * @date 10/3/2026 3:14 PM
 */
public class UserAuthConvertor {

    public static UserAuth convertToDomain(UserAuthDO userAuthDO) {
        if (userAuthDO == null) {
            return null;
        }
        UserAuth userAuth = new UserAuth();
        userAuth.setAuthType(userAuthDO.getAuthType());
        userAuth.setUserId(userAuthDO.getUserId());
        userAuth.setAuthId(userAuthDO.getAuthId());
        userAuth.setCredentialHash(userAuthDO.getCredentialHash());
        userAuth.setGmtModified(userAuthDO.getGmtModified());
        userAuth.setGmtCreate(userAuthDO.getGmtCreate());
        userAuth.setFailedAttempts(userAuthDO.getFailedAttempts());
        userAuth.setIsActive(userAuthDO.getIsActive());
        userAuth.setLastUsed(userAuthDO.getLastUsed());
        userAuth.setLockUntil(userAuthDO.getLockUntil());
        return userAuth;
    }

    public static UserAuthDO convertToDO(UserAuth userAuth) {
        if (userAuth == null) {
            return null;
        }
        UserAuthDO userAuthDO = new UserAuthDO();
        userAuthDO.setAuthType(userAuth.getAuthType());
        userAuthDO.setUserId(userAuth.getUserId());
        userAuthDO.setAuthId(userAuth.getAuthId());
        userAuthDO.setCredentialHash(userAuth.getCredentialHash());
        userAuthDO.setGmtModified(userAuth.getGmtModified());
        userAuthDO.setGmtCreate(userAuth.getGmtCreate());
        userAuthDO.setFailedAttempts(userAuth.getFailedAttempts());
        userAuthDO.setIsActive(userAuth.getIsActive());
        userAuthDO.setLastUsed(userAuth.getLastUsed());
        userAuthDO.setLockUntil(userAuth.getLockUntil());
        return userAuthDO;
    }
}