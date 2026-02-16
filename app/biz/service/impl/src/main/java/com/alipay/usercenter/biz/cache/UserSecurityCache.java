package com.alipay.usercenter.biz.cache;

import com.alipay.usercenter.common.service.facade.request.QueryUserSecurityRequest;
import com.alipay.usercenter.core.model.UserSecurity;

public interface UserSecurityCache {

    /**
     * Query user security information from cache
     *
     * @param request the query request
     * @return the user security information result
     */
    UserSecurity queryUserSecurity(QueryUserSecurityRequest request);

    void update(UserSecurity userSecurity);
}
