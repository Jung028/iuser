package com.alipay.usercenter.common.util;

import org.slf4j.MDC;

public class TraceContext {

    private static final String TRACE_ID = "traceId";
    private static final String USER_ID = "userId";

    public static void set(String traceId) {
        MDC.put(TRACE_ID, traceId);
    }

    public static String get() {
        return MDC.get(TRACE_ID);
    }

    public static void setUserId(String userId) {
        if (userId != null) {
            MDC.put(USER_ID, userId);
        }
    }

    public static String getUserId() {
        return MDC.get(USER_ID);
    }

    public static void clear() {
        MDC.clear();
    }
}