package com.bofa.protocol.codec.mqtt.model;

import com.bofa.commons.apt4j.annotate.cache.CacheMapping;
import com.bofa.commons.apt4j.annotate.protocol.ByteBufConvert;
import com.bofa.commons.apt4j.annotate.protocol.internal.*;
import com.bofa.protocol.codec.method.convert.*;
import com.bofa.protocol.codec.mqtt.AbstractMqttPacket;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.context.annotation.Description;

/**
 * @author bofa1ex
 * @since 2020/3/23
 */
@Data
@Description("CONNACK - 确认连接请求报文")
@EqualsAndHashCode(callSuper = true)
@CacheMapping("mqttConnectAckPacket")
public class MqttConnectAckPacket extends AbstractMqttPacket {

    /* ******************************** 可变报文头部 固定2字节 ********************************/
    /** 当前会话标志 */
    @ByteBufConvert(
            index = @ByteBufInternalPoint(step = "0"),
            length = @ByteBufInternalPoint(step = "1"),
            convertMethod = BinaryIntegerConvertMethod.class
    )
    private Integer sessionPresent;

    /** 协议名不正确的情况下, 不处理connect报文, 防火墙可以根据该字段来识别mqtt流量 */
    @ByteBufConvert(
            index = @ByteBufInternalPoint(step = "0"),
            length = @ByteBufInternalPoint(step = "1"),
            convertMethod = IntegerConvertMethod.class
    )
    private Integer ackSign;
}
