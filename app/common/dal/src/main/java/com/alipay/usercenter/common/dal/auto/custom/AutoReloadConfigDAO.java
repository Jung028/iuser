package com.alipay.usercenter.common.dal.auto.custom;

import com.alipay.usercenter.common.dal.auto.dataobject.UserAuthDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AutoReloadConfigDAO {
    // Custom query selecting specific columns
    UserAuthDO queryUserAuth(@Param("userId") String userId);

    int updateAutoReloadConfig(@Param("userId") String userId,
                                @Param("cardId") String cardId,
                                @Param("autoReloadAmount") Integer autoReloadAmount,
                                @Param("thresholdAmount") Integer thresholdAmount);
}
