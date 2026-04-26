package com.alipay.usercenter.common.dal.auto.custom;

import com.alipay.usercenter.common.dal.auto.dataobject.UserCardDetailDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.UUID;

@Mapper
public interface UserCardDetailDAO {

    UserCardDetailDO queryUserCardDetail(@Param("userId") long userId);

    List<UserCardDetailDO> queryCardDetailList(@Param("userId") long userId);

    int insertNewCard(UserCardDetailDO userCardDetailDO);

    UserCardDetailDO queryDefaultCard(@Param("userCardId") UUID userCardId);

    UserCardDetailDO findByProviderToken(@Param("providerToken") String providerToken,
                                         @Param("provider") String provider);
}
