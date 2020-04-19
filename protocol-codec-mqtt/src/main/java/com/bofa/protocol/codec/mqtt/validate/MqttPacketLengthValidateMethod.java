package com.bofa.protocol.codec.mqtt.validate;

import com.bofa.protocol.codec.method.ValidateMethod;
import com.bofa.protocol.codec.mqtt.constants.MqttPacketTypeEnum;
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

    @Autowired
    private MqttPacketLengthConvertMethod mqttPacketLengthConvertMethod;

    @Override
    public void validate(ByteBuf buffer, Channel channel, Integer validateIndex, Integer validateLength, Integer mapperIndex, Integer mapperLength, String... parameters) {
        // (fix)心跳包等报文, 对应的length字段最大长度为2字节..
        // 这里有点坑, 干脆直接all in了. 否则传的4字节, 结果导致越界了.
        final ByteBuf packetLengthBuffer = buffer.slice(mapperIndex, buffer.readableBytes() - mapperIndex);
        final Integer packetLength = mqttPacketLengthConvertMethod.decode(packetLengthBuffer, channel);
        // packetLength真实所占字节数.
        final int packetLengthRealBits = packetLengthBuffer.readerIndex();
        final int packetLengthOffset = 1;
        // 2020-04-18 这里又遇到一个坑. 当publish 发布报文, qos为0时, payload后面跟了莫名其妙的e000数据, 原本校验的逻辑是正确的.
        // mosquito_pub -p 21234 -u bofa1ex -P 123456 -t "test/1/123" -m "hello world asd" -d -q
        // 数据: 301B000A746573742F312F31323368656C6C6F20776F726C6420617364E000
        final short head = buffer.getUnsignedByte(0);
        boolean isPublishWithZeroQosLevel =
                // 检查报文类型
                head >> 4 == MqttPacketTypeEnum.PUBLISH.packetType &&
                        // 检查qosLevel
                        (head >> 1 & 0x03) == 0;
        // 计算得出的packetLength
        int computedPacketLength = buffer.writerIndex() - packetLengthOffset - packetLengthRealBits;
        if (computedPacketLength != packetLength) {
            if (!isPublishWithZeroQosLevel) {
                throw new RuntimeException(Strings.lenientFormat("mqttPacketLength校验失败 [%d] != [%d]", packetLength, computedPacketLength));
            }
            // 处理mosquito_pub的bug
            final int adjustment = computedPacketLength > packetLength ? computedPacketLength - packetLength : 0;
            buffer.writerIndex(buffer.writerIndex() - adjustment);
        }
    }

    @Override
    public void mapper(ByteBuf buffer, Channel channel, Integer validateIndex, Integer validateLength, Integer mapperIndex, Integer mapperLength, String... parameters) {
        // 这里默认给定了报文长度为4字节(0x00,0x00,0x00,0x00).
        // 因此, 正确的报文长度为当前写索引 - 报文长度字段的偏移量(1) - 报文长度字段的默认长度(4).
        int packetLength = buffer.writerIndex() - mapperIndex - mapperLength;
        // 获取packetLength后的buffer.
        final ByteBuf rightBuffer = buffer.slice(mapperIndex + mapperLength, packetLength);
        // 直接根据mapperIndex, mapperLength获取缓冲区分片, 这里需要清空写索引.
        final ByteBuf packetLengthBuffer = buffer.slice(mapperIndex, mapperLength).clear();
        do {
            int digit = packetLength % 128;
            packetLength /= 128;
            if (packetLength > 0) {
                digit |= 0x80;
            }
            packetLengthBuffer.writeByte(digit);
        } while (packetLength > 0);
        // 拼装两段buffer
        buffer.writerIndex(mapperIndex).writeBytes(packetLengthBuffer).writeBytes(rightBuffer);
    }
}
