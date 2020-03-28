package com.bofa.protocol.codec.method.convert;

import com.bofa.protocol.codec.method.ConvertMethod;
import com.google.common.base.Strings;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author bofa1ex
 * @since 2020/1/26
 */
@Component
public class BinaryIntegerConvertMethod implements ConvertMethod<Integer> {
    @Autowired
    private BinaryStringConvertMethod binaryStringConvertMethod;

    @Override
    public Integer decode(ByteBuf buffer, Channel channel, String... parameters) {
        final String binary = binaryStringConvertMethod.decode(buffer, channel, parameters);
        return Integer.parseInt("".equals(binary) ? "0" : binary, 2);
    }

    @Override
    public ByteBuf encode(Integer value, int capacity, Channel channel, String... parameters) {
        if (parameters == null || parameters.length != 2) {
            throw new IllegalArgumentException("parameters不可为空或长度不合规范");
        }
        final int lOffset = Integer.parseInt(parameters[0]);
        final int rOffset = Integer.parseInt(parameters[1]);
        final int adjustment = rOffset - lOffset;
        final String binaryString = Strings.padStart(Integer.toBinaryString(value), adjustment, '0');
        return binaryStringConvertMethod.encode(binaryString, capacity, channel, parameters);
    }
}
