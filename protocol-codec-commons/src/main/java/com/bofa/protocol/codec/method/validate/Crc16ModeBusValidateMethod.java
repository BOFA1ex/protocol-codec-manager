package com.bofa.protocol.codec.method.validate;

import com.bofa.protocol.codec.method.ValidateMethod;
import com.bofa.protocol.codec.util.ByteBufUtils;
import com.bofa.protocol.codec.util.algorithm.CRCUtils;
import com.google.common.base.Strings;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import org.springframework.context.annotation.Description;
import org.springframework.stereotype.Component;


/**
 * @author bofa1ex
 * @since  2020/3/18
 */
@Component
@Description("循环冗余算法校验, crc16-modbus")
public class Crc16ModeBusValidateMethod implements ValidateMethod {

    @Override
    public void validate(ByteBuf buffer, Channel channel, Integer validateIndex, Integer validateLength, Integer mapperIndex, Integer mapperLength, String... parameters) {
        final byte[] crcBytes = new byte[validateLength];
        buffer.getBytes(validateIndex, crcBytes);
        final String computedCrc = Strings.padStart(Integer.toHexString(
                CRCUtils.crc16_modbus(crcBytes, 0 , crcBytes.length))
                , 4, '0');
        final String crc = ByteBufUtils.buffer2Hex(buffer.slice(mapperIndex, mapperLength));
        if (!computedCrc.equalsIgnoreCase(crc)) {
            throw new RuntimeException("CRC校验失败");
        }
    }

    @Override
    public void mapper(ByteBuf buffer, Channel channel, Integer validateIndex, Integer validateLength, Integer mapperIndex, Integer mapperLength, String... parameters) {
        final byte[] crcBytes = new byte[validateLength];
        buffer.getBytes(validateIndex, crcBytes);
        final String computedCrc = Strings.padStart(Integer.toHexString(
                CRCUtils.crc16_modbus(crcBytes, 0 , crcBytes.length))
                , 4, '0');
        buffer.writerIndex(mapperIndex);
        buffer.writeBytes(ByteBufUtils.hex2Buffer(computedCrc, 2));
    }
}
