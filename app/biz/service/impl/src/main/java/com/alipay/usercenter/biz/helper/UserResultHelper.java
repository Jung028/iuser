package com.alipay.usercenter.biz.helper;

import com.alipay.usercenter.common.service.facade.baseresult.UserBaseResult;
import com.alipay.usercenter.common.service.facade.enums.UserResultCode;

public class UserResultHelper {


    public static <R extends UserBaseResult> void fillExceptionResultCode(R result, UserResultCode userResultCode) {
        result.setResultCode(userResultCode.getCode());
        result.setResultMessage(userResultCode.getDescription());
        result.setSuccess(false);
    }

    public static <R extends UserBaseResult> void fillSuccessResultCode(R result) {
        result.setSuccess(true);
    }

}
