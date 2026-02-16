package com.alipay.usercenter.common.dal.auto.custom;

import com.alipay.usercenter.common.dal.auto.dataobject.UserInfoDetailDO;

public interface UserInfoDetailDAO {
    UserInfoDetailDO queryUserInfoDetail(String userId);
}
