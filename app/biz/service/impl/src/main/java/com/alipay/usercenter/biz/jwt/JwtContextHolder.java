package com.alipay.usercenter.biz.jwt;

public class JwtContextHolder {
    private static final ThreadLocal<JwtClaims> CONTEXT = new ThreadLocal<>();

    public static JwtClaims get() {
        JwtClaims claims = CONTEXT.get();
        if (claims == null) {
            throw new IllegalArgumentException("JwtContextHolder has not been initialized!");
        }
        return claims;
    }
}
