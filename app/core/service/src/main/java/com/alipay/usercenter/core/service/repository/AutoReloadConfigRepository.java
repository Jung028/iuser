package com.alipay.usercenter.core.service.repository;

import com.alipay.usercenter.common.service.facade.request.UpdateAutoReloadConfigRequest;
import com.alipay.usercenter.core.model.AutoReloadConfig;

/**
 * @author adam
 * @date 26/3/2026 5:09 PM
 */
public interface AutoReloadConfigRepository {

    AutoReloadConfig queryAutoReloadConfig(String userId);

    void updateAutoReloadConfig(UpdateAutoReloadConfigRequest request);
}