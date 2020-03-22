package com.bofa.codec.method.convert;


import com.bofa.codec.method.ConvertMethod;
import com.bofa.codec.util.ByteBufUtils;
import com.google.common.base.Strings;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import org.springframework.stereotype.Component;

/**
 * @author bofa1ex
 * @since 2020/1/26
 */
@Component
public class IntegerConvertMethod implements ConvertMethod<Integer> {

    public static final IntegerConvertMethod INSTANCE = new IntegerConvertMethod();

    @Override
    public Integer decode(ByteBuf buffer, Channel channel, String... parameters) {
        final String hex = HexConvertMethod.INSTANCE.decode(buffer, channel, parameters);
        return Integer.parseInt("".equals(hex) ? "0" : hex, 16);
    }

    @Override
    public ByteBuf encode(Integer value, int capacity, Channel channel, String... parameters) {
        final String hex = Strings.padStart(Integer.toHexString(value), capacity * 2, '0');
        return ByteBufUtils.hex2Buffer(hex, capacity);
    }
}
