package com.bofa.protocol.codec.mqtt.model;

import com.bofa.commons.apt4j.annotate.cache.CacheMapping;
import com.bofa.protocol.codec.mqtt.AbstractMqttPacket;
import com.bofa.protocol.codec.mqtt.constants.MqttPacketTypeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.context.annotation.Description;


/**
 * @author bofa1ex
 * @since 2020/3/31
 */
@Data
@Description("PINGRESP- 心跳响应报文")
@EqualsAndHashCode(callSuper = true)
@CacheMapping("mqttPingResponsePacket")
public class MqttPingResponsePacket extends AbstractMqttPacket {

    public static MqttPingResponsePacket mapper(){
        final MqttPingResponsePacket mqttPingResponsePacket = new MqttPingResponsePacket();
        mqttPingResponsePacket.setPacketType(MqttPacketTypeEnum.PINGRESP.packetType);
        return mqttPingResponsePacket;
    }
}
