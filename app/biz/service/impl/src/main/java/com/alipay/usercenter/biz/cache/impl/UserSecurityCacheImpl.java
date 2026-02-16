package com.alipay.usercenter.biz.cache.impl;

import com.alipay.usercenter.biz.cache.UserSecurityCache;
import com.alipay.usercenter.common.service.facade.request.QueryUserSecurityRequest;
import com.alipay.usercenter.core.model.UserSecurity;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class UserSecurityCacheImpl implements UserSecurityCache {

    @ Resource
    private RedisTemplate<String, Object> redisTemplate;

    private static final String KEY_PREFIX = "user:security:";

    @Override
    public UserSecurity queryUserSecurity(QueryUserSecurityRequest request) {
        Long userId = request.getUserId();
        String key = KEY_PREFIX + userId;

        HashOperations<String, String, Object> ops = redisTemplate.opsForHash();

        // initialise if not exists, save to redis and return
        if (!redisTemplate.hasKey(key)) {
            UserSecurity init = UserSecurity.newUser(userId);
            update(init);
            return init;
        }

        // if existing fetch from redis user security info
        String status = (String) ops.get(key, "status");
        Integer failedAttempts = (Integer) ops.get(key, "failedAttempts");
        Long lockedUntil = (Long) ops.get(key, "lockedUntil");

        UserSecurity security = new UserSecurity();
        security.setUserId(userId);
        security.setStatus(status);
        security.setFailedAttempts(failedAttempts == null ? 0 : failedAttempts);
        security.setLockedUntil(lockedUntil == null ? null : new Date(lockedUntil).toInstant());

        return security;
    }

    @Override
    public void update(UserSecurity security) {
        String key = KEY_PREFIX + security.getUserId();

        Map<String, Object> map = new HashMap<>();
        map.put("status", security.getStatus());
        map.put("failedAttempts", security.getFailedAttempts());
        map.put("lockedUntil",
                security.getLockedUntil() == null
                        ? null
                        : security.getLockedUntil());

        redisTemplate.opsForHash().putAll(key, map);
    }

}

