package com.alipay.usercenter.common.dal.auto.custom;

import com.alipay.usercenter.common.dal.auto.dataobject.UserInfoDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserInfoDAO {
    // Custom query selecting specific columns
    UserInfoDO queryUserInfo(@Param("phoneNo") String phoneNo);


    void insertUserInfo(UserInfoDO userInfo);
}
