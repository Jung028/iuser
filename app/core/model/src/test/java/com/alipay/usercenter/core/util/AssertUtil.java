package com.alipay.usercenter.core.util;

import com.alipay.usercenter.common.service.facade.enums.UserResultEnum;
import com.alipay.usercenter.core.exception.BaseSlipException;
import io.micrometer.common.util.StringUtils;
import org.springframework.util.Assert;

public class AssertUtil {

    public static void notNull(final Object object, final UserResultEnum userResultEnum, final String resultMsg) {
        check(new AssertTemplate() {
            @Override
            public void doAssert() {
                Assert.notNull(object, "resultMsg");
            }
        }, userResultEnum, resultMsg);
    }
    public static interface AssertTemplate {
        public void doAssert();
    }
    private static void check(AssertTemplate assertTemplate, UserResultEnum userResultEnum, String resultMsg) {
        try {
            assertTemplate.doAssert();
        } catch (IllegalArgumentException e) {
            if (StringUtils.isBlank(resultMsg)) {
                throw new BaseSlipException(userResultEnum);
            } else {
                throw new BaseSlipException(userResultEnum, resultMsg);
            }
        }
    }
}
