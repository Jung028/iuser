package com.alipay.usercenter.core.converter;

import com.alipay.usercenter.common.dal.auto.dataobject.UserCardDetailDO;
import com.alipay.usercenter.common.service.facade.item.UserCardDetailItem;
import com.alipay.usercenter.core.model.UserCardDetail;

import java.util.ArrayList;
import java.util.List;

/**
 * @author adam
 * @date 27/3/2026 4:22 PM
 */
public class UserCardDetailConvertor {


    public static List<UserCardDetailItem> convertToItem(List<UserCardDetail> userCardDetailList) {
        List<UserCardDetailItem> userCardDetailItemList = new ArrayList<>();
        for (UserCardDetail userCardDetail : userCardDetailList) {
            UserCardDetailItem userCardDetailItem = new UserCardDetailItem();
            userCardDetailItem.setCardNetwork(userCardDetail.getCardNetwork());
            userCardDetailItem.setUserId(userCardDetail.getUserId());
            userCardDetailItem.setCardType(userCardDetail.getCardType());
            userCardDetailItem.setUserCardId(userCardDetail.getUserCardId());
            userCardDetailItem.setGmtCreate(userCardDetail.getGmtCreate());
            userCardDetailItem.setGmtModified(userCardDetail.getGmtModified());
            userCardDetailItem.setCardholderName(userCardDetail.getCardholderName());
            userCardDetailItem.setExpiryMonth(userCardDetail.getExpiryMonth());
            userCardDetailItem.setExpiryYear(userCardDetail.getExpiryYear());
            userCardDetailItem.setLastFour(userCardDetail.getLastFour());
            userCardDetailItem.setProvider(userCardDetail.getProvider());
            userCardDetailItem.setProviderToken(userCardDetail.getProviderToken());
            userCardDetailItem.setProviderCustomerId(userCardDetail.getProviderCustomerId());
            userCardDetailItem.setStatus(userCardDetail.getStatus());
            userCardDetailItemList.add(userCardDetailItem);
        }
        return userCardDetailItemList;
    }

    public static UserCardDetailItem convertToItem(UserCardDetail userCardDetail) {
        if (userCardDetail == null) {
            return null;
        }
        UserCardDetailItem userCardDetailItem = new UserCardDetailItem();
        userCardDetailItem.setCardNetwork(userCardDetail.getCardNetwork());
        userCardDetailItem.setUserId(userCardDetail.getUserId());
        userCardDetailItem.setCardType(userCardDetail.getCardType());
        userCardDetailItem.setUserCardId(userCardDetail.getUserCardId());
        userCardDetailItem.setGmtCreate(userCardDetail.getGmtCreate());
        userCardDetailItem.setGmtModified(userCardDetail.getGmtModified());
        userCardDetailItem.setCardholderName(userCardDetail.getCardholderName());
        userCardDetailItem.setExpiryMonth(userCardDetail.getExpiryMonth());
        userCardDetailItem.setExpiryYear(userCardDetail.getExpiryYear());
        userCardDetailItem.setLastFour(userCardDetail.getLastFour());
        userCardDetailItem.setProvider(userCardDetail.getProvider());
        userCardDetailItem.setProviderToken(userCardDetail.getProviderToken());
        userCardDetailItem.setProviderCustomerId(userCardDetail.getProviderCustomerId());
        userCardDetailItem.setStatus(userCardDetail.getStatus());
        return userCardDetailItem;
    }

    public static UserCardDetail convertToDomain(UserCardDetailDO userCardDetailDO) {
        if (userCardDetailDO == null) {
            return null;
        }
        UserCardDetail userCardDetail = new UserCardDetail();
        userCardDetail.setUserId(userCardDetailDO.getUserId());
        userCardDetail.setCardNetwork(userCardDetailDO.getCardNetwork());
        userCardDetail.setCardType(userCardDetailDO.getCardType());
        userCardDetail.setUserCardId(userCardDetailDO.getUserCardId());
        userCardDetail.setGmtCreate(userCardDetailDO.getGmtCreate());
        userCardDetail.setGmtModified(userCardDetailDO.getGmtModified());
        userCardDetail.setCardholderName(userCardDetailDO.getCardholderName());
        userCardDetail.setExpiryMonth(userCardDetailDO.getExpiryMonth());
        userCardDetail.setExpiryYear(userCardDetailDO.getExpiryYear());
        userCardDetail.setLastFour(userCardDetailDO.getLastFour());
        userCardDetail.setProvider(userCardDetailDO.getProvider());
        userCardDetail.setProviderToken(userCardDetailDO.getProviderToken());
        userCardDetail.setProviderCustomerId(userCardDetailDO.getProviderCustomerId());
        userCardDetail.setStatus(userCardDetailDO.getStatus());
        return userCardDetail;
    }

    public static List<UserCardDetail> convertToDomain(List<UserCardDetailDO> userCardDetailDOList) {
        List<UserCardDetail> userCardDetailList = new ArrayList<>();
        for (UserCardDetailDO userCardDetailDO : userCardDetailDOList) {
            UserCardDetail userCardDetail = new UserCardDetail();
            userCardDetail.setUserId(userCardDetailDO.getUserId());
            userCardDetail.setCardNetwork(userCardDetailDO.getCardNetwork());
            userCardDetail.setCardType(userCardDetailDO.getCardType());
            userCardDetail.setUserCardId(userCardDetailDO.getUserCardId());
            userCardDetail.setGmtCreate(userCardDetailDO.getGmtCreate());
            userCardDetail.setGmtModified(userCardDetailDO.getGmtModified());
            userCardDetail.setCardholderName(userCardDetailDO.getCardholderName());
            userCardDetail.setExpiryMonth(userCardDetailDO.getExpiryMonth());
            userCardDetail.setExpiryYear(userCardDetailDO.getExpiryYear());
            userCardDetail.setLastFour(userCardDetailDO.getLastFour());
            userCardDetail.setProvider(userCardDetailDO.getProvider());
            userCardDetail.setProviderToken(userCardDetailDO.getProviderToken());
            userCardDetail.setProviderCustomerId(userCardDetailDO.getProviderCustomerId());
            userCardDetail.setStatus(userCardDetailDO.getStatus());
            userCardDetailList.add(userCardDetail);
        }
        return userCardDetailList;
    }

    public static UserCardDetailDO convertToDO(UserCardDetail userCardDetail) {
        UserCardDetailDO userCardDetailDO = new UserCardDetailDO();
        userCardDetailDO.setUserCardId(userCardDetail.getUserCardId());
        userCardDetailDO.setCardNetwork(userCardDetail.getCardNetwork());
        userCardDetailDO.setUserId(userCardDetail.getUserId());
        userCardDetailDO.setCardType(String.valueOf(userCardDetail.getCardType()));
        userCardDetailDO.setGmtCreate(userCardDetail.getGmtCreate());
        userCardDetailDO.setGmtModified(userCardDetail.getGmtModified());
        userCardDetailDO.setCardholderName(userCardDetail.getCardholderName());
        userCardDetailDO.setExpiryMonth(userCardDetail.getExpiryMonth());
        userCardDetailDO.setExpiryYear(userCardDetail.getExpiryYear());
        userCardDetailDO.setLastFour(userCardDetail.getLastFour());
        userCardDetailDO.setProvider(userCardDetail.getProvider());
        userCardDetailDO.setProviderToken(userCardDetail.getProviderToken());
        userCardDetailDO.setProviderCustomerId(userCardDetail.getProviderCustomerId());
        userCardDetailDO.setStatus(userCardDetail.getStatus());
        return userCardDetailDO;
    }
}