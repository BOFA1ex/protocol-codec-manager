package com.bofa.protocol.codec.mqtt.model;

import com.bofa.commons.apt4j.annotate.cache.CacheMapping;
import com.bofa.protocol.codec.mqtt.constants.MqttPacketTypeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.context.annotation.Description;


/**
 * @author bofa1ex
 * @since 2020/3/31
 */
@Data
@Description("DISCONNECT- 主动断开连接报文")
@EqualsAndHashCode(callSuper = true)
@CacheMapping("mqttDisConnectPacket")
public class MqttDisConnectPacket extends AbstractMqttPacket {

    public static MqttDisConnectPacket mapper(){
        final MqttDisConnectPacket mqttDisConnectPacket = new MqttDisConnectPacket();
        mqttDisConnectPacket.setPacketType(MqttPacketTypeEnum.DISCONNECT.packetType);
        return mqttDisConnectPacket;
    }
}
