package com.bofa.protocol.codec.method.convert;


import com.bofa.protocol.codec.method.ConvertMethod;
import com.bofa.protocol.codec.util.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import org.springframework.stereotype.Component;

/**
 * @author bofa1ex
 * @since 2020/1/26
 */
@Component
public class HexConvertMethod implements ConvertMethod<String> {

    @Override
    public String decode(ByteBuf buffer, Channel channel, String... parameters) {
        return ByteBufUtils.buffer2Hex(buffer);
    }

    @Override
    public ByteBuf encode(String s, int capacity, Channel channel, String... parameters) {
        return ByteBufUtils.hex2Buffer(s, capacity);
    }
}
