package com.bofa.protocol.codec.mqtt.model;

import com.bofa.commons.apt4j.annotate.cache.CacheMapping;
import com.bofa.commons.apt4j.annotate.protocol.ByteBufConvert;
import com.bofa.commons.apt4j.annotate.protocol.internal.*;
import com.bofa.protocol.codec.method.convert.IntegerConvertMethod;
import com.bofa.protocol.codec.mqtt.AbstractMqttPacket;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.context.annotation.Description;

/**
 * @author bofa1ex
 * @since 2020/3/31
 */
@Data
@Description("PUBREC - 发布收到报文, 主要是对QoS为2等级的PUBLISH报文的响应")
@EqualsAndHashCode(callSuper = true)
@CacheMapping("mqttPublishRecPacket")
public class MqttPublishRecPacket extends AbstractMqttPacket {

    /* ******************************** 可变报文头部 固定2字节 ********************************/

    /** 报文标识符, 只有qos等级为1或2时, 该字段才出现在publish报文*/
    @ByteBufConvert(
            index = @ByteBufInternalPoint(step = "0"),
            length = @ByteBufInternalPoint(step = "2"),
            convertMethod = IntegerConvertMethod.class
    )
    private Integer packetIdentifier;
}
