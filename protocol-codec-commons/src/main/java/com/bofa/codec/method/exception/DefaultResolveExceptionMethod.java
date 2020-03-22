package com.bofa.codec.method.exception;

import com.bofa.codec.method.ResolveExceptionMethod;
import com.bofa.codec.util.ChannelSpelContextUtils;
import io.netty.channel.Channel;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * @author bofa1ex
 * @since 2020/2/15
 */
@Component
public class DefaultResolveExceptionMethod implements ResolveExceptionMethod {

    public static final DefaultResolveExceptionMethod INSTANCE = new DefaultResolveExceptionMethod();

    @Override
    public Object resolveException(Class<?> resolveClazz, Exception e, Channel channel, String... parameters) {
        System.out.println(e.getMessage());
        final String expr = StringUtils.uncapitalize(resolveClazz.getSimpleName());
        return ChannelSpelContextUtils.processExprAndGet("#" + expr + "", channel, resolveClazz);
    }
}
