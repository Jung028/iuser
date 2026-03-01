package com.alipay.usercenter.biz.cache.impl;

import com.alipay.usercenter.biz.cache.UserSecurityCache;
import com.alipay.usercenter.common.service.facade.request.QueryUserSecurityRequest;
import com.alipay.usercenter.core.model.UserSecurity;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class UserSecurityCacheImpl implements UserSecurityCache {

    @Resource
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
            init.setLockedUntil(Instant.now());  // default to past so NPE never occurs

            update(init);
            return init;
        }

        String status = (String) ops.get(key, "status");

        // Redis might store as Integer or Long
        Object failedObj = ops.get(key, "failedAttempts");
        int failedAttempts = failedObj == null ? 0 : ((Number) failedObj).intValue();

        Object lockedObj = ops.get(key, "lockedUntil");
        Instant lockedUntil = lockedObj == null
                ? Instant.EPOCH
                : Instant.ofEpochMilli(((Number) lockedObj).longValue());

        UserSecurity security = new UserSecurity();
        security.setUserId(userId);
        security.setStatus(status);
        security.setFailedAttempts(failedAttempts);
        security.setLockedUntil(lockedUntil);
        return security;
    }

    @Override
    public void update(UserSecurity security) {
        String key = KEY_PREFIX + security.getUserId();

        Map<String, Object> map = new HashMap<>();
        map.put("status", security.getStatus());
        map.put("failedAttempts", security.getFailedAttempts());
        // store lockedUntil as epoch millis
        map.put("lockedUntil", security.getLockedUntil() != null
                ? security.getLockedUntil().toEpochMilli()
                : Instant.EPOCH.toEpochMilli());


        redisTemplate.opsForHash().putAll(key, map);
    }

}

