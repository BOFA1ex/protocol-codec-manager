package com.bofa.protocol.codec.mqtt.validate;

import com.bofa.protocol.codec.method.ValidateMethod;
import com.bofa.protocol.codec.mqtt.convert.MqttPacketLengthConvertMethod;
import com.google.common.base.Strings;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author bofa1ex
 * @since 2020/3/24
 */
@Component
public class MqttPacketLengthValidateMethod implements ValidateMethod {

    public static final MqttPacketLengthValidateMethod INSTANCE = new MqttPacketLengthValidateMethod();

    @Autowired
    private MqttPacketLengthConvertMethod mqttPacketLengthConvertMethod;

    @Override
    public void validate(ByteBuf buffer, Channel channel, Integer validateIndex, Integer validateLength, Integer mapperIndex, Integer mapperLength, String... parameters) {
        final ByteBuf packetLengthBuffer = buffer.slice(mapperIndex, mapperLength);
        final Integer packetLength = mqttPacketLengthConvertMethod.decode(packetLengthBuffer, channel);
        // packetLength真实所占字节数
        final int packetLengthRealBits = packetLengthBuffer.readerIndex();
        final int packetLengthOffset = 1;
        final int computedPacketLength = buffer.readableBytes() - packetLengthOffset - packetLengthRealBits;
        if (computedPacketLength != packetLength) {
            throw new RuntimeException(Strings.lenientFormat("mqttPacketLength校验失败 [%d] != [%d]", packetLength, parameters[0]));
        }
    }

    @Override
    public void mapper(ByteBuf buffer, Channel channel, Integer validateIndex, Integer validateLength, Integer mapperIndex, Integer mapperLength, String... parameters) {
        int packetLength = buffer.writerIndex() - mapperIndex - mapperLength;
        // 获取packetLength后的buffer
        final ByteBuf rightBuffer = buffer.slice(mapperIndex + mapperLength, packetLength);
        // 直接根据mapperIndex, mapperLength获取缓冲区分片
        final ByteBuf packetLengthBuffer = buffer.slice(mapperIndex, mapperLength).clear();
        do {
            int digit = packetLength % 128;
            packetLength /= 128;
            if (packetLength > 0) {
                digit |= 0x80;
            }
            packetLengthBuffer.writeByte(digit);
        } while (packetLength > 0);
        // 组装
        buffer.writerIndex(mapperIndex).writeBytes(packetLengthBuffer).writeBytes(rightBuffer);
    }
}
