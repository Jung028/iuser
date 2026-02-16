package com.alipay.usercenter.biz.util;

import org.springframework.security.crypto.bcrypt.BCrypt;

/**
 * User password utility class
 *
 * @author adam
 * @version $Id: UserPasswordUtil.java, v 0.1 2026-01-01 10:00 AM adam Exp $
 */
public class UserPasswordUtil {

    /**
     * verify password
     * @param password
     * @param hashedUserPassword
     * @return
     */
    public static boolean verifyPassword(String password, String hashedUserPassword) {
        if (password == null || hashedUserPassword == null) {
            return false;
        }
        try {
            return BCrypt.checkpw(password, hashedUserPassword);
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    /**
     * hash password
     * @param password
     * @return
     */
    public static String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }
}
