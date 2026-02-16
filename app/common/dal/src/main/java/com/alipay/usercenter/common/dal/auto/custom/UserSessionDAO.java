package com.alipay.usercenter.common.dal.auto.custom;

import com.alipay.usercenter.common.dal.auto.dataobject.UserSessionDO;

public interface UserSessionDAO {
    UserSessionDO queryUserSession(String sessionId);
}
