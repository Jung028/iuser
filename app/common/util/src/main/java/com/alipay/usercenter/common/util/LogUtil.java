package com.alipay.usercenter.common.util;

import com.alipay.usercenter.common.service.facade.baseresult.UserBaseRequest;
import com.alipay.usercenter.common.service.facade.baseresult.UserBaseResult;
import com.alipay.usercenter.common.service.facade.enums.UserResultCode;
import com.alipay.usercenter.core.exception.UserException;
import org.slf4j.Logger;

public class LogUtil {
    public static final String SEPARATOR = ",";
    public static final char RIGHT_TAG = ']';
    public static final char LEFT_TAG = '[';

    public static void info(Logger logger, Object msg) {
        if(logger.isInfoEnabled()) {
            logger.info(msg.toString());
        }
    }
    public static void warn(Logger logger, Object msg) {
        if(logger.isWarnEnabled()) {
            logger.warn(msg.toString());
        }
    }
    public static void error(Logger logger, Object msg) {
        if (logger.isErrorEnabled()) {
            logger.error(msg.toString());
        }
    }

    public static <T extends UserBaseRequest> void warn(Logger logger, UserException e,
                                                        String s, T request, String s1, String s2,
                                                        UserResultCode resultCode, String s3,
                                                        String message) {
        if (logger.isWarnEnabled()) {
            StringBuilder logBuilder = new StringBuilder();
            logBuilder.append(s)
                    .append(LEFT_TAG)
                    .append(request)
                    .append(RIGHT_TAG)
                    .append(s1)
                    .append(resultCode)
                    .append(s3)
                    .append(message);
            logger.warn(logBuilder.toString(), e);
        }
    }

    public static <R extends UserBaseResult, T extends UserBaseRequest> void info(Logger logger, String s, R result, String s1, T request, String s2) {
        if (logger.isInfoEnabled()) {
            StringBuilder logBuilder = new StringBuilder();
            logBuilder.append(s)
                    .append(LEFT_TAG)
                    .append(result)
                    .append(RIGHT_TAG)
                    .append(s1)
                    .append(LEFT_TAG)
                    .append(request)
                    .append(RIGHT_TAG);
            logger.info(logBuilder.toString());
        }
    }

    public static <T extends UserBaseRequest> void error(Logger logger, Throwable e, String s, T request, String s1) {
        if (logger.isErrorEnabled()) {
            StringBuilder logBuilder = new StringBuilder();
            logBuilder.append(s)
                    .append(LEFT_TAG)
                    .append(request)
                    .append(RIGHT_TAG);
            logger.error(logBuilder.toString(), e);
        }
    }

    public static <T extends UserBaseRequest> void info(Logger logger, String s, T request, String s1) {
        if (logger.isInfoEnabled()) {
            StringBuilder logBuilder = new StringBuilder();
            logBuilder.append(s)
                    .append(LEFT_TAG)
                    .append(request)
                    .append(RIGHT_TAG);
            logger.info(logBuilder.toString());
        }
    }

    public static void info(Logger logger, String s, Long userId, String string) {
        if (logger.isInfoEnabled()) {
            StringBuilder logBuilder = new StringBuilder();
            logBuilder.append(s)
                    .append(LEFT_TAG)
                    .append(userId)
                    .append(RIGHT_TAG)
                    .append(string);
            logger.info(logBuilder.toString());
        }
    }

    public static void info(Logger logger, String s, String phoneNo, String string) {
        if (logger.isInfoEnabled()) {
            String logBuilder = s +
                    LEFT_TAG +
                    phoneNo +
                    RIGHT_TAG +
                    string;
            logger.info(logBuilder);
        }
    }

    public static void error(Logger logger, UserResultCode userResultCode, String failedToSendOtp) {
        if (logger.isInfoEnabled()) {
            StringBuilder logBuilder = new StringBuilder();
            logBuilder.append(userResultCode)
                    .append(LEFT_TAG)
                    .append(failedToSendOtp)
                    .append(RIGHT_TAG);
            logger.info(logBuilder.toString());
        }
    }
}
