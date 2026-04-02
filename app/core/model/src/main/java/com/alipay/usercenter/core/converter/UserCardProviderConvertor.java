package com.alipay.usercenter.core.converter;

import com.alipay.usercenter.common.dal.auto.dataobject.UserCardProviderDO;
import com.alipay.usercenter.core.model.UserCardProvider;

/**
 * @author adam
 * @date 2/4/2026 5:17 PM
 */
public class UserCardProviderConvertor {

    public static UserCardProvider convertToDomain(UserCardProviderDO userCardProviderDO) {
        UserCardProvider userCardProvider = new UserCardProvider();
        userCardProvider.setId(userCardProviderDO.getId());
        userCardProvider.setUserId(userCardProviderDO.getUserId());
        userCardProvider.setProvider(userCardProviderDO.getProvider());
        userCardProvider.setProviderCustomerId(userCardProviderDO.getProviderCustomerId());
        userCardProvider.setGmtCreate(userCardProviderDO.getGmtCreate());
        userCardProvider.setGmtModified(userCardProviderDO.getGmtModified());
        userCardProvider.setStatus(userCardProviderDO.getStatus());
        return userCardProvider;
    }
}