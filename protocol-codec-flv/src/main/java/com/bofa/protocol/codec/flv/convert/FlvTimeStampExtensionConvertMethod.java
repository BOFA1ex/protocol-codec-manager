package com.bofa.protocol.codec.flv.convert;

import com.bofa.protocol.codec.flv.model.FlvTag;
import com.bofa.protocol.codec.method.ConvertMethod;
import com.bofa.protocol.codec.method.convert.IntegerConvertMethod;
import com.bofa.protocol.codec.util.ChannelCodecContextUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import org.springframework.stereotype.Component;

/**
 * @author bofa1ex
 * @since 2020/1/31
 */
@Component
public class FlvTimeStampExtensionConvertMethod implements ConvertMethod<Integer> {

    @Override
    public Integer decode(ByteBuf buffer, Channel channel, String... parameters) {
        return ((FlvTag) ChannelCodecContextUtils.getVariable("flvTag", channel)).getTimeStamp()
                | IntegerConvertMethod.INSTANCE.decode(buffer, channel, parameters) << 24;
    }

    @Override
    public ByteBuf encode(Integer value, int capacity, Channel channel, String... parameters) {
        return IntegerConvertMethod.INSTANCE.encode(value >> 24, capacity, channel, parameters);
    }
}
