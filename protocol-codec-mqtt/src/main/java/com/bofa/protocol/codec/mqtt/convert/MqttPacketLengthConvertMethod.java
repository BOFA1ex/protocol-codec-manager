package com.bofa.protocol.codec.mqtt.convert;

import com.bofa.protocol.codec.method.ConvertMethod;
import com.bofa.protocol.codec.util.ChannelCodecContextUtils;
import io.netty.buffer.*;
import io.netty.channel.Channel;
import io.netty.channel.embedded.EmbeddedChannel;
import org.springframework.stereotype.Component;


/**
 * @author bofa1ex
 * @since 2020/3/23
 */
@Component
public class MqttPacketLengthConvertMethod implements ConvertMethod<Integer> {

    public static final MqttPacketLengthConvertMethod INSTANCE = new MqttPacketLengthConvertMethod();

    @Override
    public Integer decode(ByteBuf buffer, Channel channel, String... parameters) {
        /* 这里偷懒了, 直接拿netty MqttDecoder#decodeFixedHeader方法里的部分代码, 用于解析remainingLength */
        int remainingLength = 0;
        int multiplier = 1;
        short digit;
        int loops = 0;
        do {
            digit = buffer.readUnsignedByte();
            remainingLength += (digit & 0x7f) * multiplier;
            multiplier *= 0x80;
            loops++;
        } while ((digit & 0x80) != 0 && loops < 4);

        // MQTT protocol limits Remaining Length to 4 bytes
        if (loops == 4 && (digit & 0x80) != 0) {
            throw new IllegalArgumentException("remaining length exceeds 4 digits");
        }
        return remainingLength;
    }

    @Override
    public ByteBuf encode(Integer integer, int capacity, Channel channel, String... parameters) {
        // 直接走mapper自动计算packetLength, 不需要手动注入长度
        final int defaultCapacity = 4;
        return PooledByteBufAllocator.DEFAULT.buffer(defaultCapacity).writeBytes(new byte[]{0x00, 0x00, 0x00, 0x00});
    }

    public static void main(String[] args) {
        final ByteBuf buffer = Unpooled.wrappedBuffer(new byte[]{0x00, (byte) 0x80, 0x01, 0x00, 0x00});
        buffer.readerIndex(1);
        final EmbeddedChannel channel = new EmbeddedChannel();
        ChannelCodecContextUtils.setVariable("asd_buffer", buffer, channel);
        final Integer value = MqttPacketLengthConvertMethod.INSTANCE.decode(buffer, channel, "#asd_buffer");
        System.out.println("value -> " + value);
    }
}
