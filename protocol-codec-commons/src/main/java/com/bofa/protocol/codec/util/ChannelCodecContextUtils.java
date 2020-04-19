package com.bofa.protocol.codec.util;

import com.github.benmanes.caffeine.cache.*;
import io.netty.channel.Channel;
import io.netty.util.AttributeKey;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.StampedLock;

/**
 * @author bofa1ex
 * @since 2020/1/15
 */
public class ChannelCodecContextUtils {

    private static final StampedLock lock = new StampedLock();

    /* cache都设置了过期时间策略, 无需显式回收 */
    private static final Duration EXPIRE_AFTER_ACCESS_DURATION = Duration.ofMinutes(1);

    /* 解析运行所需的缓存容器 */
    private static final AttributeKey<Cache<String, Object>> CODEC_CONTEXT = AttributeKey.valueOf("CODEC_CONTEXT");

    /* 为了解决同个channel解析或编码过程中, key值并发冲突的情况, 因此引入时间戳 */
    private static final AttributeKey<Cache<Thread, String>> TIMESTAMP = AttributeKey.valueOf("TIME_STAMP");

    /**
     * 注入变量
     *
     * @param key     变量名
     * @param obj     变量值
     * @param channel 通道
     */
    public static void setVariable(String key, Object obj, Channel channel) {
        final String timestamp = ThreadLocalRandom.current().nextLong() + "";
        _getTimeStampCache(channel).put(Thread.currentThread(), timestamp);
        // key值加上时间戳, 避免冲突
        _getContextCache(channel).put(key + timestamp, obj);
    }

    /**
     * fetch variable
     *
     * @param key     变量名
     * @param channel 通道
     */
    public static Object getVariable(String key, Channel channel) {
        final String timestamp = _getTimeStampCache(channel).getIfPresent(Thread.currentThread());
        // key值加上时间戳, 避免冲突
        return _getContextCache(channel).getIfPresent(key + timestamp);
    }

    /**
     * fetch variables
     *
     * @param channel 通道
     */
    public static Collection<Object> getVariables(Channel channel) {
        return _getContextCache(channel).asMap().values();
    }

    private static Cache<Thread, String> _getTimeStampCache(Channel channel) {
        long stamp = lock.readLock();
        Cache<Thread, String> cache = channel.attr(TIMESTAMP).get();
        try {
            while (cache == null) {
                long ws = lock.tryConvertToWriteLock(stamp);
                if (ws != 0L) {
                    stamp = ws;
                    cache = Caffeine.newBuilder()
                            .initialCapacity(2 << 5)
                            // 1分钟内未访问该key, 则自动回收.
                            .expireAfterAccess(EXPIRE_AFTER_ACCESS_DURATION)
                            .maximumSize(2 << 7)
                            .build();
                    channel.attr(TIMESTAMP).set(cache);
                    break;
                } else {
                    lock.unlockRead(stamp);
                    stamp = lock.writeLock();
                }
            }
        } finally {
            lock.unlock(stamp);
        }
        return cache;
    }

    private static Cache<String, Object> _getContextCache(Channel channel) {
        long stamp = lock.readLock();
        Cache<String, Object> cache = channel.attr(CODEC_CONTEXT).get();
        try {
            while (cache == null) {
                long ws = lock.tryConvertToWriteLock(stamp);
                if (ws != 0L) {
                    stamp = ws;
                    cache = Caffeine.newBuilder()
                            .initialCapacity(2 << 5)
                            // 1分钟内未访问该key, 则自动回收.
                            .expireAfterAccess(EXPIRE_AFTER_ACCESS_DURATION)
                            .maximumSize(2 << 7)
                            .build();
                    channel.attr(CODEC_CONTEXT).set(cache);
                    break;
                } else {
                    lock.unlockRead(stamp);
                    stamp = lock.writeLock();
                }
            }
        } finally {
            lock.unlock(stamp);
        }
        return cache;
    }
}
