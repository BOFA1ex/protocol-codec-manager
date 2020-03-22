package com.bofa.codec.method.validate;

import com.bofa.codec.util.ByteBufUtils;
import com.bofa.codec.util.algorithm.CRCUtils;
import com.google.common.base.Strings;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
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
public class Crc16CcittValidateMethod extends AbstractValidateMethod {

    public static final Crc16CcittValidateMethod INSTANCE = new Crc16CcittValidateMethod();

    @Override
    protected void validate0(ByteBuf buffer, Channel channel, Integer validateIndex, Integer validateLength, Integer mapperIndex, Integer mapperLength, String... parameters) {
        final byte[] crcBytes = new byte[validateLength];
        buffer.getBytes(validateIndex, crcBytes);
        final String computedCrc = Strings.padStart(Integer.toHexString(
                CRCUtils.crc16_ccitt(crcBytes, 0 , crcBytes.length))
                , 4, '0');
        final String crc = ByteBufUtils.buffer2Hex(buffer.slice(mapperIndex, mapperLength));
        if (!computedCrc.equalsIgnoreCase(crc)) {
            throw new RuntimeException("CRC校验失败");
        }
    }

    @Override
    protected void mapper0(ByteBuf buffer, Channel channel, Integer validateIndex, Integer validateLength, Integer mapperIndex, Integer mapperLength, String... parameters) {
        final byte[] crcBytes = new byte[validateLength];
        buffer.getBytes(validateIndex, crcBytes);
        final String computedCrc = Strings.padStart(Integer.toHexString(
                CRCUtils.crc16_ccitt(crcBytes, 0 , crcBytes.length))
                , 4, '0');
        buffer.writerIndex(mapperIndex);
        buffer.writeBytes(ByteBufUtils.hex2Buffer(computedCrc, 2));
    }

    public static void main(String[] args) {
        final ByteBuf buffer = Unpooled.wrappedBuffer(new byte[]{0x31, 0x32, 0x33, 0x34, 0x35, 0x74, 0x37});
        Crc16CcittValidateMethod.INSTANCE.validate0(buffer, new EmbeddedChannel(),
                0, 5, 5, 2);
    }
}
