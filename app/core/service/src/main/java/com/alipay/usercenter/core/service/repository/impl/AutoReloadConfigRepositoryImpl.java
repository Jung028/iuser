package com.alipay.usercenter.core.service.repository.impl;

import com.alipay.usercenter.common.dal.auto.custom.AutoReloadConfigDAO;
import com.alipay.usercenter.common.dal.auto.dataobject.AutoReloadConfigDO;
import com.alipay.usercenter.common.dal.auto.dataobject.UserAuthDO;
import com.alipay.usercenter.common.service.facade.request.UpdateAutoReloadConfigRequest;
import com.alipay.usercenter.core.converter.AutoReloadConfigConvertor;
import com.alipay.usercenter.core.converter.UserAuthConvertor;
import com.alipay.usercenter.core.exception.RepositoryException;
import com.alipay.usercenter.core.model.AutoReloadConfig;
import com.alipay.usercenter.core.service.repository.AutoReloadConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * @author adam
 * @date 26/3/2026 5:09 PM
 */
@Repository
public class AutoReloadConfigRepositoryImpl implements AutoReloadConfigRepository {

    @Autowired
    private AutoReloadConfigDAO autoReloadConfigDAO;

    @Override
    public AutoReloadConfig queryAutoReloadConfig(long userId) {
        if (userId <= 0) {
            return null;
        }
        AutoReloadConfigDO autoReloadConfigDO = autoReloadConfigDAO.queryAutoReloadConfig(userId);
        if (autoReloadConfigDO == null) {
            return null;
        }
        return AutoReloadConfigConvertor.convertToDomain(autoReloadConfigDO);
    }

    @Override
    public void insertAutoReloadConfig(AutoReloadConfig autoReloadConfig) {
        try {
            AutoReloadConfigDO autoReloadConfigDO = AutoReloadConfigConvertor.convertToDO(autoReloadConfig);
            int rows = autoReloadConfigDAO.insertAutoReloadConfig(autoReloadConfigDO);
            if (rows <= 0) {
                throw new RepositoryException("AutoReloadConfig insert failed");
            }
        } catch (RepositoryException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateAutoReloadConfig(UpdateAutoReloadConfigRequest request) {
        try {
            if (request == null) {
                return;
            }
            int rows = autoReloadConfigDAO.updateAutoReloadConfig(
                    Long.parseLong(request.getUserId()),
                    UUID.fromString(request.getCardId()),
                    request.getAutoReloadAmount(),
                    request.getThresholdAmount());
            if (rows < 0) {
                throw new RepositoryException("failed to update auto reload config");
            }
        } catch (RepositoryException e) {
            throw e;
        } catch (Exception e) {
            throw new RepositoryException("DB error during insert user auth", e);
        }
    }

    @Override
    public void toggleAutoReloadConfig(String userId, boolean isActive) {
        try {
            int rows = autoReloadConfigDAO.toggleAutoReloadConfig(Long.parseLong(userId), isActive);
            if (rows <= 0) {
                throw new RepositoryException("failed to toggle auto reload config");
            }
        } catch (RepositoryException e) {
            throw e;
        } catch (Exception e) {
            throw new RepositoryException("DB error during query user card detail list", e);
        }
    }

}