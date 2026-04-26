package com.alipay.usercenter.core.exception;

import com.alipay.usercenter.common.service.facade.enums.UserResultEnum;
import com.alipay.usercenter.common.service.facade.enums.UserResultCode;

public class BaseSlipException extends RuntimeException {

    public BaseSlipException(UserResultEnum userResultEnum) {
        super(userResultEnum.getResultMsg());

    }

    public BaseSlipException(UserResultEnum userResultEnum, String resultMsg) {
        super(userResultEnum.getResultMsg() + ":" + resultMsg);
    }

    public BaseSlipException(UserResultCode userResultCode, String resultMsg) {
        super(userResultCode.getDescription() + ":" + resultMsg);
    }
}
