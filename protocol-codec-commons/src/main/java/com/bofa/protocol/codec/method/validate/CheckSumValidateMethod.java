package com.bofa.protocol.codec.method.validate;

import com.bofa.protocol.codec.method.ValidateMethod;
import com.bofa.protocol.codec.util.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import org.springframework.context.annotation.Description;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author bofa1ex
 * @since 2020/2/17
 */
@Component
@Description("累计求和算法校验")
public class CheckSumValidateMethod implements ValidateMethod {

    @Override
    public void validate(ByteBuf buffer, Channel channel, Integer validateIndex, Integer validateLength, Integer mapperIndex, Integer mapperLength, String... parameters) {
        int calculate = checksum(buffer, validateIndex, validateLength);
        final int csValue = Integer.parseInt(ByteBufUtils.buffer2Hex(buffer.slice(mapperIndex, mapperLength)), 16);
        if (csValue != calculate) {
            throw new RuntimeException(String.format("cs校验失败 %s != %s", csValue, calculate));
        }
    }

    @Override
    public void mapper(ByteBuf buffer, Channel channel, Integer validateIndex, Integer validateLength, Integer mapperIndex, Integer mapperLength, String... parameters) {
        int calculate = checksum(buffer, validateIndex, validateLength);
        final String hex = Integer.toHexString(calculate).substring(0, mapperLength);
        buffer.writerIndex(mapperIndex);
        buffer.writeBytes(ByteBufUtils.hex2Buffer(hex, mapperLength));
    }

    public int checksum(ByteBuf buffer, Integer validateIndex, Integer validateLength){
        final AtomicInteger sum = new AtomicInteger();
        buffer.slice(validateIndex, validateLength).forEachByte(b -> {
            sum.addAndGet(b);
            return true;
        });
        int calculate = sum.intValue();
        if (calculate > 0xff) {
            calculate = sum.updateAndGet(val -> ~val + 1);
        }
        return calculate;
    }
}
