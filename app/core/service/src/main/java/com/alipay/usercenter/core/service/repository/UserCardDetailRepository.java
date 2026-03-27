package com.alipay.usercenter.core.service.repository;

import com.alipay.usercenter.common.service.facade.item.UserCardDetailItem;
import com.alipay.usercenter.common.service.facade.request.InsertNewCardRequest;
import com.alipay.usercenter.core.model.UserCardDetail;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author adam
 * @date 24/3/2026 11:12 PM
 */
@Repository
public interface UserCardDetailRepository {
    UserCardDetail queryDefaultCard(String userId);

    List<UserCardDetail> queryCardDetailList(String userId);

    void insertNewCard(InsertNewCardRequest request);

    void toggleAutoReloadConfig(boolean autoReload);
}