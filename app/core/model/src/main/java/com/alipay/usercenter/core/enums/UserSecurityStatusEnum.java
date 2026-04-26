package com.alipay.usercenter.core.enums;

public enum UserSecurityStatusEnum {
    /**
     * 临时锁定
     */
    PERMANENT_LOCK("PERMANENT_LOCK", "Permanently Locked"),

    /**
     * timeout lock
     */
    TIMEOUT_LOCK("TIMEOUT_LOCK", "Temporarily Locked"),

    /**
     * active
     */
    ENABLED("ENABLED", "Enabled Session")
    ;

    /**
     * 锁定类型
     */
    private String code;
    /**
     * 描述
     */
    private String description;


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    /**
     * 获取描述
     * @return
     */
    public String getDescription() {
        return description;
    }

    /**
     * 设置描述
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 构造方法
     * @param code
     * @param description
     */
    UserSecurityStatusEnum(String code, String description) {
        this.code = code;
        this.description = description;
    }
}
