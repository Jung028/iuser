package com.alipay.usercenter.biz.helper;

/**
 * @author adam
 * @date 6/3/2026 3:18 PM
 */
public class GenerateUserId {

    private static final long CUSTOM_EPOCH = 1704067200000L; // 2024-01-01
    private static final int SEQUENCE_BITS = 12; // 0-4095 per millisecond

    private static long lastTimestamp = -1L;
    private static long sequence = 0L;
    private static final long MAX_SEQUENCE = ~(-1L << SEQUENCE_BITS);

    public static synchronized long nextId() {
        long timestamp = System.currentTimeMillis();

        if (timestamp < lastTimestamp) {
            throw new RuntimeException("Clock moved backwards!");
        }

        if (timestamp == lastTimestamp) {
            sequence = (sequence + 1) & MAX_SEQUENCE;
            if (sequence == 0) {
                // wait until next millisecond
                while (timestamp <= lastTimestamp) {
                    timestamp = System.currentTimeMillis();
                }
            }
        } else {
            sequence = 0;
        }

        lastTimestamp = timestamp;

        return ((timestamp - CUSTOM_EPOCH) << SEQUENCE_BITS) | sequence;
    }
}