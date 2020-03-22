package com.bofa.codec.method.convert;


import com.bofa.codec.method.ConvertMethod;
import com.bofa.codec.util.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import org.springframework.stereotype.Component;

/**
 * @author bofa1ex
 * @since 2020/1/26
 */
@Component
public class AsciiConvertMethod implements ConvertMethod<String> {

    public static final AsciiConvertMethod INSTANCE = new AsciiConvertMethod();

    @Override
    public String decode(ByteBuf buffer, Channel channel, String... parameters) {
        return buffer != null ? ByteBufUtils.buffer2Ascii(buffer) : "";
    }

    @Override
    public ByteBuf encode(String s, int capacity, Channel channel, String... parameters) {
        return ByteBufUtils.ascii2Buffer(s, capacity);
    }
}
