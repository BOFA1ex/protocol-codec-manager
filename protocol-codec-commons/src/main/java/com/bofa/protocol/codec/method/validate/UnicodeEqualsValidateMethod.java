package com.bofa.protocol.codec.method.validate;

import com.bofa.protocol.codec.method.ValidateMethod;
import com.bofa.protocol.codec.method.convert.UnicodeConvertMethod;
import com.google.common.base.Strings;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author bofa1ex
 * @since 2020/3/21
 */
@Component
public class UnicodeEqualsValidateMethod implements ValidateMethod {

    @Autowired
    private UnicodeConvertMethod unicodeConvertMethod;

    @Override
    public void validate(ByteBuf buffer, Channel channel, Integer validateIndex, Integer validateLength, Integer mapperIndex, Integer mapperLength, String... parameters) {
        if (parameters == null || parameters.length != 2){
            throw new IllegalArgumentException("unicode校验需要指定unicode值和charsetName");
        }
        if (Strings.isNullOrEmpty(parameters[0])){
            throw new IllegalArgumentException("unicode parameter[1] charsetName不能为空");
        }
        final String data = unicodeConvertMethod.decode(buffer.slice(validateIndex, validateLength), channel, parameters[1]);
        if (!parameters[0].equals(data)) {
            throw new RuntimeException(Strings.lenientFormat("UnicodeEquals 校验失败 [结果:%s != %s] ", data, parameters[0]));
        }
    }

    @Override
    public void mapper(ByteBuf buffer, Channel channel, Integer validateIndex, Integer validateLength, Integer mapperIndex, Integer mapperLength, String... parameters) {
        if (parameters == null || parameters.length != 2){
            throw new IllegalArgumentException("unicode校验需要指定unicode值和charsetName");
        }
        if (Strings.isNullOrEmpty(parameters[0])){
            throw new IllegalArgumentException("unicode parameter[1] charsetName不能为空");
        }
        final ByteBuf mapperBuffer = unicodeConvertMethod.encode(parameters[0], mapperLength, channel, parameters[1]);
        afterMapperFlushBuffer(buffer, mapperBuffer, mapperIndex);
    }
}
