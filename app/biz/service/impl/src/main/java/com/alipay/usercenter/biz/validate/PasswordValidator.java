package com.alipay.usercenter.biz.validate;

import com.alipay.usercenter.common.service.facade.enums.UserResultCode;
import com.alipay.usercenter.core.util.AssertUtil;

import java.util.regex.Pattern;

/**
 * @author adam
 * @date 6/3/2026 3:50 PM
 */
public class PasswordValidator {

    private static final Pattern UPPERCASE = Pattern.compile("[A-Z]");
    private static final Pattern LOWERCASE = Pattern.compile("[a-z]");
    private static final Pattern DIGIT = Pattern.compile("[0-9]");
    private static final Pattern SPECIAL = Pattern.compile("[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?]");

    /**
     * validate password meets specific criterion
     * @param password
     */
    public static void check(String password) {
        // Not null
        AssertUtil.notNull(password, UserResultCode.PARAM_ILLEGAL, "Password cannot be null");

        // Length check
        AssertUtil.isTrue(password.length() >= 6 && password.length() <= 20,
                UserResultCode.PARAM_ILLEGAL, "Password must be 6–20 characters long");

        // Uppercase
        AssertUtil.isTrue(UPPERCASE.matcher(password).find(),
                UserResultCode.PARAM_ILLEGAL, "Password must contain at least one uppercase letter");

        // Lowercase
        AssertUtil.isTrue(LOWERCASE.matcher(password).find(),
                UserResultCode.PARAM_ILLEGAL, "Password must contain at least one lowercase letter");

        // Digit
        AssertUtil.isTrue(DIGIT.matcher(password).find(),
                UserResultCode.PARAM_ILLEGAL, "Password must contain at least one digit");

        // Special character
        AssertUtil.isTrue(SPECIAL.matcher(password).find(),
                UserResultCode.PARAM_ILLEGAL, "Password must contain at least one special character");

        // No repeated consecutive characters
        char[] chars = password.toCharArray();
        for (int i = 1; i < chars.length; i++) {
            AssertUtil.isTrue(chars[i] != chars[i - 1],
                    UserResultCode.PARAM_ILLEGAL, "Password cannot have consecutive identical characters");
        }
    }
}