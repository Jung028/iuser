package com.alipay.usercenter.common.dal.auto.custom;

import com.alipay.usercenter.common.dal.auto.dataobject.UserAuthDO;
import com.alipay.usercenter.common.dal.auto.dataobject.UserInfoDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserAuthDAO {
    // Custom query selecting specific columns
    UserAuthDO queryUserAuth(@Param("userId") String userId, @Param("authType") String authType);

    int insertUserAuth(UserAuthDO userAuthDO);

    void updateUserAuthPassword(@Param("userId") String userId, @Param("hashedPassword") String hashedPassword);
}
