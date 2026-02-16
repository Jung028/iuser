package com.alipay.usercenter.biz.helper;

import com.alipay.usercenter.common.service.facade.baseresult.UserBaseResult;
import com.alipay.usercenter.common.service.facade.enums.UserResultCode;

public class UserResultHelper {


    public static <R extends UserBaseResult> void fillExceptionResultCode(R result, UserResultCode userResultCode) {
    }

    public static <R extends UserBaseResult> void fillSuccessResultCode(R result) {
    }
}
