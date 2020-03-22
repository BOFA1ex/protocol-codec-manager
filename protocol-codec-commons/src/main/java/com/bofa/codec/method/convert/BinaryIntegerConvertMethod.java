package com.bofa.codec.method.convert;

import com.bofa.codec.method.ConvertMethod;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import org.springframework.stereotype.Component;

/**
 * @author bofa1ex
 * @since 2020/1/26
 */
@Component
public class BinaryIntegerConvertMethod implements ConvertMethod<Integer> {

    public static final BinaryIntegerConvertMethod INSTANCE = new BinaryIntegerConvertMethod();

    @Override
    public Integer decode(ByteBuf buffer, Channel channel, String... parameters) {
        final String binary = BinaryStringConvertMethod.INSTANCE.decode(buffer, channel, parameters);
        return Integer.parseInt("".equals(binary) ? "0" : binary, 2);
    }

    @Override
    public ByteBuf encode(Integer value, int capacity, Channel channel, String... parameters) {
        return BinaryStringConvertMethod.INSTANCE.encode(Integer.toBinaryString(value), capacity, channel, parameters);
    }
}
