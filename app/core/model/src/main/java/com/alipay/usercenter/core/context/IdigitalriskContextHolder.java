package com.alipay.usercenter.core.context;

import com.alipay.usercenter.core.enums.IdigitalriskActionTypeEnum;
import java.util.Date;

public final class IdigitalriskContextHolder {

    private final static ThreadLocal<IdigitalriskContext> contextLocal = new ThreadLocal<>();

    public static void set(IdigitalriskContext context){
        contextLocal.set(context);
    }

    public static void set(IdigitalriskActionTypeEnum action, Date time, String operatorId, String operatorName) {
        set(new IdigitalriskContext(action, time, operatorId, operatorName));
    }

    public static void clear() {
        contextLocal.remove();
    }
}
