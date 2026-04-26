package com.alipay.usercenter.core.converter;

import com.alipay.usercenter.common.dal.auto.dataobject.AutoReloadConfigDO;
import com.alipay.usercenter.common.service.facade.item.AutoReloadConfigItem;
import com.alipay.usercenter.core.model.AutoReloadConfig;

/**
 * @author adam
 * @date 26/3/2026 5:04 PM
 */
public class AutoReloadConfigConvertor {

    /**
     * convert to item
     * @param autoReloadConfig
     * @return
     */
    public static AutoReloadConfigItem convertToItem(AutoReloadConfig autoReloadConfig) {
        AutoReloadConfigItem autoReloadConfigItem = new AutoReloadConfigItem();
        autoReloadConfigItem.setConfigId(autoReloadConfig.getConfigId());
        autoReloadConfigItem.setReloadAmount(autoReloadConfig.getReloadAmount());
        autoReloadConfigItem.setThresholdAmount(autoReloadConfig.getThresholdAmount());
        autoReloadConfigItem.setConfigId(autoReloadConfig.getConfigId());
        autoReloadConfigItem.setGmtCreate(autoReloadConfig.getGmtCreate());
        autoReloadConfigItem.setGmtModified(autoReloadConfig.getGmtModified());
        autoReloadConfigItem.setReloadAmount(autoReloadConfig.getReloadAmount());
        autoReloadConfigItem.setIsReloadInFlight(autoReloadConfig.getIsReloadInFlight());
        autoReloadConfigItem.setIsActive(autoReloadConfig.getIsActive());
        autoReloadConfigItem.setUserCardId(autoReloadConfig.getUserCardId());
        autoReloadConfigItem.setLastTriggeredAt(autoReloadConfig.getLastTriggeredAt());
        return autoReloadConfigItem;
    }

    /**
     * convert to domain
     * @param autoReloadConfigDO
     * @return
     */
    public static AutoReloadConfig convertToDomain(AutoReloadConfigDO autoReloadConfigDO) {
        if (autoReloadConfigDO == null) {
            return null;
        }
        AutoReloadConfig autoReloadConfig = new AutoReloadConfig();
        autoReloadConfig.setConfigId(autoReloadConfigDO.getConfigId());
        autoReloadConfig.setGmtCreate(autoReloadConfigDO.getGmtCreate());
        autoReloadConfig.setGmtModified(autoReloadConfigDO.getGmtModified());
        autoReloadConfig.setReloadAmount(autoReloadConfigDO.getReloadAmount());
        autoReloadConfig.setIsReloadInFlight(autoReloadConfigDO.getIsReloadInFlight());
        autoReloadConfig.setThresholdAmount(autoReloadConfigDO.getThresholdAmount());
        autoReloadConfig.setIsActive(autoReloadConfigDO.getIsActive());
        autoReloadConfig.setUserCardId(autoReloadConfigDO.getUserCardId());
        autoReloadConfig.setLastTriggeredAt(autoReloadConfigDO.getLastTriggeredAt());
        return autoReloadConfig;
    }

    public static AutoReloadConfigDO convertToDO(AutoReloadConfig autoReloadConfig) {
        return null;
    }
}