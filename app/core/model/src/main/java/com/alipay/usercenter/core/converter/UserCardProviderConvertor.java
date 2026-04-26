package com.alipay.usercenter.core.converter;

import com.alipay.usercenter.common.dal.auto.dataobject.UserCardProviderDO;
import com.alipay.usercenter.common.service.facade.item.UserCardProviderItem;
import com.alipay.usercenter.core.model.UserCardProvider;

/**
 * @author adam
 * @date 2/4/2026 5:17 PM
 */
public class UserCardProviderConvertor {

    public static UserCardProvider convertToDomain(UserCardProviderDO userCardProviderDO) {
        if (userCardProviderDO == null) {
            return null;
        }
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

    public static UserCardProviderDO convertToDO(UserCardProvider userCardProvider) {
        if (userCardProvider == null) {
            return null;
        }
        UserCardProviderDO userCardProviderDO = new UserCardProviderDO();
        userCardProviderDO.setUserId(userCardProvider.getUserId());
        userCardProviderDO.setProvider(userCardProvider.getProvider());
        userCardProviderDO.setProviderCustomerId(userCardProvider.getProviderCustomerId());
        userCardProviderDO.setGmtCreate(userCardProvider.getGmtCreate());
        userCardProviderDO.setGmtModified(userCardProvider.getGmtModified());
        userCardProviderDO.setStatus(userCardProvider.getStatus());
        return userCardProviderDO;
    }

    public static UserCardProviderItem convertToItem(UserCardProvider userCardProvider) {
        if (userCardProvider == null) {
            return null;
        }
        UserCardProviderItem userCardProviderItem = new UserCardProviderItem();
        userCardProviderItem.setUserId(userCardProvider.getUserId());
        userCardProviderItem.setProvider(userCardProvider.getProvider());
        userCardProviderItem.setProviderCustomerId(userCardProvider.getProviderCustomerId());
        userCardProviderItem.setGmtCreate(userCardProvider.getGmtCreate());
        userCardProviderItem.setGmtModified(userCardProvider.getGmtModified());
        userCardProviderItem.setStatus(userCardProvider.getStatus());
        return userCardProviderItem;    }
}