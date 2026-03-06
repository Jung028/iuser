package com.alipay.usercenter.common.service.facade.enums;

import static com.alipay.usercenter.common.service.facade.enums.ErrorCodeConstants.PREFIX;

public enum UserResultCode {

    // --------------------------------Common Errors--------------------------------- //

    /**
     * 成功
     */
    EXECUTE_SUCCESS(GlobalResultCodes.EXECUTE_SUCCESS, "Success"),
    /**
     * 系统异常
     */
    SYSTEM_EXCEPTION(GlobalResultCodes.SYSTEM_EXCEPTION, "System Exception"),
    /**
     * 参数非法
     */
    PARAM_ILLEGAL(GlobalResultCodes.PARAM_ILLEGAL, "Parameter Illegal"),
    /**
     * 重复提交
     */
    REPEATED_SUBMIT(GlobalResultCodes.REPEATED_SUBMIT, "Repeated Submit"),


    // ------------------------------------User Biz Errors----------------------------- //
    /**
     * 用户已存在
     */
    EXISTING_USER(ResultCodeLevel.WARN, ResultCodeType.BIZ_ERROR, UserBizType.USER_COMMON, "01", "Existing User"),
    /**
     * 用户不存在
     */
    ACCOUNT_LOCKED(ResultCodeLevel.WARN, ResultCodeType.BIZ_ERROR, UserBizType.USER_COMMON, "02", "Account Locked"),
    /**
     * 用户锁定
     */
    TIMEOUT_LOCK(ResultCodeLevel.WARN, ResultCodeType.BIZ_ERROR, UserBizType.USER_COMMON, "03", "Timeout Lock"),
    /**
     * OTP过期
     */
    OTP_EXPIRED(ResultCodeLevel.WARN, ResultCodeType.BIZ_ERROR, UserBizType.USER_COMMON, "04", "OTP Expired"),
    /**
     * OTP无效
     */
    OTP_INVALID(ResultCodeLevel.WARN, ResultCodeType.BIZ_ERROR, UserBizType.USER_COMMON, "05", "OTP invalid"),
    /**
     * OTP验证通过的Token过期
     */
    OTP_VERIFIED_TOKEN_EXPIRED(ResultCodeLevel.WARN, ResultCodeType.BIZ_ERROR, UserBizType.USER_COMMON, "06", "OTP Verified Token Expired"),
    /**
     * OTP验证通过的Token无效
     */
    OTP_VERIFIED_TOKEN_INVALID(ResultCodeLevel.WARN, ResultCodeType.BIZ_ERROR, UserBizType.USER_COMMON, "07", "OTP Verified Token Invalid"),
    /**
     * 注册时手机号不匹配
     */
    PHONE_NO_MISMATCH(ResultCodeLevel.WARN, ResultCodeType.BIZ_ERROR, UserBizType.USER_COMMON,"08", "Phone number mismatch during registration" ),
    /**
     * 用户未找到
     */
    USER_NOT_FOUND(ResultCodeLevel.WARN, ResultCodeType.BIZ_ERROR, UserBizType.USER_COMMON, "09", "User Not Found"),
    /**
     * no permission
     */
    NO_PERMISSION(ResultCodeLevel.WARN, ResultCodeType.BIZ_ERROR, UserBizType.USER_COMMON, "10", "No Permission" ),
    /**
     * OTP scene mismatch
     */
    OTP_SCENE_MISMATCH(ResultCodeLevel.ERROR, ResultCodeType.SYS_ERROR, UserBizType.OTP_COMMON, "11", "Cannot verify otp that is not for its intended purpose" ),
    /**
     * Password Mismatch
     */
    PASSWORD_MISMATCH(ResultCodeLevel.WARN, ResultCodeType.BIZ_ERROR, UserBizType.USER_COMMON, "12", "Password Mismatch");
    /**
     * code 码
     */
    private final String code;
    /**
     * 描述信息
     */
    private final String description;

    /**
     * 获取code码
     * @return
     */
    public String getCode() {
        return code;
    }
    /**
     * 获取描述信息
     * @return
     */
    public String getDescription() {
        return description;
    }

    /**
     * 构造函数
     * @param code
     * @param description
     */
    UserResultCode(String code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
     * 构造函数
     * @param level
     * @param bizError
     * @param bizType
     * @param errorSpecific
     * @param description
     */
    UserResultCode(String level, String bizError, String bizType, String errorSpecific, String description) {
        this.code = PREFIX + level + bizError + SystemCode.I_SLIPCORE + bizType + errorSpecific;
        this.description = description;
    }

}
