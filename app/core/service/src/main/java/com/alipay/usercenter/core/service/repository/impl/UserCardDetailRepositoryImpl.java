package com.alipay.usercenter.core.service.repository.impl;

import com.alipay.usercenter.common.service.facade.item.UserCardDetailItem;
import com.alipay.usercenter.common.service.facade.request.InsertNewCardRequest;
import com.alipay.usercenter.core.model.UserCardDetail;
import com.alipay.usercenter.core.service.repository.UserCardDetailRepository;

import java.util.List;

/**
 * @author adam
 * @date 24/3/2026 11:12 PM
 */
public class UserCardDetailRepositoryImpl implements UserCardDetailRepository {

    @Override
    public UserCardDetail queryDefaultCard(String userId) {
        return null;
    }

    @Override
    public List<UserCardDetail> queryCardDetailList(String userId) {
        return List.of();
    }

    @Override
    public void insertNewCard(InsertNewCardRequest request) {

    }

    @Override
    public void toggleAutoReloadConfig(boolean autoReload) {

    }

}