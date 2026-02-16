package com.alipay.usercenter.biz.template;

import com.alipay.usercenter.common.service.facade.baseresult.UserBaseRequest;
import com.alipay.usercenter.common.service.facade.baseresult.UserBaseResult;

public abstract class UserBizCallback<T extends UserBaseRequest, R extends UserBaseResult>{
    /**
     * define the default response object
     */
    protected abstract R createDefaultResponse();

    /**
     * check params
     */
    protected abstract void checkParams(T request);

    /**
     * execute
     */
    protected abstract void process(T request, R response);



}
