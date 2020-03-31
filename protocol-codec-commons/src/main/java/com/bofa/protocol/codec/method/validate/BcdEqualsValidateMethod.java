package com.bofa.protocol.codec.method.validate;

import com.bofa.protocol.codec.method.ValidateMethod;
import com.bofa.protocol.codec.method.convert.HexConvertMethod;
import com.google.common.base.Strings;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Description;
import org.springframework.stereotype.Component;

/**
 * @author bofa1ex
 * @since  2020/3/18
 */
@Component
@Description("bcd码校验")
public class BcdEqualsValidateMethod implements ValidateMethod {

    @Autowired
    private HexConvertMethod hexConvertMethod;

    @Override
    public void validate(ByteBuf buffer, Channel channel, Integer validateIndex, Integer validateLength, Integer mapperIndex, Integer mapperLength, String... parameters) {
        if (parameters.length != 1) {
            throw new RuntimeException("bcd校验需要提供一个参数");
        }
        String hex = hexConvertMethod.decode(buffer.slice(validateIndex, validateLength), channel, parameters);
        if (!parameters[0].equals(hex)) {
            throw new RuntimeException(Strings.lenientFormat("BcdEquals 校验失败 [结果:%s != %s] ", hex, parameters[0]));
        }
    }

    @Override
    public void mapper(ByteBuf buffer, Channel channel, Integer validateIndex, Integer validateLength, Integer mapperIndex, Integer mapperLength, String... parameters) {
        if (parameters.length != 1) {
            throw new RuntimeException("bcd校验需要提供一个参数");
        }
        ByteBuf mapperBuffer = hexConvertMethod.encode(parameters[0], mapperLength, channel, parameters);
        buffer.writerIndex(mapperIndex);
        buffer.writeBytes(mapperBuffer);
    }
}
