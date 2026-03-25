package com.alipay.usercenter.core.service.repository;

import com.alipay.usercenter.common.service.facade.item.UserCardDetailItem;

/**
 * @author adam
 * @date 24/3/2026 11:12 PM
 */
public interface UserCardDetailRepository {
    UserCardDetailItem queryDefaultCard(String userId);
}