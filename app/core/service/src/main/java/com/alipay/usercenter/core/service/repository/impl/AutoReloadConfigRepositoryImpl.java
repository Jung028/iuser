package com.alipay.usercenter.core.service.repository.impl;

import com.alipay.usercenter.common.dal.auto.custom.AutoReloadConfigDAO;
import com.alipay.usercenter.common.service.facade.request.UpdateAutoReloadConfigRequest;
import com.alipay.usercenter.core.exception.RepositoryException;
import com.alipay.usercenter.core.model.AutoReloadConfig;
import com.alipay.usercenter.core.service.repository.AutoReloadConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author adam
 * @date 26/3/2026 5:09 PM
 */
public class AutoReloadConfigRepositoryImpl implements AutoReloadConfigRepository {

    @Autowired
    private AutoReloadConfigDAO autoReloadConfigDAO;

    @Override
    public AutoReloadConfig queryAutoReloadConfig(String userId) {
        return null;
    }

    @Override
    public void updateAutoReloadConfig(UpdateAutoReloadConfigRequest request) {
        try {
            if (request == null) {
                return;
            }
            int rows = autoReloadConfigDAO.updateAutoReloadConfig(
                    request.getUserId(),
                    request.getCardId(),
                    request.getAutoReloadAmount(),
                    request.getThresholdAmount());
            if (rows > 0) {
                throw new RepositoryException("failed to update auto reload config");
            }
        } catch (RepositoryException e) {
            throw e;
        } catch (Exception e) {
            throw new RepositoryException("DB error during insert user auth", e);
        }
    }
}