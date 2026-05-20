package com.alipay.usercenter.core.converter;

import com.alipay.sofa.rpc.common.utils.JSONUtils;
import com.alipay.usercenter.common.dal.auto.dataobject.UserInfoDO;
import com.alipay.usercenter.common.service.facade.config.ContactConfig;
import com.alipay.usercenter.common.service.facade.enums.UserStatus;
import com.alipay.usercenter.common.service.facade.item.UserInfoItem;
import com.alipay.usercenter.core.model.UserInfo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class UserInfoConvertor {

    public static UserInfo convertToDomain(UserInfoDO userInfoDO) {
        if (userInfoDO == null) return null;

        UserInfo userInfo = new UserInfo();
        userInfo.setId(userInfoDO.getId());
        userInfo.setPhoneNo(userInfoDO.getPhoneNo());
        userInfo.setUserId(userInfoDO.getUserId());
        userInfo.setGmtModified(userInfoDO.getGmtModified());
        userInfo.setGmtCreate(userInfoDO.getGmtCreate());
        userInfo.setHashedPassword(userInfoDO.getHashedPassword());
        userInfo.setStatus(userInfoDO.getStatus());

        // to map object to ContactConfig instance
        ObjectMapper objectMapper = new ObjectMapper();
        if (userInfoDO.getContactConfig() != null) {
            try {
                ContactConfig config = objectMapper.readValue(userInfoDO.getContactConfig().toString(),
                        ContactConfig.class);
                userInfo.setContactConfig(config);
            } catch (JsonProcessingException e) {
                throw new RuntimeException("Failed to parse contactConfig JSON", e);
            }
        }

        Object extInfo = null;
        try {
            if (userInfoDO.getExtInfo() != null) {
                extInfo = objectMapper.readValue(userInfoDO.getExtInfo().toString(), Object.class);
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        userInfo.setExtInfo(extInfo);
        userInfo.setUserName(userInfoDO.getUserName());
        return userInfo;
    }

    public static UserInfoDO convertToDO(UserInfo userInfo) {
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
        userInfoDO.setContactConfig(userInfo.getContactConfig());
        userInfoDO.setExtInfo(userInfo.getExtInfo());
        userInfoDO.setUserName(userInfo.getUserName());
        return userInfoDO;
    }

    public static UserInfoItem convertToItem(UserInfo userInfo) {
        if (userInfo == null) {
            return null;
        }
        UserInfoItem userInfoItem = new UserInfoItem();
        userInfoItem.setGmtCreate(userInfo.getGmtCreate());
        userInfoItem.setGmtModified(userInfo.getGmtModified());
        userInfoItem.setId(userInfo.getId());
        userInfoItem.setHashedPassword(userInfo.getHashedPassword());
        userInfoItem.setPhoneNo(userInfo.getPhoneNo());
        userInfoItem.setStatus(UserStatus.valueOf(userInfo.getStatus()));
        userInfoItem.setUserId(userInfo.getUserId());
        userInfoItem.setContactConfig(userInfo.getContactConfig());
        userInfoItem.setExtInfo(JSONUtils.toJSONString(userInfo.getExtInfo()));
        userInfoItem.setUserName(userInfo.getUserName());
        return userInfoItem;
    }
}
