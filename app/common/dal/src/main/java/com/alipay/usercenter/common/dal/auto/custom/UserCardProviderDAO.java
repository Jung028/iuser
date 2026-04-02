package com.alipay.usercenter.common.dal.auto.custom;

import com.alipay.usercenter.common.dal.auto.dataobject.UserCardDetailDO;
import com.alipay.usercenter.common.dal.auto.dataobject.UserCardProviderDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserCardProviderDAO {

    UserCardProviderDO findByUserIdAndProvider(@Param("userId") long userId, @Param("provider") String provider);
}
