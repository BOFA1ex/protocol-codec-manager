package com.bofa.codec.method;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;

import java.io.Serializable;

/**
 * @author bofa1ex
 * @since 2020/1/31
 */
public interface ConvertMethod<T> extends Serializable {

    T decode(ByteBuf buffer, Channel channel, String... parameters);

    ByteBuf encode(T t, int capacity, Channel channel, String... parameters);
}
