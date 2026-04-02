package com.alipay.usercenter.common.dal.auto.custom;

import com.alipay.usercenter.common.dal.auto.dataobject.UserCardDetailDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserCardDetailDAO {

    UserCardDetailDO queryUserCardDetail(@Param("userId") long userId);

    List<UserCardDetailDO> queryCardDetailList(@Param("userId") long userId);

    int insertNewCard(UserCardDetailDO userCardDetailDO);

    UserCardDetailDO queryDefaultCard(@Param("userId") long userId);
}
