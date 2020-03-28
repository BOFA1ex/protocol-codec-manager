package com.bofa.protocol.codec.util;

import com.github.benmanes.caffeine.cache.*;
import io.netty.channel.Channel;
import io.netty.util.AttributeKey;

import java.util.Collection;
import java.util.concurrent.locks.StampedLock;

/**
 * @author bofa1ex
 * @since  2020/1/15
 */
public class ChannelCodecContextUtils {

    private static final StampedLock lock = new StampedLock();

    private static final AttributeKey<Cache<String, Object>> CODEC_CONTEXT = AttributeKey.valueOf("CODEC_CONTEXT");

    /**
     * 注入变量
     *
     * @param key      变量名
     * @param obj      变量值
     * @param channel  通道
     */
    public static void setVariable(String key, Object obj, Channel channel) {
        _getCache(channel).put(key, obj);
    }

    /**
     * fetch variable
     *
     * @param key      变量名
     * @param channel  通道
     */
    public static Object getVariable(String key, Channel channel){
        return _getCache(channel).getIfPresent(key);
    }

    /**
     * fetch variables
     *
     * @param channel  通道
     */
    public static Collection<Object> getVariables(Channel channel){
        return _getCache(channel).asMap().values();
    }

    private static Cache<String, Object> _getCache(Channel channel) {
        long stamp = lock.readLock();
        Cache<String, Object> cache = channel.attr(CODEC_CONTEXT).get();
        try {
            while (cache == null) {
                long ws = lock.tryConvertToWriteLock(stamp);
                if (ws != 0L) {
                    stamp = ws;
                    cache = Caffeine.newBuilder()
                            .initialCapacity(2 << 5)
                            .maximumSize(2 << 8)
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
