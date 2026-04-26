package com.alipay.usercenter.common.dal.auto.custom;

import com.alipay.usercenter.common.dal.auto.dataobject.AutoReloadConfigDO;
import com.alipay.usercenter.common.dal.auto.dataobject.UserAuthDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.UUID;

@Mapper
public interface AutoReloadConfigDAO {
    // Custom query selecting specific columns
    UserAuthDO queryUserAuth(@Param("userId") String userId);

    int updateAutoReloadConfig(@Param("userId") long userId,
                                @Param("cardId") UUID cardId,
                                @Param("autoReloadAmount") Integer autoReloadAmount,
                                @Param("thresholdAmount") Integer thresholdAmount);

    AutoReloadConfigDO queryAutoReloadConfig(@Param("userId") long userId);

    int toggleAutoReloadConfig(@Param("userId") long userId, @Param("isActive") boolean isActive);

    int insertAutoReloadConfig(AutoReloadConfigDO autoReloadConfigDO);
}
