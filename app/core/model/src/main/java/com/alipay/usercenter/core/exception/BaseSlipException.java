package com.alipay.usercenter.core.exception;

import com.alipay.usercenter.common.service.facade.enums.SlipResultEnum;
import com.alipay.usercenter.common.service.facade.enums.UserResultCode;

public class BaseSlipException extends RuntimeException {

    public BaseSlipException(SlipResultEnum slipResultEnum) {
        super(slipResultEnum.getResultMsg());

    }

    public BaseSlipException(SlipResultEnum slipResultEnum, String resultMsg) {
        super(slipResultEnum.getResultMsg() + ":" + resultMsg);
    }

    public BaseSlipException(UserResultCode userResultCode, String resultMsg) {
        super(userResultCode.getDescription() + ":" + resultMsg);
    }
}
