package com.bofa.codec.method.convert.flv;

import com.bofa.codec.method.ConvertMethod;
import com.bofa.codec.method.convert.IntegerConvertMethod;
import com.bofa.codec.util.ChannelSpelContextUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import org.springframework.stereotype.Component;

/**
 * @author bofa1ex
 * @since 2020/1/31
 */
@Component
public class FlvTimeStampExtensionConvertMethod implements ConvertMethod<Integer> {

    public static final FlvTimeStampExtensionConvertMethod INSTANCE = new FlvTimeStampExtensionConvertMethod();

    @Override
    public Integer decode(ByteBuf buffer, Channel channel, String... parameters) {
        return ChannelSpelContextUtils.processExprAndGet("#flvTag.timeStamp", channel, int.class)
        | IntegerConvertMethod.INSTANCE.decode(buffer, channel, parameters) << 24;
    }

    @Override
    public ByteBuf encode(Integer value, int capacity, Channel channel, String... parameters) {
        return IntegerConvertMethod.INSTANCE.encode(value >> 24, capacity, channel, parameters);
    }
}
