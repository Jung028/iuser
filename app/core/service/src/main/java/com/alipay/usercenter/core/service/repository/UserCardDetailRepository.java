package com.alipay.usercenter.core.service.repository;

import com.alipay.usercenter.core.model.UserCardDetail;

import java.util.List;
import java.util.UUID;

/**
 * @author adam
 * @date 24/3/2026 11:12 PM
 */
public interface UserCardDetailRepository {
    UserCardDetail queryDefaultCard(UUID userCardId);

    List<UserCardDetail> queryCardDetailList(String userId);

    void insertNewCard(UserCardDetail userCardDetail);

    UserCardDetail findByProviderToken(String paymentMethodId, String stripe);
}