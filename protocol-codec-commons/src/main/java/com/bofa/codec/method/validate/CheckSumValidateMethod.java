package com.bofa.codec.method.validate;

import com.bofa.codec.util.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.embedded.EmbeddedChannel;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author bofa1ex
 * @since 2020/2/17
 */
@Component
public class CheckSumValidateMethod extends AbstractValidateMethod {

    public static final CheckSumValidateMethod INSTANCE = new CheckSumValidateMethod();

    public static void main(String[] args) {
        final EmbeddedChannel channel = new EmbeddedChannel();
        final ByteBuf buffer = Unpooled.buffer(6);
        buffer.writeBytes(new byte[]{0x01, 0x02, 0x03, 0x04, 0x05});
        CheckSumValidateMethod.INSTANCE.validate(buffer, channel, "0", "5", "5", "1");
        System.out.println("ByteBufUtils.buffer2Hex(buffer) -> " + ByteBufUtils.buffer2Hex(buffer));
    }

    @Override
    protected void validate0(ByteBuf buffer, Channel channel, Integer validateIndex, Integer validateLength, Integer mapperIndex, Integer mapperLength, String... parameters) {
        final AtomicInteger sum = new AtomicInteger();
        buffer.slice(validateIndex, validateLength).forEachByte(b -> {
            sum.addAndGet(b);
            return true;
        });
        int calculate = sum.intValue();
        // 如果超出范围, 取补码 + 1
        if (calculate > 0xff) {
            calculate = sum.updateAndGet(val -> ~val + 1);
        }
        final int csValue = Integer.parseInt(ByteBufUtils.buffer2Hex(buffer.slice(mapperIndex, mapperLength)), 16);
        if (csValue != calculate) {
            throw new RuntimeException(String.format("cs校验失败 %s != %s", csValue, calculate));
        }
    }

    @Override
    protected void mapper0(ByteBuf buffer, Channel channel, Integer validateIndex, Integer validateLength, Integer mapperIndex, Integer mapperLength, String... parameters) {
        final AtomicInteger sum = new AtomicInteger();
        buffer.slice(validateIndex, validateLength).forEachByte(b -> {
            sum.addAndGet(b);
            return true;
        });
        int calculate = sum.intValue();
        if (calculate > 0xff) {
            calculate = sum.updateAndGet(val -> ~val + 1);
        }
        final String hex = Integer.toHexString(calculate).substring(0, mapperLength);
        buffer.writerIndex(mapperIndex);
        buffer.writeBytes(ByteBufUtils.hex2Buffer(hex, mapperLength));
    }
}
