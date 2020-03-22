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
 * @version 1.0
 * @package com.bofa.resolve.method.validate
 * @date 2020/3/18
 */
@Component
public class BcdEqualsValidateMethod extends AbstractValidateMethod {

    public final static BcdEqualsValidateMethod INSTANCE = new BcdEqualsValidateMethod();

    @Override
    protected void validate0(ByteBuf buffer, Channel channel, Integer validateIndex, Integer validateLength, Integer mapperIndex, Integer mapperLength, String... parameters) {
        if (parameters.length != 1) {
            throw new RuntimeException("bcd校验需要提供一个参数");
        }
        String hex = ByteBufUtils.buffer2Hex(buffer.slice(validateIndex, validateLength));
        if (!parameters[0].equals(hex)) {
            throw new RuntimeException(Strings.lenientFormat("BcdEquals 校验失败 [结果:%s != %s] ", hex, parameters[0]));
        }
    }

    @Override
    protected void mapper0(ByteBuf buffer, Channel channel, Integer validateIndex, Integer validateLength, Integer mapperIndex, Integer mapperLength, String... parameters) {
        if (parameters.length != 1) {
            throw new RuntimeException("bcd校验需要提供一个参数");
        }
        ByteBuf mapperBuffer = ByteBufUtils.hex2Buffer(parameters[0], mapperLength);
        buffer.writerIndex(mapperIndex);
        buffer.writeBytes(mapperBuffer);
    }


    public static void main(String[] args) {
        ByteBuf byteBuf = PooledByteBufAllocator.DEFAULT.buffer();
//        byteBuf.writeBytes(new byte[]{0x00, 0x01});
//        BcdEqualsValidateMethod.INSTANCE.mapper0(byteBuf, new EmbeddedChannel(),
//                0,0, 2,1,"15");
//        String resp = ByteBufUtils.buffer2Hex(byteBuf);
        byteBuf.writeBytes(new byte[]{0x10, 0x11});
        BcdEqualsValidateMethod.INSTANCE.validate0(byteBuf, new EmbeddedChannel(),
                1, 1, 0, 0, "11");
    }
}
