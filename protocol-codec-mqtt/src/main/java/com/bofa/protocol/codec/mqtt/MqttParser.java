package com.bofa.protocol.codec.mqtt;


import com.bofa.commons.apt4j.annotate.protocol.*;
import com.bofa.protocol.codec.mqtt.outward.MqttConnectCommand;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;

/**
 * @author bofa1ex
 * @since 2020/3/23
 */
@Protocol(implName = "MqttParserImpl")
public interface MqttParser {

    @ByteBufEncode(initialCapacity = 2 << 7)
    ByteBuf encode(MqttConnectCommand command, Channel channel);

    @ByteBufDecode()
    MqttConnectCommand decode(ByteBuf buffer, Channel channel);
}
