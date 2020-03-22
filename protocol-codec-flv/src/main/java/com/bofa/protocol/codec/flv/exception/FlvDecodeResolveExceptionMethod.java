package com.bofa.protocol.codec.flv.exception;

import com.bofa.codec.method.ResolveExceptionMethod;
import com.bofa.codec.util.ChannelSpelContextUtils;
import com.bofa.protocol.codec.flv.model.FlvFile;
import com.bofa.protocol.codec.flv.model.FlvTag;
import io.netty.channel.Channel;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author bofa1ex
 * @since 2020/2/15
 */
@Component
public class FlvDecodeResolveExceptionMethod implements ResolveExceptionMethod {

    public static final FlvDecodeResolveExceptionMethod INSTANCE = new FlvDecodeResolveExceptionMethod();

    @Override
    public Object resolveException(Class<?> resolveClazz, Exception e, Channel channel, String... parameters) {
        System.out.println(Arrays.stream(e.getStackTrace())
                .map(StackTraceElement::toString)
                .collect(Collectors.joining("\n")));
        final String expr = StringUtils.uncapitalize(resolveClazz.getSimpleName());
        final Object flvFile = ChannelSpelContextUtils.processExprAndGet("#" + expr + "", channel, resolveClazz);
        final List<FlvTag> flvTags = ChannelSpelContextUtils.processExprAndGet("#flvTags", channel, List.class);
        ((FlvFile) flvFile).setFlvTags(flvTags);
        return flvFile;
    }
}
