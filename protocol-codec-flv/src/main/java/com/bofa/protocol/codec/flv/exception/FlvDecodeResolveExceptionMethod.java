package com.bofa.protocol.codec.flv.exception;

import com.bofa.protocol.codec.method.ResolveExceptionMethod;
import com.bofa.protocol.codec.util.ChannelCodecContextUtils;
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
        final Object flvFile = ChannelCodecContextUtils.getVariable(expr, channel);
        final List<FlvTag> flvTags = (List<FlvTag>) ChannelCodecContextUtils.getVariable("flvTags", channel);
        ((FlvFile) flvFile).setFlvTags(flvTags);
        return flvFile;
    }
}
