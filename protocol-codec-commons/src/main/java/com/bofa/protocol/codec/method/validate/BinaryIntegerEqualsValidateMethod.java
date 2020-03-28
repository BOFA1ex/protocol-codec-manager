package com.bofa.protocol.codec.method.validate;

import com.bofa.protocol.codec.method.ValidateMethod;
import com.bofa.protocol.codec.method.convert.BinaryStringConvertMethod;
import com.google.common.base.Strings;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author bofa1ex
 * @since 2020/3/23
 */
@Component
public class BinaryIntegerEqualsValidateMethod implements ValidateMethod {

    @Autowired
    private BinaryStringConvertMethod binaryStringConvertMethod;

    @Override
    public void validate(ByteBuf buffer, Channel channel, Integer validateIndex, Integer validateLength, Integer mapperIndex, Integer mapperLength, String... parameters) {
        if (parameters.length != 2) {
            throw new RuntimeException("binaryInteger校验需要提供两个个参数");
        }
        final String binary = binaryStringConvertMethod.decode(buffer, channel, parameters);
        final int binaryIntValue = Integer.parseInt("".equals(binary) ? "0" : binary, 2);
        if (Integer.parseInt(parameters[0]) != binaryIntValue) {
            throw new RuntimeException(Strings.lenientFormat("BinaryIntegerEquals 校验失败 [结果:%s != %s] ", binaryIntValue, parameters[0]));
        }
    }

    @Override
    public void mapper(ByteBuf buffer, Channel channel, Integer validateIndex, Integer validateLength, Integer mapperIndex, Integer mapperLength, String... parameters) {
        if (parameters.length != 2) {
            throw new RuntimeException("binaryInteger校验需要提供两个个参数");
        }
        final int mapperIntValue = Integer.parseInt(parameters[0]);
        ByteBuf mapperBuffer = binaryStringConvertMethod.encode(Integer.toBinaryString(mapperIntValue), mapperLength, channel, parameters);
        buffer.writerIndex(mapperIndex);
        buffer.forEachByte(value -> {
            final byte val = mapperBuffer.readByte();
            buffer.writeByte(val + value);
            return true;
        });
    }
}
