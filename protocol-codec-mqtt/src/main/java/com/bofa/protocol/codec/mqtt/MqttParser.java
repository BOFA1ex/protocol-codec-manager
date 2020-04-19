package com.bofa.protocol.codec.mqtt;


import com.bofa.commons.apt4j.annotate.protocol.*;
import com.bofa.protocol.codec.mqtt.model.*;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;

/**
 * @author bofa1ex
 * @since 2020/3/23
 */
@Protocol(implName = "MqttParserImpl")
public interface MqttParser {

    @ByteBufEncode(initialCapacity = 2 << 7, maxCapacity = 2 << 8)
    ByteBuf encode(MqttConnectPacket command, Channel channel);

    @ByteBufEncode(initialCapacity = 2 << 3, maxCapacity = 2 << 3)
    ByteBuf encode(MqttConnectAckPacket command, Channel channel);

    @ByteBufEncode(initialCapacity = 2 << 3, maxCapacity = 2 << 3)
    ByteBuf encode(MqttDisConnectPacket command, Channel channel);

    @ByteBufEncode(initialCapacity = 2 << 3, maxCapacity = 2 << 3)
    ByteBuf encode(MqttPingReqPacket command, Channel channel);

    @ByteBufEncode(initialCapacity = 2 << 3, maxCapacity = 2 << 3)
    ByteBuf encode(MqttPingResponsePacket command, Channel channel);

    @ByteBufEncode(initialCapacity = 2 << 4, maxCapacity = 2 << 5)
    ByteBuf encode(MqttPublishAckPacket command, Channel channel);

    @ByteBufEncode(initialCapacity = 2 << 4, maxCapacity = 2 << 5)
    ByteBuf encode(MqttPublishCompPacket command, Channel channel);

    @ByteBufEncode(initialCapacity = 2 << 7, maxCapacity = 2 << 8)
    ByteBuf encode(MqttPublishPacket command, Channel channel);

    @ByteBufEncode(initialCapacity = 2 << 4, maxCapacity = 2 << 5)
    ByteBuf encode(MqttPublishRecPacket command, Channel channel);

    @ByteBufEncode(initialCapacity = 2 << 4, maxCapacity = 2 << 5)
    ByteBuf encode(MqttPublishRelPacket command, Channel channel);

    @ByteBufEncode(initialCapacity = 2 << 7, maxCapacity = 2 << 8)
    ByteBuf encode(MqttSubscribePacket command, Channel channel);

    @ByteBufEncode(initialCapacity = 2 << 5, maxCapacity = 2 << 6)
    ByteBuf encode(MqttSubscribeAckPacket command, Channel channel);

    @ByteBufEncode(initialCapacity = 2 << 7, maxCapacity = 2 << 8)
    ByteBuf encode(MqttUnSubscribePacket command, Channel channel);

    @ByteBufEncode(initialCapacity = 2 << 4, maxCapacity = 2 << 5)
    ByteBuf encode(MqttUnSubscribeAckPacket command, Channel channel);

    @ByteBufDecode()
    MqttConnectPacket decodeMqttConnectPacket(ByteBuf buffer, Channel channel);

    @ByteBufDecode()
    MqttConnectAckPacket decodeMqttConnectAckPacket(ByteBuf buffer, Channel channel);

    @ByteBufDecode()
    MqttDisConnectPacket decodeMqttDisConnectPacket(ByteBuf buffer, Channel channel);

    @ByteBufDecode()
    MqttPingReqPacket decodeMqttPingReqPacket(ByteBuf buffer, Channel channel);

    @ByteBufDecode()
    MqttPingResponsePacket decodeMqttPingResponsePacket(ByteBuf buffer, Channel channel);

    @ByteBufDecode()
    MqttPublishAckPacket decodeMqttPublishAckPacket(ByteBuf buffer, Channel channel);

    @ByteBufDecode()
    MqttPublishCompPacket decodeMqttPublishCompPacket(ByteBuf buffer, Channel channel);

    @ByteBufDecode()
    MqttPublishPacket decodeMqttPublishPacket(ByteBuf buffer, Channel channel);

    @ByteBufDecode()
    MqttPublishRecPacket decodeMqttPublishRecPacket(ByteBuf buffer, Channel channel);

    @ByteBufDecode()
    MqttPublishRelPacket decodeMqttPublishRelPacket(ByteBuf buffer, Channel channel);

    @ByteBufDecode()
    MqttSubscribePacket decodeMqttSubscribePacket(ByteBuf buffer, Channel channel);

    @ByteBufDecode()
    MqttSubscribeAckPacket decodeMqttSubscribeAckPacket(ByteBuf buffer, Channel channel);

    @ByteBufDecode()
    MqttUnSubscribePacket decodeMqttUnSubscribePacket(ByteBuf buffer, Channel channel);

    @ByteBufDecode()
    MqttUnSubscribeAckPacket decodeMqttUnSubscribeAckPacket(ByteBuf buffer, Channel channel);
}
