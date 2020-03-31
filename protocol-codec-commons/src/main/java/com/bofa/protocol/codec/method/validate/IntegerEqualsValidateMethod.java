package com.bofa.protocol.codec.method.validate;

import com.bofa.protocol.codec.method.ValidateMethod;
import com.bofa.protocol.codec.method.convert.IntegerConvertMethod;
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
@Description("integer校验")
public class IntegerEqualsValidateMethod implements ValidateMethod {

    @Autowired
    private IntegerConvertMethod integerConvertMethod;

    @Override
    public void validate(ByteBuf buffer, Channel channel, Integer validateIndex, Integer validateLength, Integer mapperIndex, Integer mapperLength, String... parameters) {
        if (parameters.length != 1) {
            throw new RuntimeException("integer校验需要提供一个参数");
        }
        final Integer computedValue = integerConvertMethod.decode(buffer.slice(validateIndex, validateLength), channel, parameters);
        if (Integer.parseInt(parameters[0]) != computedValue) {
            throw new RuntimeException(Strings.lenientFormat("IntegerEquals 校验失败 [结果:%s != %s] ", computedValue, parameters[0]));
        }
    }

    @Override
    public void mapper(ByteBuf buffer, Channel channel, Integer validateIndex, Integer validateLength, Integer mapperIndex, Integer mapperLength, String... parameters) {
        if (parameters.length != 1) {
            throw new RuntimeException("integer校验需要提供一个参数");
        }
        final int parameterValue = Integer.parseInt(parameters[0]);
        final ByteBuf mapperBuffer = integerConvertMethod.encode(parameterValue, mapperLength, channel, parameters);
        afterMapperFlushBuffer(buffer, mapperBuffer, mapperIndex);
    }
}
