package com.alipay.usercenter.core.service.repository;

import com.alipay.usercenter.common.service.facade.enums.Provider;
import com.alipay.usercenter.core.model.UserCardProvider;

import java.util.Optional;

/**
 * @author adam
 * @date 2/4/2026 3:22 PM
 */

public interface UserCardProviderRepository {

    UserCardProvider findByUserIdAndProvider(String userId, String stripe);

    UserCardProvider insertNewCardProvider(UserCardProvider userCardProvider);
}