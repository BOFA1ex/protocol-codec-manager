package com.bofa.protocol.codec.method;

import io.netty.channel.Channel;

/**
 * @author bofa1ex
 * @since 2020/2/15
 */
public interface ResolveExceptionMethod {

    Object resolveException(Class<?> resolveClazz, Exception e, Channel channel, String... parameters);

}
