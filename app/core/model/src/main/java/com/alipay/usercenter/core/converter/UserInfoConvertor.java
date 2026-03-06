package com.alipay.usercenter.core.converter;

import com.alipay.usercenter.common.dal.auto.dataobject.UserInfoDO;
import com.alipay.usercenter.common.service.facade.item.UserInfoItem;
import com.alipay.usercenter.core.model.UserInfo;

public class UserInfoConvertor {

    public static UserInfo convertDomain(UserInfoDO userInfoDO) {
        if (userInfoDO == null) {
            return null;
        }
        UserInfo userInfo = new UserInfo();
        userInfo.setId(userInfoDO.getId());
        userInfo.setPhoneNo(userInfoDO.getPhoneNo());
        userInfo.setUserId(userInfoDO.getUserId());
        userInfo.setGmtModified(userInfoDO.getGmtModified());
        userInfo.setGmtCreate(userInfoDO.getGmtCreate());
        userInfo.setHashedPassword(userInfoDO.getHashedPassword());
        userInfo.setStatus(userInfoDO.getStatus());
        return userInfo;
    }

    public static UserInfoDO convertDO(UserInfo userInfo) {
        if (userInfo == null) {
            return null;
        }
        UserInfoDO userInfoDO = new UserInfoDO();
        userInfoDO.setPhoneNo(userInfo.getPhoneNo());
        // should we set user id to incremental in db or random val + index here
        userInfoDO.setUserId(userInfo.getUserId());
        userInfoDO.setGmtModified(userInfo.getGmtModified());
        userInfoDO.setGmtCreate(userInfo.getGmtCreate());
        userInfoDO.setHashedPassword(userInfo.getHashedPassword());
        userInfoDO.setStatus(userInfo.getStatus());
        return userInfoDO;
    }

    public static UserInfoItem convertItem(UserInfo userInfo) {
        if (userInfo == null) {
            return null;
        }
        UserInfoItem userInfoItem = new UserInfoItem();
        userInfoItem.setGmtCreate(userInfo.getGmtCreate());
        userInfoItem.setGmtModified(userInfo.getGmtModified());
        userInfoItem.setId(userInfo.getId());
        userInfoItem.setPassword(userInfo.getHashedPassword());
        userInfoItem.setPhoneNo(userInfo.getPhoneNo());
        userInfoItem.setStatus(userInfo.getStatus());
        userInfoItem.setUserId(userInfo.getUserId());
        return userInfoItem;
    }
}
