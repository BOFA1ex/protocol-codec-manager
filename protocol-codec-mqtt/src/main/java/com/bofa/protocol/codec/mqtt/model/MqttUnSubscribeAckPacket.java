package com.bofa.protocol.codec.mqtt.model;

import com.bofa.commons.apt4j.annotate.cache.CacheMapping;
import com.bofa.commons.apt4j.annotate.protocol.ByteBufConvert;
import com.bofa.commons.apt4j.annotate.protocol.internal.ByteBufInternalPoint;
import com.bofa.protocol.codec.method.convert.IntegerConvertMethod;
import com.bofa.protocol.codec.mqtt.constants.MqttPacketTypeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.context.annotation.Description;


/**
 * @author bofa1ex
 * @since 2020/3/31
 */
@Data
@Description("UNSUBACK- 取消订阅确认报文")
@EqualsAndHashCode(callSuper = true)
@CacheMapping("mqttUnSubscribeAckPacket")
public class MqttUnSubscribeAckPacket extends AbstractMqttPacket {

    public static MqttUnSubscribeAckPacket mapper(){
        final MqttUnSubscribeAckPacket mqttUnSubscribeAckPacket = new MqttUnSubscribeAckPacket();
        mqttUnSubscribeAckPacket.setPacketType(MqttPacketTypeEnum.UNSUBACK.packetType);
        return mqttUnSubscribeAckPacket;
    }
    /* ******************************** 可变报文头部 固定2字节 ********************************/

    /** 报文标识符, 只有qos等级为1或2时, 该字段才出现在publish报文*/
    @ByteBufConvert(
            index = @ByteBufInternalPoint(step = "0"),
            length = @ByteBufInternalPoint(step = "2"),
            convertMethod = IntegerConvertMethod.class
    )
    private Integer packetIdentifier;
}
