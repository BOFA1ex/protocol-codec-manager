package com.bofa.protocol.codec.mqtt.model;

import com.bofa.commons.apt4j.annotate.cache.CacheMapping;
import com.bofa.commons.apt4j.annotate.protocol.ByteBufConvert;
import com.bofa.commons.apt4j.annotate.protocol.internal.ByteBufInternalPoint;
import com.bofa.protocol.codec.method.convert.*;
import com.bofa.protocol.codec.mqtt.AbstractMqttPacket;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.context.annotation.Description;

import java.util.List;

/**
 * @author bofa1ex
 * @since 2020/3/31
 */
@Data
@Description("SUBACK - 订阅确认报文" +
        "SUBACK 报文包含一个返回码清单，它们指定了 SUBSCRIBE 请求的每个订阅被授予的最大 QoS 等级")
@EqualsAndHashCode(callSuper = true)
@CacheMapping("mqttSubscribeAckPacket")
public class MqttSubscribeAckPacket extends AbstractMqttPacket {

    /* ******************************** 可变报文头部 固定2字节 ********************************/

    /** 报文标识符, 只有qos等级为1或2时, 该字段才出现在publish报文*/
    @ByteBufConvert(
            index = @ByteBufInternalPoint(step = "0"),
            length = @ByteBufInternalPoint(step = "2"),
            convertMethod = IntegerConvertMethod.class
    )
    private Integer packetIdentifier;

    /* ******************************** 有效载荷 ********************************/
    /**
     * 返回码
     * 0x00 最大QoS 0
     * 0x01 成功 - 最大QoS 1
     * 0x02 成功 - 最大QoS 2
     * 0x80 失败
     */
    @ByteBufConvert(
            index = @ByteBufInternalPoint(),
            length = @ByteBufInternalPoint(step = "0", type = ByteBufInternalPoint.StepType.REVERSE),
            convertMethod = HexCollectionsConvertMethod.class,
            parameters = "1"
    )
    private List<String> code;
}
