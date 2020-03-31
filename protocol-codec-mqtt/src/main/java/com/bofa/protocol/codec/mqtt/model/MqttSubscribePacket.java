package com.bofa.protocol.codec.mqtt.model;

import com.bofa.commons.apt4j.annotate.cache.CacheMapping;
import com.bofa.commons.apt4j.annotate.protocol.ByteBufConvert;
import com.bofa.commons.apt4j.annotate.protocol.internal.ByteBufInternalPoint;
import com.bofa.protocol.codec.method.convert.IntegerConvertMethod;
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
@Description("SUBSCRIBE - 订阅主题报文")
@EqualsAndHashCode(callSuper = true)
@CacheMapping("mqttSubscribePacket")
public class MqttSubscribePacket extends AbstractMqttPacket {

    /* ******************************** 可变报文头部 固定2字节 ********************************/

    /** 报文标识符, 只有qos等级为1或2时, 该字段才出现在publish报文*/
    @ByteBufConvert(
            index = @ByteBufInternalPoint(step = "0"),
            length = @ByteBufInternalPoint(step = "2"),
            convertMethod = IntegerConvertMethod.class
    )
    private Integer packetIdentifier;

    /* ******************************** 有效载荷 ********************************/
    /** 订阅的主题过滤器 */
    @ByteBufConvert(
            index = @ByteBufInternalPoint(step = "0"),
            length = @ByteBufInternalPoint(step = "0", type = ByteBufInternalPoint.StepType.REVERSE),
            parameters = "java.util.LinkedList"
    )
    private List<MqttTopicFilterPacket> topicFilters;
}
