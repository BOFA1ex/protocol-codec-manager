package com.bofa.codec.method.validate;

import com.bofa.codec.util.ByteBufUtils;
import com.google.common.base.Strings;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.embedded.EmbeddedChannel;
import org.springframework.stereotype.Component;

/**
 * @author bofa1ex
 * @since 2020/3/21
 */
@Component
public class AsciiEqualsValidateMethod extends AbstractValidateMethod {

    public static final AsciiEqualsValidateMethod INSTANCE = new AsciiEqualsValidateMethod();

    @Override
    protected void validate0(ByteBuf buffer, Channel channel, Integer validateIndex, Integer validateLength, Integer mapperIndex, Integer mapperLength, String... parameters) {
        if (parameters.length != 1) {
            throw new RuntimeException("ascii校验需要提供一个参数");
        }
        String ascii = ByteBufUtils.buffer2Ascii(buffer.slice(validateIndex, validateLength));
        if (!parameters[0].equals(ascii)) {
            throw new RuntimeException(Strings.lenientFormat("AsciiEquals 校验失败 [结果:%s != %s] ", ascii, parameters[0]));
        }
    }

    @Override
    protected void mapper0(ByteBuf buffer, Channel channel, Integer validateIndex, Integer validateLength, Integer mapperIndex, Integer mapperLength, String... parameters) {
        if (parameters.length != 1) {
            throw new RuntimeException("ascii校验需要提供一个参数");
        }
        ByteBuf mapperBuffer = ByteBufUtils.ascii2Buffer(parameters[0], mapperLength);
        buffer.writerIndex(mapperIndex);
        buffer.writeBytes(mapperBuffer);
    }

    public static void main(String[] args) {
        ByteBuf byteBuf = PooledByteBufAllocator.DEFAULT.buffer();
//        byteBuf.writeBytes(new byte[]{});
//        AsciiEqualsValidateMethod.INSTANCE.mapper0(byteBuf, new EmbeddedChannel(),
//                0, 3, 0, 3, "FLV");
//        String resp = ByteBufUtils.buffer2Hex(byteBuf);
//        System.out.println("resp -> " + resp);
        byteBuf.writeBytes(new byte[]{0x46, 0x4C, 0x56});
        AsciiEqualsValidateMethod.INSTANCE.validate0(byteBuf, new EmbeddedChannel(),
                0, 3, 0, 3, "FLV");
    }
}
