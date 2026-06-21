package com.alipay.usercenter.core.util;

import com.alipay.usercenter.common.service.facade.enums.UserResultEnum;
import com.alipay.usercenter.common.service.facade.enums.UserResultCode;
import com.alipay.usercenter.core.model.UserInfo;
import com.alipay.usercenter.core.enums.BusinessTypeEnum;
import com.alipay.usercenter.core.exception.BaseSlipException;
import com.alipay.usercenter.core.exception.UserException;
import io.micrometer.common.util.StringUtils;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

public class AssertUtil {

    public static void notNull(final Object object, final UserResultEnum userResultEnum, final String resultMsg) {
        check(new AssertTemplate() {
            @Override
            public void doAssert() {
                Assert.notNull(object, "resultMsg");
            }
        }, userResultEnum, resultMsg);
    }

    public static void notNull(final Object object, final UserResultCode userResultCode, final String resultMsg) {
        check(new AssertTemplate() {
            @Override
            public void doAssert() {
                Assert.notNull(object, "resultMsg");
            }
        }, userResultCode, resultMsg);
    }

    public static void notBlank(final String str, final UserResultCode resultCode,
                                final String resultMsg) {
        check(() -> Assert.isTrue(StringUtils.isNotBlank(str),"is true"), resultCode, resultMsg);
    }

    public static void isTrue(final boolean expression, final UserResultCode resultCode,
                              final String resultMsg) {
        check(() -> Assert.isTrue(expression,"is true"), resultCode, resultMsg);
    }

    public static void equals(BusinessTypeEnum businessType, BusinessTypeEnum businessTypeEnum, UserResultCode userResultCode, String businessTypeMustBeChargeback) {
        check(() -> Assert.isTrue(ObjectUtils.nullSafeEquals(businessType, businessTypeEnum), businessTypeMustBeChargeback), userResultCode, businessTypeMustBeChargeback);
    }

    public static void isNull(UserInfo userInfo, UserResultCode userResultCode, String userAlreadyExists) {
        check(() -> Assert.isNull(userInfo, userAlreadyExists), userResultCode, userAlreadyExists);
    }

    public static void isNull(final Object object, final UserResultCode userResultCode, final String resultMsg) {
        check(() -> Assert.isNull(object, resultMsg), userResultCode, resultMsg);
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

    private static void check(AssertTemplate assertTemplate, UserResultCode userResultCode, String resultMsg) {
        try {
            assertTemplate.doAssert();
        } catch (IllegalArgumentException e) {
            if (StringUtils.isBlank(resultMsg)) {
                throw new UserException(userResultCode);
            } else {
                throw new BaseSlipException(userResultCode, resultMsg);
            }
        }
    }
}
