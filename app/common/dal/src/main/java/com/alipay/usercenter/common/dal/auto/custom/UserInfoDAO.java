package com.alipay.usercenter.common.dal.auto.custom;

import com.alipay.usercenter.common.dal.auto.dataobject.UserInfoDO;

public interface UserInfoDAO {
    // Custom query selecting specific columns
    UserInfoDO queryUserInfo(String phoneNo);


    void insertUserInfo(UserInfoDO userInfo);
}
